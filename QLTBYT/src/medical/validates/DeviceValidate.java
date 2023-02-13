package medical.validates;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import medical.logics.UserLogicImpl;
import medical.utils.Common;
import medical.utils.Constants;
import medical.utils.MessageProperties;

/**
 * Validate(xác thực) các hạng mục liên quan đến User
 *
 */
public class DeviceValidate {

	/**
	 * Validate account đăng nhập
	 * 
	 * @param loginName - tên đăng nhập
	 * @param password  - mật khẩu
	 * @return listErrorMess - list chứa các câu thông báo validate
	 * @throws SQLException             - Ngoại lệ SQLException
	 * @throws ClassNotFoundException   - Ngoại lệ ClassNotFoundException
	 * @throws NullPointerException     - Ngoại lệ NullPointerException
	 * @throws NoSuchAlgorithmException - Ngoại lệ NoSuchAlgorithmException
	 */
	public List<String> validateLogin(String loginName, String password)
			throws SQLException, ClassNotFoundException, NullPointerException, NoSuchAlgorithmException {
		// Khai báo danh sách chứa câu thông báo validate
		List<String> listErrorMess = new ArrayList<>();
		// Nếu không nhập loginName
		if (Common.checkIsEmpty(loginName)) {
			// Add câu thông báo chưa nhập loginName
			listErrorMess.add(MessageProperties.getMessage(Constants.ER001_LOGIN_NAME));
		}
		// Nếu không nhập password
		if (Common.checkIsEmpty(password)) {
			// Add câu thông báo chưa nhập password
			listErrorMess.add(MessageProperties.getMessage(Constants.ER001_PASSWORD));
		}
		// Nếu đã nhập cả hai hạng mục
		if (listErrorMess.size() == 0) {
			// Khởi tạo TblUserLogic
			UserLogicImpl userLogicImpl = new UserLogicImpl();
			// Nếu account không tồn tại trong DB
			if (!userLogicImpl.checkExistAccount(loginName, password)) {
				// Add câu thông báo tên tài khoản và mật khẩu không đúng
				listErrorMess.add(MessageProperties.getMessage(Constants.ER016_ACCOUNT_LOGIN));
			}
		}
		// Trả về listErrorMess
		return listErrorMess;
	}

	/**
	 * Validate hạng mục loginName
	 * 
	 * @param loginName - tên đăng nhập của user
	 * @return String error - thông báo lỗi validate
	 * @throws SQLException           - Ngoại lệ SQLException
	 * @throws ClassNotFoundException - Ngoại lệ ClassNotFoundException
	 */
	public String validateLoginName(String loginName) throws ClassNotFoundException, SQLException {
		// Khai báo chuỗi chứa câu thông báo validate
		String error = "";
		// Khởi tạo đối tượng TblUserLogic
		UserLogicImpl userLogicImpl = new UserLogicImpl();
		// Nếu không nhập loginName
		if (Common.checkIsEmpty(loginName)) {
			// Lấy câu thông báo lỗi
			error = MessageProperties.getMessage(Constants.ER001_LOGIN_NAME);
			// Nếu nhập loginName sai định dạng
		} else if (!Common.checkFormat(loginName, Constants.FORMAT_LOGIN_NAME)) {
			// Lấy câu thông báo lỗi
			error = MessageProperties.getMessage(Constants.ER019);
			// Nếu độ dài loginName không thỏa mãn
		} else if (!Common.checkLengthRange(Constants.MIN_VALUE_LOGIN_NAME, Constants.MAX_VALUE_LOGIN_NAME,
				loginName)) {
			// Lấy câu thông báo lỗi
			error = MessageProperties.getMessage(Constants.ER007_LOGIN_NAME);
			// Nếu loginName đã tồn tại trong DB
		} else if (userLogicImpl.checkExistLoginName(loginName)) {
			// Lấy câu thông báo lỗi
			error = MessageProperties.getMessage(Constants.ER003_LOGIN_NAME);
		}
		// Trả về câu thông báo
		return error;
	}

	/**
	 * Validate hạng mục Group
	 * 
	 * @param departmentId - Id của nhóm
	 * @return String error - thông báo lỗi validate
	 * @throws SQLException           - Ngoại lệ SQLException
	 * @throws ClassNotFoundException - Ngoại lệ ClassNotFoundException
	 */
	public String validateDepartment(int departmentId) throws ClassNotFoundException, SQLException {
		// Khai báo chuỗi chứa câu thông báo validate
		String error = "";
		// Nếu không chọn group nào
		if (departmentId == 0) {
			// Lấy câu thông báo lỗi
			error = MessageProperties.getMessage(Constants.NO_CHOOSE_DEPARTMENT);
		}
		// Trả về câu thông báo
		return error;
	}

	/**
	 * Validate hạng mục fullName
	 * 
	 * @param fullName - Tên đầy đủ của user
	 * @return String error - thông báo lỗi
	 */
	public String validateFullName(String fullName) {
		// Khai báo chuỗi chứa câu thông báo validate
		String error = "";
		// check không nhập
		if (Common.checkIsEmpty(fullName)) {
			error = MessageProperties.getMessage(Constants.ER001_FULL_NAME);
			// check vượt quá số kí tự max
		} else if (!Common.checkMaxLength(Constants.MAX_VALUE_STRING, fullName)) {
			error = MessageProperties.getMessage(Constants.ER006_FULL_NAME_255);
		}
		// Trả về câu thông báo
		return error;
	}

	public String validateEmpty(String input) {
		// Khai báo chuỗi chứa câu thông báo validate
		String error = "";
		// check không nhập
		if (Common.checkIsEmpty(input)) {
			error = MessageProperties.getMessage("ER001_DEVICE");
			// check vượt quá số kí tự max
		}
		// Trả về câu thông báo
		return error;
	}
}
