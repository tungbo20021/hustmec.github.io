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
 * Controller để xử lý cho màn hình System_error
 *
 *
 */

public class SystemErrorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Lấy câu thông báo từ request
		String keyMess = request.getParameter(Constants.MESS);
		// Nếu có câu thông báo được truyền theo request
		if (keyMess != null) {
			// Đọc câu thông báo lỗi từ file
			String mess = MessageProperties.getMessage(keyMess);
			// set lỗi lên request để hiển thị
			request.setAttribute(Constants.MESS, mess);
		} else {
			// Đọc câu thông báo lỗi hệ thống
			String mess = MessageProperties.getMessage(Constants.ERROR_SYSTEM_MESS);
			// set lỗi lên request để hiển thị
			request.setAttribute(Constants.MESS, mess);
		}
		// Forward đến màn hình System_error
		RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.SYSTEM_ERROR_URL);
		dispatcher.forward(request, response);
	}
}
