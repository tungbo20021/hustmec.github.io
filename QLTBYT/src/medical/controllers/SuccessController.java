package medical.controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import medical.utils.Constants;
import medical.utils.MessageProperties;

/**
 * 
 * Controller để xử lý cho màn hình ADM006
 *
 *
 */
public class SuccessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy câu thông báo từ request
			String keyMess = request.getParameter(Constants.MESS);
			// Đọc câu thông báo từ file
			String mess = MessageProperties.getMessage(keyMess);
			// set câu thông báo lên request để hiển thị
			request.setAttribute(Constants.MESS, mess);
			request.setAttribute("keyMess", keyMess);
			// Forward đến màn hình ADM006
			RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.SUCCESS_JSP);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển lỗi
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}
}
