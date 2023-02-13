package medical.controllers.device;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import medical.entities.Device;
import medical.logics.DeviceLogicImpl;
import medical.utils.Constants;

/**
 * 
 * Controller để xử lý cho màn hình ADM004
 *
 *
 */
public class EditDeviceConfirmController extends HttpServlet {
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
				// Lấy deviceInfor theo dynamicKey từ session
				Device device = (Device) session.getAttribute("device" + dynamicKey);
				
				device.setEmScore(device.getEmScore());
								
				// Set dynamicKey và đối tượng DeviceInfor lên request
				request.setAttribute(Constants.DYNAMIC_KEY, dynamicKey);
				request.setAttribute("device", device);
				// Set action là edit lên request để hiển thị ADM004 trường hợp edit
				request.setAttribute(Constants.ACTION, Constants.EDIT_ACTION);
				// Forward đến màn hình ADM004
				RequestDispatcher req = request.getRequestDispatcher(Constants.CONFIRM_DATA_DEVICE_JSP);
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
			Device device = new Device();
			DeviceLogicImpl deviceLogicImpl = new DeviceLogicImpl();
			// Lấy dynamicKey từ request
			String dynamicKey = request.getParameter(Constants.DYNAMIC_KEY);
			// Lấy deviceInfor theo dynamicKey từ session
			device = (Device) session.getAttribute("device" + dynamicKey);
			// Lấy xong DeviceInfor thì xóa trên session
			session.removeAttribute("device" + dynamicKey);
			// Đường dẫn redirect
			String path = Constants.SYSTEM_ERROR_DO;
			
			// Nếu device có tồn tại trong DB
			if (deviceLogicImpl.checkExistDevice(device.getDeviceId())) {
				if (deviceLogicImpl.updateDevice(device)) {
					// Nếu update device thành công thì lấy đường dẫn chứa câu update thành công
					path = Constants.SUCCESS_URL + "?" + Constants.MESS + "=" + Constants.UPDATE_DEVICE_SUCCESSFUL;
				} else {
					// Nếu update device thất bại thì lấy đường dẫn chứa câu lỗi hệ thống
					path = Constants.SYSTEM_ERROR_DO;
				}
			} else {
				// Nếu đã bị trùng email khác trong DB thì lấy đường dẫn chứa câu device không
				// tồn
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
