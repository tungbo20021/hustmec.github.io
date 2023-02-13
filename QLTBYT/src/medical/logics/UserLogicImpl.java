package medical.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import medical.dao.UserDaoImpl;
import medical.entities.User;
import medical.utils.Common;
import medical.utils.Constants;

/**
 * Implement UserLogic để Xử lý logic cho các chức năng liên quan đến tbl_user
 *
 */
public class UserLogicImpl {
	UserDaoImpl userDaoImpl = new UserDaoImpl();

	public boolean checkAdminByLoginName(String loginName) throws ClassNotFoundException, SQLException {
		boolean check = false;
//		 Lấy tblUser theo loginName
		User user = userDaoImpl.getUserByLoginName(loginName);
		// Nếu user vừa lấy được là admin thì trả về true
		if (user.getRole().contentEquals(Constants.RULE_ADMIN)) {
			check = true;
		}
		
		System.out.println(user.getRole() + "      ---    " + check);
		return check;
	}

	public boolean checkExistAccount(String loginName, String password)
			throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
		boolean check = false;
		// Lấy user có rule là admin theo login_name
		User user = userDaoImpl.getUserByLoginName(loginName, 1);
		// Nếu account có tồn tại
		if (user != null) {
			// Mã hóa password
			String passwordEncrypt = Common.encryptPassword(password, user.getSalt());
			// Kiểm tra so sánh password vừa mã hóa với password của admin lấy được
			check = Common.compareString(passwordEncrypt, user.getPassword());
		}
		return check;
	}

	public int getTotalUsers(String fullName) throws SQLException, ClassNotFoundException {
		// Replace kí tự wildcard cho fullName
		fullName = Common.replaceWildCard(fullName);
		// Lấy về tổng số bản ghi user
		int totalRecords = userDaoImpl.getTotalUsers(fullName);
		// Trả về tổng số bản ghi lấy được
		return totalRecords;
	}

	public List<User> getListUsers(int offset, int limit, String fullName, String sortByFullName)
			throws SQLException, ClassNotFoundException {
		// Replace kí tự wildcard cho fullName
		fullName = Common.replaceWildCard(fullName);
		// Gọi method getListUsers() trả về listUserInfor
		List<User> listUsers = userDaoImpl.getListUsers(offset, limit, fullName, sortByFullName);
		// Trả về listUserInfor
		return listUsers;
	}
	
	public List<User> getAllUsers()
			throws SQLException, ClassNotFoundException {
		// Replace kí tự wildcard cho fullName
		// Gọi method getListUsers() trả về listUserInfor
		List<User> listUsers = userDaoImpl.getAllUsers();
		// Trả về listUserInfor
		return listUsers;
	}

	public User getUserById(int userId) throws SQLException, ClassNotFoundException {
		// Gọi getUserInforByUserId bên DAO trả về 1 UserInfor
		User user = userDaoImpl.getUserById(userId);
		// Trả về userInfor
		return user;
	}
	
	public User getUserByLoginName(String loginName) throws SQLException, ClassNotFoundException {
		// Gọi getUserInforByUserId bên DAO trả về 1 UserInfor
		User user = userDaoImpl.getUserByLoginName(loginName);
		// Trả về userInfor
		return user;
	}

	public boolean checkExistLoginName(String loginName) throws SQLException, ClassNotFoundException {
		boolean check = false;
		// Lấy tblUser theo loginName
		User user = userDaoImpl.getUserByLoginName(loginName);
		// Nếu đã tồn tại user có loginName này trong DB thì check = true;
		if (user != null) {
			check = true;
		}
		return check;
	}

	public boolean checkExistEmail(String email) throws SQLException, ClassNotFoundException {
		boolean check = false;
		// Lấy tblUser theo email
		User user = userDaoImpl.getUserByEmail(email);
		// Nếu đã tồn tại user có email này trong DB thì check = true;
		if (user != null) {
			check = true;
		}
		return check;
	}

	public boolean checkExistEmail(String email, int userId) throws SQLException, ClassNotFoundException {
		boolean check = false;
		// Lấy tblUser theo email
		User user = userDaoImpl.getUserByEmail(email);
		// Nếu đã tồn tại user có email này trong DB thì check = true;
		if (user != null && user.getUserId() != userId) {
			check = true;
		}
		return check;
	}


	public boolean checkExistUserId(int userId) throws ClassNotFoundException, SQLException {
		boolean check = false;
		// Lấy về rule theo userId
		String role = userDaoImpl.getRoleByUserId(userId);
		// Nếu lấy được rule của user theo id thì check = true
		if (role != "") {
			check = true;
		}
		return check;
	}

	public boolean checkExistUser(int userId) throws ClassNotFoundException, SQLException {
		boolean check = false;
		// Lấy về rule theo userId
		String role = userDaoImpl.getRoleByUserId(userId);
		// Nếu là user thì check = true
		if (Constants.RULE_USER.equals(role) || Constants.RULE_ADMIN.equals(role)) {
			check = true;
		}
		return check;
	}

	public boolean checkAdminByUserId(int userId) throws ClassNotFoundException, SQLException {
		boolean check = false;
		// Lấy về rule theo userId
		String role = userDaoImpl.getRoleByUserId(userId);
		// Nếu là admin thì check = true
		if (Constants.RULE_ADMIN.equals(role)) {
			check = true;
		}
		return check;
	}

	public boolean createUser(User user) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
		boolean create = userDaoImpl.insertUser(user);
		return create;
	}

	public boolean updateUser(User user) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
		boolean update = false;
		// Thực hiện update user
		userDaoImpl.updateUser(user);
		update = true;
		// Trả về biến update
		return update;
	}

	public boolean deleteUser(int userId) throws SQLException, ClassNotFoundException {
		boolean delete = false;
		// Thực hiện delete user
		userDaoImpl.deleteUser(userId);
		delete = true;
		// Trả về biến delete
		return delete;
	}

}
