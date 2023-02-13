package medical.controllers.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import medical.entities.User;
import medical.logics.UserLogicImpl;
import medical.utils.Constants;

/**
 * 
 * Controller để xử lý cho màn hình ADM004
 *
 *
 */
public class EditUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy session
			HttpSession session = request.getSession();
			// Lấy về giá trị flag trên session
			String flag = session.getAttribute(Constants.FLAG).toString();
			// Nếu truy cập ADM004 bằng cách đi qua ADM003
			if (flag != null) {
				// Xóa giá trị flag trên session
				session.removeAttribute(Constants.FLAG);
				// Lấy dynamicKey từ request
				String dynamicKey = request.getParameter(Constants.DYNAMIC_KEY);
				// Lấy userInfor theo dynamicKey từ session
				User user = (User) session.getAttribute("user" + dynamicKey);
				// Set dynamicKey và đối tượng UserInfor lên request
				request.setAttribute(Constants.DYNAMIC_KEY, dynamicKey);
				request.setAttribute("user", user);
				// Set action là edit lên request để hiển thị ADM004 trường hợp edit
				request.setAttribute(Constants.ACTION, Constants.EDIT_ACTION);
				// Forward đến màn hình ADM004
				RequestDispatcher req = request.getRequestDispatcher(Constants.CONFIRM_DATA_USER_JSP);
				req.forward(request, response);
			} else {
				// Nếu truy cập ADM004 mà chưa qua ADM003
				// Redirect đến controller điều khiển lỗi với câu thông báo lỗi hệ thống
				response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
			}
		} catch (Exception e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển lỗi với câu thông báo lỗi hệ thống
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy session
			HttpSession session = request.getSession();
			// Khởi tạo các đối tượng
			User user = new User();
			UserLogicImpl userLogicImpl = new UserLogicImpl();
			// Lấy dynamicKey từ request
			String dynamicKey = request.getParameter(Constants.DYNAMIC_KEY);
			// Lấy userInfor theo dynamicKey từ session
			user = (User) session.getAttribute("user" + dynamicKey);
			// Lấy xong UserInfor thì xóa trên session
			session.removeAttribute("user" + dynamicKey);
			// Đường dẫn redirect
			String path = Constants.SYSTEM_ERROR_DO;
			// Nếu user có tồn tại trong DB
			if (userLogicImpl.checkExistUser(user.getUserId())) {
				// Nếu email update không trùng với email khác trong DB
				if (!userLogicImpl.checkExistEmail(user.getEmail(), user.getUserId())) {
					// Gọi method updateUser() và kiểm tra
					if (userLogicImpl.updateUser(user)) {
						// Nếu update user thành công thì lấy đường dẫn chứa câu update thành công
						path = Constants.SUCCESS_URL + "?" + Constants.MESS + "=" + Constants.UPDATE_SUCCESSFUL;
					} else {
						// Nếu update user thất bại thì lấy đường dẫn chứa câu lỗi hệ thống
						path = Constants.SYSTEM_ERROR_DO;
					}
				}
			} else {
				// Nếu đã bị trùng email khác trong DB thì lấy đường dẫn chứa câu user không tồn
				// tại
				path = Constants.SYSTEM_ERROR_DO + "?" + Constants.MESS + "=" + Constants.USER_NOT_EXIST;
			}
			// Redirect đến controller điều khiển trong từng trường hợp
			response.sendRedirect(request.getContextPath() + path);
		} catch (Exception e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển lỗi với câu lỗi hệ thống
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}
}
