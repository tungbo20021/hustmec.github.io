package medical.controllers.device;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import medical.entities.Device;
import medical.logics.DeviceLogicImpl;
import medical.utils.Common;
import medical.utils.Constants;

/**
 * 
 * Controller để xử lý cho màn hình ADM003 trường hợp edit
 *
 *
 */
public class EditDeviceInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo các đối tượng
			DeviceLogicImpl deviceLogicImpl = new DeviceLogicImpl();
			Device device = null;
			// Lấy userId từ request
			String deviceIdFromRequest = request.getParameter("deviceId");
			// Parse userId sang Integer
			int deviceId = Common.convertStringToInteger(deviceIdFromRequest, Constants.DEFAULT_VALUE);
			// Nếu Parse userId thành công và user đó có tồn tại trong DB
			if (deviceId > Constants.DEFAULT_VALUE && deviceLogicImpl.checkExistDevice(deviceId)) {
				// Lấy về UserInfor từ method getDefaultValue
				device = getDefaultValue(request);
			}
			// Nếu userInfor khác null
			if (device != null) {
				// Set lại giá trị cho các pulldown ở màn hình ADM003
				setDataLogic(request);
				// Set userInfor lên request
				request.setAttribute("device", device);
				// Set action là edit lên request để hiển thị ADM003 trường hợp edit
				request.setAttribute(Constants.ACTION, Constants.EDIT_ACTION);
				// Forward đến MH ADM003
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ADD_DEVICE_JSP);
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
			// Khởi tạo các đối tượng
			DeviceLogicImpl deviceLogicImpl = new DeviceLogicImpl();
			Device device = null;
			// Lấy về userId từ request, parse sang int
			int deviceId = Common.convertStringToInteger(request.getParameter("deviceId"), Constants.DEFAULT_VALUE);
			// Nếu parse userId thành công và user đó có tồn tại trong DB
			if (deviceId > 0 && deviceLogicImpl.checkExistDevice(deviceId)) {
				// Gọi method getDefaultValue trả về 1 UserInfor
				device = getDefaultValue(request);
				// Lấy session
				HttpSession session = request.getSession();
				// Lấy key động
				String dynamicKey = Common.getSalt();
				// Ghi userInfor lên session theo key động
				// để giữ lại được giá trị khi click back từ ADM004
				session.setAttribute("device" + dynamicKey, device);
				// Đánh dấu cờ khi đi qua màn hình ADM003
				session.setAttribute(Constants.FLAG, Constants.FLAG);
				// redirect đến EditUserConfirmController
				response.sendRedirect(request.getContextPath() + Constants.EDIT_DEVICE_CONFIRM_URL + "?"
						+ Constants.DYNAMIC_KEY + "=" + dynamicKey);
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
	 * Get giá trị default cho màn hình ADM003 TH edit
	 * 
	 * @param request
	 * @return UserInfor
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private Device getDefaultValue(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		// Khởi tạo các đối tượng
		Device device = null;
		// Lấy action
		String typeAction = request.getParameter(Constants.ACTION);
		// Lấy userId từ request
		int deviceId = Common.convertStringToInteger(request.getParameter("deviceId"), Constants.DEFAULT_VALUE);
		// Trường hợp khi click vào button xác nhận
		if (Constants.CONFIRM_ACTION.equals(typeAction)) {
			device = new Device();
			// Lấy dữ liệu từ request
			String deviceName = request.getParameter("deviceName");
			String deviceModel = request.getParameter("deviceModel");
			String deviceSerial = request.getParameter("deviceSerial");
			String department = request.getParameter("department");

			String brand = request.getParameter("brand");
			String productDate = request.getParameter("productDate");
			String importDate = request.getParameter("importDate");
			String unit = request.getParameter("unit");
			int amount = Integer.parseInt(request.getParameter("amount"));
			
			String source = request.getParameter("source");
			String status = request.getParameter("status");
			
			int funcScore = Integer.parseInt(request.getParameter("funcScore"));
			int appScore = Integer.parseInt(request.getParameter("appScore"));
			int maintainScore = Integer.parseInt(request.getParameter("maintainScore"));
			int histoScore = Integer.parseInt(request.getParameter("histoScore"));


			// Set các giá trị cho userInfor
			device.setDeviceId(deviceId);
			device.setDeviceName(deviceName);
			device.setDeviceModel(deviceModel);
			device.setDeviceSerial(deviceSerial);
			device.setDepartment(department);
			device.setBrand(brand);

			device.setProductDate(productDate);
			device.setImportDate(importDate);
			
			device.setUnit(unit);
			device.setAmount(amount);
			device.setSource(source);
			device.setStatus(status);

			
			device.setFuncScore(funcScore);
			device.setAppScore(appScore);
			device.setMaintainScore(maintainScore);
			device.setHistoScore(histoScore);
			
		} else if (Constants.BACK_ACTION.equals(typeAction)) { // Trường hợp back từ ADM004
			// Khởi tạo session
			HttpSession session = request.getSession();
			// Lấy userInfor từ session
			device = (Device) session.getAttribute("device" + request.getParameter(Constants.DYNAMIC_KEY));
		} else { // Trường hợp từ ADM005 sang ADM003
			DeviceLogicImpl deviceLogicImpl = new DeviceLogicImpl();
			// Nếu user đó có tồn tại trong DB thì gọi method getUserById lấy về 1 userInfor
			device = deviceLogicImpl.getDeviceById(deviceId);
		}
		// Trả về deviceInfor
		return device;
	}

	public void setDataLogic(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		// Khởi tạo các đối tượng

	}
}
