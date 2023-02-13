package medical.controllers.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import medical.entities.User;
import medical.utils.Common;
import medical.utils.Constants;
import medical.validates.UserValidate;

/**
 * 
 * Controller để xử lý cho màn hình ADM003 trường hợp add
 *
 *
 */
public class AddUserInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo đối tượng UserInfor
			User user = new User();

			// Lấy userInfor
			user = getDefaultValue(request);
			// set userInfor vừa lấy được lên request
			request.setAttribute("user", user);
			// Forward đến trang ADM003
			RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ADD_USER_JSP);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển màn hình lỗi hệ thống
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}

	/**
	 *
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo list chứa các câu thông báo lỗi validate
			List<String> listErrorMess = new ArrayList<>();
			// Khởi tạo đối tượng UserInfor
			User user = new User();
			// Khởi tạo đối tượng UserValidate
			UserValidate userValidate = new UserValidate();
			// Gọi method getDefaultValue trả về 1 UserInfor
			user = getDefaultValue(request);
			// Gọi mehod validateUserInfor lấy thông báo lỗi
			listErrorMess = userValidate.validateUser(user);
			// Nếu list có thông báo lỗi
			if (listErrorMess.size() > 0) {
				// set lên request
				request.setAttribute("user", user);
				// Set lỗi lên request
				request.setAttribute(Constants.LIST_ERROR_MESS, listErrorMess);
				// Forward đến trang ADM003 có lỗi
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ADD_USER_JSP);
				dispatcher.forward(request, response);
			} else {
				// Lấy key động truyền vào tên attribute
				String dynamicKey = Common.getSalt();
				// Khởi tạo session
				HttpSession session = request.getSession();
				// Ghi userInfor lên session
				// để hiển thị ADM004 và giữ lại được giá trị khi click back từ ADM004
				session.setAttribute("user" + dynamicKey, user);
				// Đánh dấu giá trị mock khi đi qua màn hình ADM003
				session.setAttribute(Constants.FLAG, Constants.FLAG);
				// redirect đến AddUserConfirmController
				response.sendRedirect(request.getContextPath() + Constants.ADD_USER_CONFIRM_URL + "?"
						+ Constants.DYNAMIC_KEY + "=" + dynamicKey);
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
	 * Get giá trị default cho màn hình ADM003
	 * 
	 * @param request
	 * @return UserInfor
	 */
	private User getDefaultValue(HttpServletRequest request) {
		// Khởi tạo session
		HttpSession session = request.getSession();
		// Khởi tạo đối tượng UserInfor
		User user = new User();
		// Lấy action
		String typeAction = request.getParameter(Constants.ACTION);
		// Trường hợp khi click vào button xác nhận
		if (Constants.CONFIRM_ACTION.equals(typeAction)) {
			// Lấy dữ liệu từ request
			String fullName = request.getParameter(Constants.FULLNAME);
			String loginName = request.getParameter(Constants.LOGIN_NAME);
			String role = request.getParameter("role");
			String password = request.getParameter(Constants.PASSWORD);
			String passwordConfirm = request.getParameter(Constants.PASSWORD_CONFIRM);
			String address = request.getParameter("address");
			String mobile = request.getParameter("mobile");
			String department = request.getParameter("department");
			String email = request.getParameter(Constants.EMAIL);
			// Set các giá trị cho userInfor
			user.setLoginName(loginName);
			user.setDepartment(department);
			user.setFullName(fullName);
			user.setEmail(email);
			user.setMobile(mobile);
			user.setPassword(password);
			user.setPasswordConfirm(passwordConfirm);
			user.setAddress(address);
			user.setRole(role);
		} else if (Constants.BACK_ACTION.equals(typeAction)) {
			// Lấy userInfor từ session
			user = (User) session.getAttribute("user" + request.getParameter(Constants.DYNAMIC_KEY));
		} else {
			// Trường hợp default khi init MH
			user.setLoginName(Constants.BLANK);
			user.setDepartment(Constants.BLANK);
			user.setFullName(Constants.BLANK);
			user.setEmail(Constants.BLANK);
			user.setMobile(Constants.BLANK);
			user.setPassword(Constants.BLANK);
			user.setAddress(Constants.BLANK);
			user.setRole(Constants.BLANK);

		}
		// Trả về userInfor
		return user;
	}
}
