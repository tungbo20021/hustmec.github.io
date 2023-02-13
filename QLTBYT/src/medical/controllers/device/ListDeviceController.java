package medical.controllers.device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import medical.entities.Device;
import medical.logics.DeviceLogicImpl;
import medical.utils.Common;
import medical.utils.Constants;
import medical.utils.MessageProperties;

public class ListDeviceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Hiển thị giá trị ở MH ADM002: Có 3 trường hợp TH1: sau khi login thành công
	 * TH2: CLick button [Search] / [Sort] / [Paging] Th3: Click button [Back] ở màn
	 * hình ADM003
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo các đối tượng
			HttpSession session = request.getSession();
			List<Device> listDevices = new ArrayList<>();
			List<Integer> listPaging = new ArrayList<>();
			DeviceLogicImpl deviceLogicImpl = new DeviceLogicImpl();


			String deviceName = Constants.BLANK;
			int offset = Constants.DEFAULT_VALUE;
			int limit = Constants.LIMIT_RECORD;
			String sortByDeviceName = Constants.ASC;
			int previousPage = Constants.DEFAULT_VALUE;
			int nextPage = Constants.DEFAULT_VALUE;
			int currentPage = 1;
			int userId = (int) session.getAttribute("userIdFile");

			// Lấy action từ request
			String typeAction = request.getParameter(Constants.ACTION);
			boolean isAdmin = (boolean) session.getAttribute("isAdmin");
//			String userIdFromRequest = request.getParameter(Constants.USER_ID);
			// Parse userId sang Integer
//			int userId = Common.convertStringToInteger(userIdFromRequest, Constants.DEFAULT_VALUE);
				if (Constants.BACK_ACTION.equals(typeAction)) {
					// Action back từ màn hình ADM003
					// Lấy các dữ liệu từ session
					deviceName = (String) session.getAttribute("deviceName");

					currentPage = (int) session.getAttribute(Constants.CURRENT_PAGE);
					sortByDeviceName = (String) session.getAttribute(sortByDeviceName);
				} else if (Constants.SEARCH_ACTION.equals(typeAction) || Constants.SORT_ACTION.equals(typeAction)
						|| Constants.PAGING_ACTION.equals(typeAction)) {
					// Action search & sort & paging
					deviceName = request.getParameter("deviceName");

					// Action sort & paging
					if (Constants.SORT_ACTION.equals(typeAction) || Constants.PAGING_ACTION.equals(typeAction)) {
						currentPage = Common.convertStringToInteger(request.getParameter(Constants.PAGE), 1);
						sortByDeviceName = request.getParameter("sortByDeviceName");
					}
				}

			// Lấy tổng số bản ghi device trong DB
			int totalDevice = deviceLogicImpl.getTotalDevices(deviceName);
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
				listDevices = deviceLogicImpl.getListDevices(offset, limit, deviceName, sortByDeviceName);
				// Set danh sách user lấy được lên request để hiển thị
				request.setAttribute("listDevices", listDevices);
			} else {
				// Nếu trong DB không có bản ghi
				// Lấy câu thông báo không tìm thấy user
				String noUserFoundMess = MessageProperties.getMessage(Constants.NO_USER_FOUND);
				request.setAttribute(Constants.NO_USER_FOUND_MESS, noUserFoundMess);
			}
			// Set các giá trị lên session để lưu lại giá trị cho trường hợp back
			session.setAttribute("deviceName", deviceName);
			session.setAttribute(Constants.CURRENT_PAGE, currentPage);
			session.setAttribute("sortByDeviceName", sortByDeviceName);
			session.setAttribute("totalDevice", totalDevice);
			// Forward đến màn hình ADM002
			RequestDispatcher req = request.getRequestDispatcher(Constants.LIST_DEVICE_JSP);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DeviceLogicImpl productModel = new DeviceLogicImpl();
		String path = Constants.SYSTEM_ERROR_DO;
		String[]id = new String[1000];
		String[]name = new String[1000];
		String[]department = new String[1000];
		String[]brand = new String[1000];
		String[]fun = new String[1000];
		String[]app = new String[1000];
		String[]main = new String[1000];
		String[]histo = new String[1000];
		String[]em = new String[1000];
		String[]productDate = new String[1000];
		String[]importDate = new String[1000];
		String[]status = new String[1000];
		String[]type = new String[1000];
		String[]fre = new String[1000];

		if(request.getParameter("id") != null) 
		       id = request.getParameterValues("id");
				
		   if(request.getParameter("name") != null)
			name = request.getParameterValues("name");
				
		   if(request.getParameter("department") != null) 
			   department = request.getParameterValues("department");
				
		   if(request.getParameter("brand") != null) 
			   brand = request.getParameterValues("brand");
		   if(request.getParameter("productDate") != null) 
			   productDate = request.getParameterValues("productDate");
		   if(request.getParameter("importDate") != null) 
			   importDate = request.getParameterValues("importDate");
		   if(request.getParameter("status") != null) 
			   status = request.getParameterValues("status");
			   fun = request.getParameterValues("funcScore");
				
			   app = request.getParameterValues("appScore");
		   
			   main = request.getParameterValues("maintainScore");
				
			   histo = request.getParameterValues("histoScore");
				
			   em = request.getParameterValues("emScore");
			   type = request.getParameterValues("type");
			   fre = request.getParameterValues("fre");

		   for(int i = 0;i < id.length;i++) {
				Device product = new Device();
		        if(id[i] != null)
		        {
		        	product.setDeviceId(Common.convertStringToInteger(id[i], Constants.DEFAULT_VALUE));
					product.setDeviceName(name[i]);
					product.setDepartment(department[i]);
		      		product.setBrand(brand[i]);
		      		product.setProductDate(productDate[i]);
		      		product.setImportDate(importDate[i]);
		      		product.setStatus(status[i]);
					product.setFuncScore(Common.convertStringToInteger(fun[i], Constants.DEFAULT_VALUE));
					product.setAppScore(Common.convertStringToInteger(app[i], Constants.DEFAULT_VALUE));
		      		product.setMaintainScore(Common.convertStringToInteger(main[i], Constants.DEFAULT_VALUE));
					product.setHistoScore(Common.convertStringToInteger(histo[i], Constants.DEFAULT_VALUE));
					product.setEmScore(Common.convertStringToInteger(em[i], Constants.DEFAULT_VALUE));
					product.setType(type[i]);
					product.setFre(fre[i]);
					try {
						if (productModel.updateDevice(product)) {
							request.setAttribute("msg", "Successful");
							path = Constants.SUCCESS_URL + "?" + Constants.MESS + "=" + Constants.UPDATE_DEVICE_SUCCESSFUL;
						} else {
							request.setAttribute("msg", "Failed");
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        else
		        {
		        	break;
		        }
		  }
		response.sendRedirect(request.getContextPath() + "/ListDevice.do?");
//		req.forward(request, response);
		}
}
