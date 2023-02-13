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

import medical.entities.File;
import medical.logics.FileLogicImpl;
import medical.utils.Common;
import medical.utils.Constants;
import medical.utils.MessageProperties;

public class ListFileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// Khởi tạo các đối tượng
			HttpSession session = request.getSession();
			List<File> listFiles = new ArrayList<>();
			List<Integer> listPaging = new ArrayList<>();
			FileLogicImpl fileLogicImpl = new FileLogicImpl();

//			String logTime = Constants.BLANK;
			int offset = Constants.DEFAULT_VALUE;
			int limit = Constants.LIMIT_RECORD;
			int previousPage = Constants.DEFAULT_VALUE;
			int nextPage = Constants.DEFAULT_VALUE;
			int currentPage = 1;

			// Lấy action từ request
			String typeAction = request.getParameter(Constants.ACTION);
			boolean isAdmin = (boolean) session.getAttribute("isAdmin");
			int userId = (int) session.getAttribute("userIdFile");

				if (Constants.BACK_ACTION.equals(typeAction)) {
					// Action back từ màn hình ADM003
					// Lấy các dữ liệu từ session
//					logTime = (String) session.getAttribute("logTime");

					currentPage = (int) session.getAttribute(Constants.CURRENT_PAGE);
				} else if (Constants.SEARCH_ACTION.equals(typeAction) || Constants.SORT_ACTION.equals(typeAction)
						|| Constants.PAGING_ACTION.equals(typeAction)) {
					// Action search & sort & paging
//					logTime = request.getParameter("logTime");

					// Action sort & paging
					if (Constants.SORT_ACTION.equals(typeAction) || Constants.PAGING_ACTION.equals(typeAction)) {
						currentPage = Common.convertStringToInteger(request.getParameter(Constants.PAGE), 1);
					}
				}

			// Lấy tổng số bản ghi device trong DB
			int totalFile = fileLogicImpl.getTotalFile();
			// Nếu trong DB có bản ghi
			if (totalFile > 0) {
				// Lấy tổng số page
				int totalPage = Common.getTotalPage(totalFile, limit);
				// Nếu trang hiện tại mà lớn hơn tổng số page(Trường hợp xóa bản ghi page cuối)
				if (currentPage > totalPage) {
					currentPage = totalPage;
				}
				// Lấy offset
				offset = Common.getOffset(currentPage, limit);
				// Lấy chuỗi paging
				listPaging = Common.getListPaging(totalFile, limit, currentPage);
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
				
				if (isAdmin) {
				
				// Lấy danh sách file hiển thị
				listFiles = fileLogicImpl.getListFiles(offset, limit);
				} else {
					listFiles = fileLogicImpl.getListFilesOfUser(offset, limit, userId);
				}
				// Set danh sách user lấy được lên request để hiển thị
				request.setAttribute("listFiles", listFiles);
			} else {
				// Nếu trong DB không có bản ghi
				// Lấy câu thông báo không tìm thấy user
				String noUserFoundMess = MessageProperties.getMessage(Constants.NO_USER_FOUND);
				request.setAttribute(Constants.NO_USER_FOUND_MESS, noUserFoundMess);
			}
			// Set các giá trị lên session để lưu lại giá trị cho trường hợp back
			session.setAttribute(Constants.CURRENT_PAGE, currentPage);
			session.setAttribute("totalFile", totalFile);
			// Forward đến màn hình ADM002
			RequestDispatcher req = request.getRequestDispatcher(Constants.LIST_FILE_JSP);
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
