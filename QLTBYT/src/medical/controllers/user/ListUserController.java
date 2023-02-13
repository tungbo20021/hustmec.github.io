package medical.controllers.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import medical.dao.UserDaoImpl;
import medical.entities.User;
import medical.utils.Common;
import medical.utils.Constants;
import medical.utils.MessageProperties;

/**
 * 
 * Controller để xử lý cho màn hình ADM002
 *
 *
 */
public class ListUserController extends HttpServlet {
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

			List<User> listUsers = new ArrayList<>();
			List<Integer> listPaging = new ArrayList<>();
			UserDaoImpl userLogicImpl = new UserDaoImpl();

			// Set các giá trị trường hợp login thành công
			String fullName = Constants.BLANK;
			int offset = Constants.DEFAULT_VALUE;
			int limit = Constants.LIMIT_RECORD;
			String sortByFullName = Constants.ASC;
			int previousPage = Constants.DEFAULT_VALUE;
			int nextPage = Constants.DEFAULT_VALUE;
			int currentPage = 1;

			// Lấy action từ request
			String typeAction = request.getParameter(Constants.ACTION);
			if (Constants.BACK_ACTION.equals(typeAction)) {
				// Action back từ màn hình ADM003
				// Lấy các dữ liệu từ session
				fullName = (String) session.getAttribute(Constants.FULLNAME);
				currentPage = (int) session.getAttribute(Constants.CURRENT_PAGE);
				sortByFullName = (String) session.getAttribute(Constants.SORT_BY_FULL_NAME);
			} else if (Constants.SEARCH_ACTION.equals(typeAction) || Constants.SORT_ACTION.equals(typeAction)
					|| Constants.PAGING_ACTION.equals(typeAction)) {
				// Action search & sort & paging
				fullName = request.getParameter(Constants.FULLNAME);

				// Action sort & paging
				if (Constants.SORT_ACTION.equals(typeAction) || Constants.PAGING_ACTION.equals(typeAction)) {
					currentPage = Common.convertStringToInteger(request.getParameter(Constants.PAGE), 1);
					sortByFullName = request.getParameter(Constants.SORT_BY_FULL_NAME);
				}
			}

			// Lấy tổng số bản ghi user trong DB
			int totalUser = userLogicImpl.getTotalUsers(fullName);
			// Nếu trong DB có bản ghi
			if (totalUser > 0) {
				// Lấy tổng số page
				int totalPage = Common.getTotalPage(totalUser, limit);
				// Nếu trang hiện tại mà lớn hơn tổng số page(Trường hợp xóa bản ghi page cuối)
				if (currentPage > totalPage) {
					currentPage = totalPage;
				}
				// Lấy offset
				offset = Common.getOffset(currentPage, limit);
				// Lấy chuỗi paging
				listPaging = Common.getListPaging(totalUser, limit, currentPage);
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
				listUsers = userLogicImpl.getListUsers(offset, limit, fullName, sortByFullName);
				// Set danh sách user lấy được lên request để hiển thị
				request.setAttribute("listUsers", listUsers);
			} else {
				// Nếu trong DB không có bản ghi
				// Lấy câu thông báo không tìm thấy user
				String noUserFoundMess = MessageProperties.getMessage(Constants.NO_USER_FOUND);
				request.setAttribute(Constants.NO_USER_FOUND_MESS, noUserFoundMess);
			}
			// Set các giá trị lên session để lưu lại giá trị cho trường hợp back
			session.setAttribute(Constants.FULLNAME, fullName);
			session.setAttribute(Constants.CURRENT_PAGE, currentPage);
			session.setAttribute(Constants.SORT_BY_FULL_NAME, sortByFullName);
			session.setAttribute("totalUser", totalUser);
			// Forward đến màn hình ADM002
			RequestDispatcher req = request.getRequestDispatcher(Constants.LIST_USER_JSP);
			req.forward(request, response);
		} catch (Exception e) {
			// Ghi log
			e.printStackTrace();
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển lỗi
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}

	}
}
