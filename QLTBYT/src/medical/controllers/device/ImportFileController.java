package medical.controllers.device;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import medical.entities.Device;
import medical.excel.FileReader;
import medical.logics.DeviceLogicImpl;
import medical.logics.FileLogicImpl;
import medical.utils.Constants;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 50, // 50MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ImportFileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher req = request.getRequestDispatcher(Constants.IMPORT_FILE_URL);
		req.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		FileLogicImpl fileLogicImpl = new FileLogicImpl();
		DeviceLogicImpl deviceLogicImpl = new DeviceLogicImpl();
		List<Part> parts = new ArrayList<Part>();
		HttpSession session = request.getSession();
		
		try {
			parts = (List<Part>) request.getParts();
			String fileName = extractFileName(parts.get(0));
			// refines the fileName in case it is an absolute path
			fileName = new File(fileName).getName();
			parts.get(0).write(this.getFolderUpload().getAbsolutePath() + File.separator + fileName);

			String fullPath = Constants.DIR_FILE_EXCELL + "/" + fileName;

			FileReader reader = new FileReader();			
			List<Device> devices = reader.readExcel(fullPath);
			
			int userId = (int) session.getAttribute("userIdFile");
			fileLogicImpl.createFile(fileName, userId);
			
			
			for (Device device : devices) {
				device.setFile("" + fileLogicImpl.getFileIdByFileName(fileName).getFileID());
				deviceLogicImpl.createDevice(device);
			}
			
			String path = Constants.SUCCESS_URL + "?" + Constants.MESS + "=" + Constants.IMPORT_DEVICE_SUCCESSFUL;
			response.sendRedirect(request.getContextPath() + path);
		} catch (Exception e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển lỗi với câu lỗi hệ thống
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

	public File getFolderUpload() {
		File folderUpload = new File(Constants.DIR_FILE_EXCELL);
		if (!folderUpload.exists()) {
			folderUpload.mkdirs();
		}
		return folderUpload;
	}
}
