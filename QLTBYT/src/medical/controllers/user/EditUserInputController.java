package medical.controllers.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import medical.entities.User;
import medical.logics.UserLogicImpl;
import medical.utils.Common;
import medical.utils.Constants;
import medical.validates.UserValidate;

/**
 * 
 * Controller để xử lý cho màn hình ADM003 trường hợp edit
 *
 *
 */
public class EditUserInputController extends HttpServlet {
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
			User user = null;
			// Lấy userId từ request
			String userIdFromRequest = request.getParameter(Constants.USER_ID);
			// Parse userId sang Integer
			int userId = Common.convertStringToInteger(userIdFromRequest, Constants.DEFAULT_VALUE);
			// Nếu Parse userId thành công và user đó có tồn tại trong DB
			if (userId > Constants.DEFAULT_VALUE && userLogicImpl.checkExistUser(userId)) {
				// Lấy về UserInfor từ method getDefaultValue
				user = getDefaultValue(request);
			}
			
			System.out.println("role - - - - - - - - " + user.getRole());

			
			// Nếu userInfor khác null
			if (user != null) {
				
				// Set userInfor lên request
				request.setAttribute("user", user);
				// Set action là edit lên request để hiển thị ADM003 trường hợp edit
				request.setAttribute(Constants.ACTION, Constants.EDIT_ACTION);
				// Forward đến MH ADM003
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ADD_USER_JSP);
				dispatcher.forward(request, response);
			} else {
				// Redirect đến controller điều khiển lỗi với câu thông báo user không tồn tại
				response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO + "?" + Constants.MESS + "="
						+ Constants.USER_NOT_EXIST);
			}
		} catch (Exception e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển lỗi
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
			// Danh sách chứa các câu thông báo lỗi validate
			List<String> listErrorMess = new ArrayList<>();
			// Khởi tạo các đối tượng
			UserLogicImpl userLogicImpl = new UserLogicImpl();
			UserValidate userValidate = new UserValidate();
			User user = null;
			// Lấy về userId từ request, parse sang int
			int userId = Common.convertStringToInteger(request.getParameter(Constants.USER_ID),
					Constants.DEFAULT_VALUE);
			// Nếu parse userId thành công và user đó có tồn tại trong DB
			if (userId > 0 && userLogicImpl.checkExistUser(userId)) {
				// Gọi method getDefaultValue trả về 1 UserInfor
				user = getDefaultValue(request);
				// Gọi mehod validateUserInfor lấy thông báo lỗi
				listErrorMess = userValidate.validateUser(user);
				// Nếu list có thông báo lỗi
				if (listErrorMess.size() > 0) {
					// Set action là edit lên request để hiển thị ADM003 trường hợp edit
					request.setAttribute(Constants.ACTION, Constants.EDIT_ACTION);
					// set userInfor lên request
					request.setAttribute("user", user);
					
					System.out.println("role - - - - - - - - " + user.getRole());

					
					// Set danh sách lỗi lên request
					request.setAttribute(Constants.LIST_ERROR_MESS, listErrorMess);
					// Forward đến trang ADM003
					RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ADD_USER_JSP);
					dispatcher.forward(request, response);
				} else {
					// Nếu không có lỗi
					// Khởi tạo các đối tượng

					// Lấy session
					HttpSession session = request.getSession();
					// Lấy key động
					String dynamicKey = Common.getSalt();
					// Ghi userInfor lên session theo key động
					// để giữ lại được giá trị khi click back từ ADM004
					session.setAttribute("user" + dynamicKey, user);
					// Đánh dấu cờ khi đi qua màn hình ADM003
					session.setAttribute(Constants.FLAG, Constants.FLAG);
					// redirect đến EditUserConfirmController
					response.sendRedirect(request.getContextPath() + Constants.EDIT_USER_CONFIRM_URL + "?"
							+ Constants.DYNAMIC_KEY + "=" + dynamicKey);
				}
			} else {
				// Redirect đến controller điều khiển lỗi với câu thông báo user không tồn tại
				response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO + "?" + Constants.MESS + "="
						+ Constants.USER_NOT_EXIST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển lỗi
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}

	/**
	 * Get giá trị default cho màn hình ADM003 TH edit
	 * 
	 * @param request
	 * @return UserInfor
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private User getDefaultValue(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		// Khởi tạo các đối tượng
		User user = null;
		// Lấy action
		String typeAction = request.getParameter(Constants.ACTION);
		// Lấy userId từ request
		int userId = Common.convertStringToInteger(request.getParameter(Constants.USER_ID), Constants.DEFAULT_VALUE);
		// Trường hợp khi click vào button xác nhận
		if (Constants.CONFIRM_ACTION.equals(typeAction)) {
			user = new User();
			// Lấy dữ liệu từ request set cho userInfor
			user.setUserId(userId);
			user.setLoginName(request.getParameter(Constants.LOGIN_NAME));
			user.setDepartment(request.getParameter("department"));
			user.setRole(request.getParameter("role"));
			user.setFullName(request.getParameter(Constants.FULLNAME));
			user.setEmail(request.getParameter(Constants.EMAIL));
			user.setMobile(request.getParameter("mobile"));
			user.setPassword(request.getParameter(Constants.PASSWORD));
			user.setAddress(request.getParameter("address"));
		} else if (Constants.BACK_ACTION.equals(typeAction)) { // Trường hợp back từ ADM004
			// Khởi tạo session
			HttpSession session = request.getSession();
			// Lấy userInfor từ session
			user = (User) session.getAttribute("user" + request.getParameter(Constants.DYNAMIC_KEY));
		} else { // Trường hợp từ ADM005 sang ADM003
			UserLogicImpl userLogicImpl = new UserLogicImpl();
			// Nếu user đó có tồn tại trong DB thì gọi method getUserById lấy về 1 userInfor
			user = userLogicImpl.getUserById(userId);
		}
		// Trả về userInfor
		return user;
	}
}
