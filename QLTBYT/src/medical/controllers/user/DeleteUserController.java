package medical.controllers.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import medical.logics.UserLogicImpl;
import medical.utils.Common;
import medical.utils.Constants;

/**
 * 
 * Controller để xử lý cho chức năng delete
 *
 *
 */
public class DeleteUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// url redirect
			String path = "";
			// Khởi tạo các đối tượng
			UserLogicImpl userLogicImpl = new UserLogicImpl();
			// Lấy userId về từ request và parse sang int
			int userId = Common.convertStringToInteger(request.getParameter(Constants.USER_ID),
					Constants.DEFAULT_VALUE);
			// Nếu parse userId sang int thành công và userId đó có tồn tại trong DB
			if (userId > 0 && userLogicImpl.checkExistUserId(userId)) {
				// Nếu user đó không phải là admin
				if (!userLogicImpl.checkAdminByUserId(userId)) {
					// Gọi method deleteUser
					boolean delete = userLogicImpl.deleteUser(userId);
					// Nếu delete user thành công
					if (delete) {
						// Lấy đường dẫn đến SuccessController với câu thông báo xóa thành công
						path = Constants.SUCCESS_URL + "?" + Constants.MESS + "=" + Constants.DELETE_SUCCESSFUL;
					} else {
						// Nếu delete thất bại
						// Lấy đường dẫn đến SystemErrorController
						path = Constants.SYSTEM_ERROR_DO;
					}
				} else {
					// Nếu user đó là admin
					// Lấy đường dẫn đến SystemErrorController với câu thông báo không thể xóa admin
					path = Constants.SYSTEM_ERROR_DO + "?" + Constants.MESS + "=" + Constants.CANT_DELETE_ADMIN;
				}
			} else {
				// Nếu không parse được userId hoặc userId không tồn tại trong DB
				// Lấy đường dẫn đến SystemErrorController với câu thông báo user không tồn tại
				path = Constants.SYSTEM_ERROR_DO + "?" + Constants.MESS + "=" + Constants.USER_NOT_EXIST;
			}
			// Redirect đến url tương ứng trong mỗi trường hợp
			response.sendRedirect(request.getContextPath() + path);
		} catch (Exception e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Redirect đến controller điều khiển màn hình lỗi hệ thống
			response.sendRedirect(request.getContextPath() + Constants.SYSTEM_ERROR_DO);
		}
	}
}
