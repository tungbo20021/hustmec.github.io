package medical.validates;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import medical.entities.User;
import medical.logics.UserLogicImpl;
import medical.utils.Common;
import medical.utils.Constants;
import medical.utils.MessageProperties;

/**
 * Validate(xác thực) các hạng mục liên quan đến User
 * 
 */
public class UserValidate {

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
	 * Validate các hạng mục ở màn hình ADM003
	 * 
	 * @param user - Đối tượng UserInfor
	 * @return listErrorMess - list chứa các câu thông báo validate
	 * @throws ClassNotFoundException - Ngoại lệ ClassNotFoundException
	 * @throws SQLException           - Ngoại lệ SQLException
	 */
	public List<String> validateUser(User user) throws ClassNotFoundException, SQLException {
		// Khởi tạo list chứa các câu thông báo validate
		List<String> listErrorMess = new ArrayList<String>();
		// Add câu thông báo lỗi sau khi validate hạng mục fullName
		Common.addStringToList(listErrorMess, validateFullName(user.getFullName()));
		// Nếu userId = 0(Trường hợp Add mới) thì validate hạng mục loginName
		if (user.getUserId() == 0) {
			// Add câu thông báo lỗi sau khi validate hạng mục loginName
			Common.addStringToList(listErrorMess, validateLoginName(user.getLoginName()));
		}
		// Nếu là trường hợp add mới thì validate hạng mục password & passwordConfirm
		if (user.getUserId() == 0) {
			// Add câu thông báo lỗi sau khi validate hạng mục password
			Common.addStringToList(listErrorMess, validatePassword(user.getPassword()));
			if (!user.getPassword().isEmpty()) {
				Common.addStringToList(listErrorMess,
						validatePasswordConfirm(user.getPassword(), user.getPasswordConfirm()));
			}
		}

		// Add câu thông báo lỗi sau khi validate hạng mục group
		Common.addStringToList(listErrorMess, validateDepartment(user.getDepartment()));
		// Add câu thông báo lỗi sau khi validate hạng mục địa chỉ
		Common.addStringToList(listErrorMess, validateAddress(user.getAddress()));
		// Add câu thông báo lỗi sau khi validate hạng mục tel
		Common.addStringToList(listErrorMess, validateTel(user.getMobile()));
		// Add câu thông báo lỗi sau khi validate hạng mục email
		Common.addStringToList(listErrorMess, validateEmail(user.getEmail(), user.getUserId()));
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
	public String validateDepartment(String department) throws ClassNotFoundException, SQLException {
		// Khai báo chuỗi chứa câu thông báo validate
		String error = "";
		// Nếu không chọn group nào
		if (Common.checkIsEmpty(department)) {
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
		String error = "";
		if (Common.checkIsEmpty(fullName)) {
			error = MessageProperties.getMessage(Constants.ER001_FULL_NAME);
		} else if (!Common.checkLengthRange(5, 100, fullName)) {
			error = MessageProperties.getMessage("ER007_FULL_NAME");
		}
		return error;
	}

	public String validateAddress(String address) {
		String error = "";
		if (Common.checkIsEmpty(address)) {
			error = MessageProperties.getMessage("ER001_ADDRESS");
		}
		return error;
	}

	/**
	 * Validate hạng mục email
	 * 
	 * @param String email - email user
	 * @param int    userId - id của user
	 * @return String error - thông báo lỗi
	 * @throws SQLException           - Ngoại lệ SQLException
	 * @throws ClassNotFoundException - Ngoại lệ ClassNotFoundException
	 */
	private String validateEmail(String email, int userId) throws ClassNotFoundException, SQLException {
		// Khai báo chuỗi chứa câu thông báo validate
		String error = "";
		// Check không nhập
		if (Common.checkIsEmpty(email)) {
			error = MessageProperties.getMessage(Constants.ER001_EMAIL);
			// Check maxlength
		} else if (!Common.checkMaxLength(Constants.MAX_VALUE_EMAIL, email)) {
			error = MessageProperties.getMessage(Constants.ER006_EMAIL_100);
			// Check format
		} else if (!Common.checkFormat(email, Constants.FORMAT_EMAIL)) {
			error = MessageProperties.getMessage(Constants.ER005_EMAIL);
		} else { // check email nhập có tồn tại trong DB hay không
			// Khởi tạo TblUserLogic
			UserLogicImpl userLogicImpl = new UserLogicImpl();
			// userId = 0 (Trường hợp add mới)
			if (userId == 0) {
				// Nếu đã tồn tại email thì lấy câu thông báo email đã tồn tại
				if (userLogicImpl.checkExistEmail(email)) {
					error = MessageProperties.getMessage(Constants.ER003_EMAIL);
				}
			} else { // Trường hợp edit
				// Nếu đã tồn tại email thì lấy câu thông báo email đã tồn tại
				if (userLogicImpl.checkExistEmail(email, userId)) {
					error = MessageProperties.getMessage(Constants.ER003_EMAIL);
				}
			}
		}
		// Trả về câu thông báo
		return error;
	}

	/**
	 * Validate hạng mục tel
	 * 
	 * @param tel - số điện thoại của user
	 * @return String error - thông báo lỗi
	 */
	private String validateTel(String tel) {
		// Khai báo chuỗi chứa câu thông báo validate
		String error = "";
		// Check không nhập
		if (Common.checkIsEmpty(tel)) {
			error = MessageProperties.getMessage(Constants.ER001_TEL);
			// Check maxlength
		} else if (!Common.checkMaxLength(Constants.MAX_VALUE_TEL, tel)) {
			error = MessageProperties.getMessage(Constants.ER006_TEL_14);
			// Check format
		} else if (!Common.checkFormat(tel, Constants.FORMAT_TEL)) {
			error = MessageProperties.getMessage(Constants.ER005_TEL);
		}
		// Trả về câu thông báo
		return error;
	}

	/**
	 * Validate hạng mục password
	 * 
	 * @param password - mật khẩu của user
	 * @return String error - thông báo lỗi
	 */
	private String validatePassword(String password) {
		// Khai báo chuỗi chứa câu thông báo validate
		String error = "";
		// Check không nhập
		if (Common.checkIsEmpty(password)) {
			error = MessageProperties.getMessage(Constants.ER001_PASSWORD);
			// Check trong khoảng
		} else if (!Common.checkLengthRange(Constants.MIN_VALUE_PASSWORD, Constants.MAX_VALUE_PASSWORD, password)) {
			error = MessageProperties.getMessage(Constants.ER007_PASSWORD);
			// Check có phải kí tự 1 byte
		} else if (!Common.checkCharacterHalfSize(password)) {
			error = MessageProperties.getMessage(Constants.ER008_PASSWORD);
		}
		// Trả về câu thông báo
		return error;
	}

	/**
	 * Validate hạng mục nhập lại password
	 * 
	 * @param passwordConfirm - mật khẩu xác nhận trên form
	 * @return String error - thông báo lỗi
	 */
	private String validatePasswordConfirm(String password, String passwordConfirm) {
		// Khai báo chuỗi chứa câu thông báo validate
		String error = "";
		// Nếu passwordConfirm không giống password
		if (!Common.compareString(password, passwordConfirm)) {
			error = MessageProperties.getMessage(Constants.ER017);
		}
		// Trả về câu thông báo
		return error;
	}
}
