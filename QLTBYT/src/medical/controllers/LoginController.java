package medical.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import medical.entities.HisLog;
import medical.entities.User;
import medical.logics.HisLogLogicImpl;
import medical.logics.UserLogicImpl;
import medical.utils.Common;
import medical.utils.Constants;
import medical.validates.UserValidate;

/**
 * 
 * Controller xử lý cho màn hình ADM001
 *
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Được gọi khi redirect đến login.do
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Forward đến MH ADM001
		RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.LOGIN_JSP);
		dispatcher.forward(request, response);
	}

	/**
	 * Submit form ở màn hình ADM001
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			HisLogLogicImpl hisLogLogicImpl = new HisLogLogicImpl();
			
			// List chứa các câu thông báo lỗi
			List<String> listErrorMess = new ArrayList<>();
			// Khởi tạo đối tượng UserValidate
			UserValidate userValidate = new UserValidate();
			// Lấy loginName và password từ request
			String loginName = request.getParameter(Constants.LOGIN_NAME);
			String password = request.getParameter(Constants.PASSWORD);
			// Gọi method validateLogin trả về danh sách các câu thông báo validate
			listErrorMess = userValidate.validateLogin(loginName, password);
			// Nếu danh sách validate có giá trị
			if (listErrorMess.size() > 0) {
				// Set lỗi lên request để hiển thị
				request.setAttribute(Constants.LIST_ERROR_MESS, listErrorMess);
				//Giữ lại giá trị loginName
				request.setAttribute(Constants.LOGIN_NAME, loginName);
				// Forward đến MH ADM001 để hiển thị lại
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.LOGIN_JSP);
				dispatcher.forward(request, response);
			} else {
				
				UserLogicImpl userLogicImpl = new UserLogicImpl();
				boolean isAdmin = userLogicImpl.checkAdminByLoginName(loginName);
				
				User user = userLogicImpl.getUserByLoginName(loginName);
				int userId = user.getUserId();
				
				// lưu lại lịch sử đăng nhập
				HisLog hisLog = new HisLog();
				hisLog.setLogTime(Common.createLogTime());
				hisLog.setUserId(userId);
				hisLogLogicImpl.createHisLog(hisLog);
				
				// Không có lỗi thì lưu loginName lên session
				HttpSession session = request.getSession();
				session.setAttribute(Constants.LOGIN_NAME, loginName);
				session.setAttribute("userIdFile", userId);
				
				session.setAttribute("isAdmin", isAdmin);

				// Redirect đến /listUser.do
				response.sendRedirect(request.getContextPath() + "/ListDevice.do");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển lỗi với câu thông báo lỗi hệ thống
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}
}
