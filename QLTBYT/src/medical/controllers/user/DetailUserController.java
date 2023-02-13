package medical.controllers.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import medical.entities.User;
import medical.logics.UserLogicImpl;
import medical.utils.Common;
import medical.utils.Constants;
import medical.utils.MessageProperties;

/**
 * 
 * Controller để xử lý cho màn hình ADM005
 *
 *
 */
public class DetailUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo các đối tượng
			UserLogicImpl userLogicImpl = new UserLogicImpl();
			User user = new User();
			// Lấy userId từ request
			String userIdFromRequest = request.getParameter(Constants.USER_ID);
			// Parse userId sang Integer
			int userId = Common.convertStringToInteger(userIdFromRequest, Constants.DEFAULT_VALUE);
			// Nếu Parse userId thành công và user đó có tồn tại trong DB
			if (userId > Constants.DEFAULT_VALUE && userLogicImpl.checkExistUser(userId)) {
				// Gọi method getUserById trả về 1 userInfor
				user = userLogicImpl.getUserById(userId);
				// Set userInfor lên request
				request.setAttribute("user", user);				
				// Set câu confirm delete lên request để dùng cho button delete ở màn ADM005
				request.setAttribute(Constants.MESS_CONFIRM_DELETE,
						MessageProperties.getMessage(Constants.CONFIRM_DELETE_USER));
				// Forward đến trang ADM005
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.DETAIL_DATA_USER_JSP);
				dispatcher.forward(request, response);
			} else {
				// Redirect đến controller điều khiển lỗi với câu user không tồn tại
				response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO + "?" + Constants.MESS + "="
						+ Constants.USER_NOT_EXIST);
			}
		} catch (Exception e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển lỗi với câu lỗi hệ thống
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}

}
