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
public class AddUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy session
			HttpSession session = request.getSession();
			// Lấy dynamicKey từ request
			String dynamicKey = request.getParameter(Constants.DYNAMIC_KEY);
			// Lấy userInfor theo dynamicKey từ session
			User user = (User) session.getAttribute("user" + dynamicKey);
			// Lấy giá trị flag trên session
			String flag = (String) session.getAttribute(Constants.FLAG);
			// Set các giá trị lên request
			request.setAttribute(Constants.DYNAMIC_KEY, dynamicKey);
			request.setAttribute("user", user);
			// Nếu truy cập ADM004 mà qua ADM003
			if (flag != null) {
				// Xóa flag đã lưu khi qua màn ADM003 trên session
				session.removeAttribute(Constants.FLAG);
				// Forward đến màn hình ADM004
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.CONFIRM_DATA_USER_JSP);
				dispatcher.forward(request, response);
			} else {
				// Redirect đến controller điều khiển màn hình lỗi hệ thống
				response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
			}
		} catch (Exception e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển màn hình lỗi hệ thống
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// url redirect
			String path = Constants.SYSTEM_ERROR_DO;
			// Lấy session
			HttpSession session = request.getSession();
			// Khởi tạo các đối tượng
			UserLogicImpl userLogicImpl = new UserLogicImpl();
			User user = new User();
			// Lấy dynamicKey từ request
			String dynamicKey = request.getParameter(Constants.DYNAMIC_KEY);
			// Lấy userInfor theo dynamicKey từ session
			user = (User) session.getAttribute("user" + dynamicKey);
			
			System.out.println("user - " + user.getFullName());
			
			// Lấy xong đối tượng UserInfor thì xóa đi trên session
			session.removeAttribute("user" + dynamicKey);
			// Nếu loginName và email muốn add chưa tồn tại trong DB
			if (!userLogicImpl.checkExistLoginName(user.getLoginName())
					&& !userLogicImpl.checkExistEmail(user.getEmail())) {
				// Thực hiện thêm mới user
				boolean create = userLogicImpl.createUser(user);
				// Nếu thêm mới user thành công
				if (create) {
					// Lấy đường dẫn đến SuccessController với câu thông báo thêm thành công
					path = Constants.SUCCESS_URL + "?" + Constants.MESS + "=" + Constants.ADD_SUCCESSFUL;
				} else {
					// Nếu thêm mới user thất bại
					// Lấy đường dẫn đến SystemErrorController
					path = Constants.SYSTEM_ERROR_DO;
				}
			}
			// Redirect đến url trong mỗi trường hợp
			response.sendRedirect(request.getContextPath() + path);
		} catch (Exception e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển màn hình lỗi hệ thống
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}
}
