package medical.controllers.device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import medical.utils.MessageProperties;

public class DetailFileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo các đối tượng
			HttpSession session = request.getSession();
			List<Device> listDevices = new ArrayList<>();
			List<Integer> listPaging = new ArrayList<>();
			DeviceLogicImpl deviceLogicImpl = new DeviceLogicImpl();
			// Lấy userId từ request
			String fileIDFromRequest = request.getParameter("fileID");
			
			// Parse userId sang Integer
			int fileID = Common.convertStringToInteger(fileIDFromRequest, Constants.DEFAULT_VALUE);
			
			
			int offset = Constants.DEFAULT_VALUE;
			int limit = Constants.LIMIT_RECORD;
			String sortByDeviceName = Constants.ASC;
			int previousPage = Constants.DEFAULT_VALUE;
			int nextPage = Constants.DEFAULT_VALUE;
			int currentPage = 1;

			// Lấy action từ request
			String typeAction = request.getParameter(Constants.ACTION);
			boolean isAdmin = (boolean) session.getAttribute("isAdmin");

				if (Constants.BACK_ACTION.equals(typeAction)) {
					// Action back từ màn hình ADM003
					// Lấy các dữ liệu từ session
					fileID = (int) session.getAttribute("fileID");

					currentPage = (int) session.getAttribute(Constants.CURRENT_PAGE);
					sortByDeviceName = (String) session.getAttribute(sortByDeviceName);
				} else if (Constants.SEARCH_ACTION.equals(typeAction) || Constants.SORT_ACTION.equals(typeAction)
						|| Constants.PAGING_ACTION.equals(typeAction)) {
					// Action search & sort & paging
//					deviceName = request.getParameter("deviceName");

					// Action sort & paging
					if (Constants.SORT_ACTION.equals(typeAction) || Constants.PAGING_ACTION.equals(typeAction)) {
						currentPage = Common.convertStringToInteger(request.getParameter(Constants.PAGE), 1);
						sortByDeviceName = request.getParameter("sortByDeviceName");
					}
				}

			// Lấy tổng số bản ghi device trong DB
			int totalDevice = deviceLogicImpl.getTotalDevicesInFile(fileID);
			// Nếu trong DB có bản ghi
			if (totalDevice > 0) {
				// Lấy tổng số page
				int totalPage = Common.getTotalPage(totalDevice, limit);
				// Nếu trang hiện tại mà lớn hơn tổng số page(Trường hợp xóa bản ghi page cuối)
				if (currentPage > totalPage) {
					currentPage = totalPage;
				}
				// Lấy offset
				offset = Common.getOffset(currentPage, limit);
				// Lấy chuỗi paging
				listPaging = Common.getListPaging(totalDevice, limit, currentPage);
				// check listPaging có giá trị thì mới hiển thị
				if (!listPaging.isEmpty()) {
					// Nếu phần tử đầu tiên trong listPaging > limitPage thì hiển thị <<
					if (listPaging.get(0) > Constants.LIMIT_PAGE) {
						previousPage = listPaging.get(0) - Constants.LIMIT_PAGE;
						request.setAttribute(Constants.PREVIOUS, Constants.ICON_PREVIOUS);
						request.setAttribute(Constants.PREVIOUS_PAGE, previousPage);
					}
					// Nếu phần tử cuối cùng trong listPaging < totalPage thì hiển thị >>
					if (listPaging.get(listPaging.size() - 1) < totalPage) {
						nextPage = listPaging.get(listPaging.size() - 1) + 1;
						request.setAttribute(Constants.NEXT, Constants.ICON_NEXT);
						request.setAttribute(Constants.NEXT_PAGE, nextPage);
					}
					// set listPaging lên request để hiển thị
					request.setAttribute(Constants.LIST_PAGING, listPaging);
				}
				// Lấy danh sách user hiển thị
				listDevices = deviceLogicImpl.getListDevicesInFile(offset, limit, fileID, sortByDeviceName);
				// Set danh sách user lấy được lên request để hiển thị
				request.setAttribute("listDevices", listDevices);
			} else {
				// Nếu trong DB không có bản ghi
				// Lấy câu thông báo không tìm thấy user
				String noUserFoundMess = MessageProperties.getMessage(Constants.NO_USER_FOUND);
				request.setAttribute(Constants.NO_USER_FOUND_MESS, noUserFoundMess);
			}
			// Set các giá trị lên session để lưu lại giá trị cho trường hợp back
			session.setAttribute("fileID", fileID);
			session.setAttribute(Constants.CURRENT_PAGE, currentPage);
			session.setAttribute("sortByDeviceName", sortByDeviceName);
			session.setAttribute("totalDevice", totalDevice);
			// Forward đến màn hình ADM002
			RequestDispatcher req = request.getRequestDispatcher(Constants.LIST_DEVICE_INFILE_JSP);
			req.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển lỗi
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}
}
