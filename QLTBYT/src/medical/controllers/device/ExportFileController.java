package medical.controllers.device;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import medical.entities.Device;
import medical.excel.FileWriter;
import medical.logics.DeviceLogicImpl;
import medical.utils.Constants;

public class ExportFileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DeviceLogicImpl deviceLogicImpl = new DeviceLogicImpl();
		FileWriter writer = new FileWriter();
		HttpSession session = request.getSession();

		try {
			List<Device> devices = new ArrayList<Device>();
			devices = deviceLogicImpl.getDevicesExportFile();
			writer.exportExcel(devices);
			
			String path = Constants.SUCCESS_URL + "?" + Constants.MESS + "=" + Constants.EXPORT_DEVICE_SUCCESSFUL;
			response.sendRedirect(request.getContextPath() + path);
			
		} catch (ClassNotFoundException | SQLException e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển lỗi với câu lỗi hệ thống
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
