package medical.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import medical.entities.File;
import medical.entities.User;
import medical.utils.Common;
import medical.utils.Constants;

/**
 * Implement UserDao, thao tác với cơ sở dữ liệu liên quan đến bảng tbl_user
 *
 *
 */
public class UserDaoImpl extends BaseDaoImpl {

	public User getUserByLoginName(String loginName) throws SQLException, ClassNotFoundException {
		User user = null;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT user_id, role ");
				sqlCommand.append("FROM user WHERE login_name = ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setString(++index, loginName);

				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					user = new User();
					user.setLoginName(loginName);
					user.setUserId(rs.getInt("user_id"));
					user.setRole(rs.getString("role"));
				}
			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
		return user;
	}

	public User getUserByLoginName(String loginName, int rule) throws SQLException, ClassNotFoundException {
		User user = null;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT login_name, password, salt, role FROM user ");
				sqlCommand.append("WHERE login_name = BINARY ? ");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				ps.setString(++index, loginName);
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					user = new User();
					user.setLoginName(rs.getString("login_name"));
					user.setPassword(rs.getString("password"));
					user.setSalt(rs.getString("salt"));
					user.setRole(rs.getString("role"));
				}
			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
		return user;
	}

	public int getTotalUsers(String fullName) throws SQLException, ClassNotFoundException {
		int totalRecords = 0;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT COUNT(user_id) AS total_user FROM user u ");
				sqlCommand.append("WHERE 1 = 1 ");

				// Nếu có nhập fullName
				if (!Common.checkIsEmpty(fullName)) {
					sqlCommand.append("AND u.full_name like ? ");
				}
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());

				// Set param
				if (!fullName.isEmpty()) {
					ps.setString(++index, "%" + fullName + "%");
				}
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					totalRecords = rs.getInt("total_user");
				}
			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
		return totalRecords;
	}

	public List<User> getListUsers(int offset, int limit, String fullName, String sortByFullName)
			throws SQLException, ClassNotFoundException {
		List<User> listUsers = new ArrayList<>();
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append(
						"SELECT u.user_id, u.full_name, u.email, u.mobile, u.address, u.role, u.department ");
				sqlCommand.append("FROM user u ");
				sqlCommand.append("WHERE 1 = 1 ");

				// Nếu có nhập fullName
				if (!Common.checkIsEmpty(fullName)) {
					sqlCommand.append("AND u.full_name like ? ");
				}

				sqlCommand.append("ORDER BY ");
				sqlCommand.append("u.full_name " + sortByFullName);

				sqlCommand.append(" LIMIT ? OFFSET ?");

				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());

				// set param
				if (!Common.checkIsEmpty(fullName)) {
					ps.setString(++index, "%" + fullName + "%");
				}
				ps.setInt(++index, limit);
				ps.setInt(++index, offset);
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					// Khởi tạo đối tượng UserInfor
					User user = new User();
					user.setUserId(rs.getInt("user_id"));
					user.setRole(rs.getString("role"));
					user.setFullName(rs.getString("full_name"));
					user.setMobile(rs.getString("mobile"));
					user.setAddress(rs.getString("address"));
					user.setEmail(rs.getString("email"));
					user.setDepartment(rs.getString("department"));
					listUsers.add(user);
				}
			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
		return listUsers;
	}

	public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
		List<User> listUsers = new ArrayList<>();
		try {
			if (connectDB()) {
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT u.user_id, u.full_name ");
				sqlCommand.append("FROM user u");

				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());

				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					// Khởi tạo đối tượng UserInfor
					User user = new User();
					user.setUserId(rs.getInt("user_id"));
					user.setFullName(rs.getString("full_name"));
					listUsers.add(user);
				}
			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
		return listUsers;
	}

	public User getUserById(int userId) throws SQLException, ClassNotFoundException {
		User user = null;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append(
						"SELECT u.user_id, u.full_name, u.email, u.department, u.mobile, u.address, u.login_name, u.role ");
				sqlCommand.append("FROM user u ");
				sqlCommand.append("WHERE u.user_id = ? ");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setInt(++index, userId);
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					// Khởi tạo đối tượng UserInfor
					user = new User();
					// Set các giá trị từ đối tượng ResultSet cho UserInfor
					user.setUserId(rs.getInt("user_id"));
					user.setLoginName(rs.getString("login_name"));
					user.setDepartment(rs.getString("department"));
					user.setRole(rs.getString("role"));
					user.setFullName(rs.getString("full_name"));
					user.setEmail(rs.getString("email"));
					user.setMobile(rs.getString("mobile"));
					user.setAddress(rs.getString("address"));
				}
			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
		return user;
	}

	public User getUserByEmail(String email) throws SQLException, ClassNotFoundException {
		User user = null;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT user_id, email ");
				sqlCommand.append("FROM user WHERE email = ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setString(++index, email);
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					user = new User();
					user.setUserId(rs.getInt("user_id"));
				}
			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
		return user;
	}

	public String getRoleByUserId(int userId) throws SQLException, ClassNotFoundException {
		String role = "";
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT role FROM user ");
				sqlCommand.append("WHERE user_id = ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setInt(++index, userId);
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					role = rs.getString("role");
				}
			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
		return role;
	}

	public boolean insertUser(User user) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
		boolean insert = false;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("INSERT INTO user(full_name, password, login_name, ");
				sqlCommand.append("email, mobile, address, department, role, salt )");
				sqlCommand.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				
				user.setSalt(Common.getSalt());
				
				// Set param
				ps.setString(++index, user.getFullName());
				ps.setString(++index, Common.encryptPassword(user.getPassword(), user.getSalt()));
				ps.setString(++index, user.getLoginName());
				ps.setString(++index, user.getEmail());
				ps.setString(++index, user.getMobile());
				ps.setString(++index, user.getAddress());
				ps.setString(++index, user.getDepartment());
				ps.setString(++index, user.getRole());
				ps.setString(++index, user.getSalt());
				ps.executeUpdate();
				insert = true;
			}
		} catch (SQLException se) {
			insert = false;
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
		return insert;
	}

	public void updateUser(User user) throws SQLException, ClassNotFoundException {
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("UPDATE user SET department = ?, full_name = ?, role = ?, ");
				sqlCommand.append("address = ?, email = ?, mobile = ? ");
				sqlCommand.append("WHERE user_id = ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setString(++index, user.getDepartment());
				ps.setString(++index, user.getFullName());
				ps.setString(++index, user.getRole());
				ps.setString(++index, user.getAddress());
				ps.setString(++index, user.getEmail());
				ps.setString(++index, user.getMobile());
				ps.setInt(++index, user.getUserId());
				ps.executeUpdate();
			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
	}

	public void deleteUser(int userId) throws SQLException, ClassNotFoundException {
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT file_id FROM file ");
				sqlCommand.append("WHERE user_id = ?;");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setInt(++index, userId);
				rs = ps.executeQuery();
				while (rs.next()) {
					// Khởi tạo đối tượng UserInfor
					int fileId = 0;
					fileId = rs.getInt("file_id");
					StringBuilder sql = new StringBuilder();
					sql.append("DELETE FROM device ");
					sql.append("WHERE dv_file = ?;");
					// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
					ps = conn.prepareStatement(sql.toString());
					// Set param
					index =0;
					ps.setInt(++index, fileId);
					ps.executeUpdate();
				}
				StringBuilder sql = new StringBuilder();
				sql.append("DELETE FROM file ");
				sql.append("WHERE file.user_id = ?;");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấnuser_id = user.user_id an
				ps = conn.prepareStatement(sql.toString());
				// Set param
				index =0;
				ps.setInt(++index, userId);
				ps.executeUpdate();
				StringBuilder query = new StringBuilder();
				query.append("DELETE FROM loghistory ");
				query.append("WHERE user_id = ?;");
				ps = conn.prepareStatement(query.toString());
				// Set param
				index=0;
				ps.setInt(++index, userId);
				ps.executeUpdate();
				StringBuilder q = new StringBuilder();
				q.append("DELETE FROM user ");
				q.append("WHERE user_id = ?;");
				ps = conn.prepareStatement(q.toString());
				// Set param
				index=0;
				ps.setInt(++index, userId);
				ps.executeUpdate();
			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
	}

	/**
	 * @param fileId
	 */
	private void deleteDevice(int fileId) throws SQLException, ClassNotFoundException{
		// TODO Auto-generated method stub
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("DELETE FROM device ");
				sqlCommand.append("WHERE dv_file = ?;");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setInt(++index, fileId);
				ps.executeUpdate();

			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
		
	}

	/**
	 * @param fileId
	 */
	private void deleteFile(int userId) throws SQLException, ClassNotFoundException{
		// TODO Auto-generated method stub
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("DELETE FROM file ");
				sqlCommand.append("WHERE file.user_id = ?;");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấnuser_id = user.user_id an
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setInt(++index, userId);
				ps.executeUpdate();
			}
		} catch (SQLException se) {
			// Ghi log và ném ngoại lệ
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		} finally {
			// Đóng Connection
			closeConnection();
		}
	}

	/**
	 * Lấy câu query OrderBy
	 * 
	 * @param String sortType - column được ưu tiên sort trước
	 * @param String sortByFullName - kiểu sort của fullName
	 * @param String sortByCodeLevel - kiểu sort của fullName
	 * @param String sortByEndDate - kiểu sort của fullName
	 * @return String - câu query OrderBy
	 */

	public String getQueryOrderBy(String sortType, String sortByFullName, String sortByCodeLevel,
			String sortByEndDate) {
		// Chuỗi StringBuilder chứa câu truy vấn
		StringBuilder sqlCommand = new StringBuilder();
		// Chia điều kiện sort ưu tiên
		switch (sortType) {
		// ưu tiên theo full_name
		case Constants.FULLNAME:
			sqlCommand.append("ORDER BY ");
			sqlCommand.append("u.full_name " + sortByFullName);
			sqlCommand.append("," + "j.code_level " + sortByCodeLevel);
			sqlCommand.append("," + "dj.end_date " + sortByEndDate);
			break;
		// ưu tiên theo code_level
		case Constants.CODE_LEVEL:
			sqlCommand.append("ORDER BY ");
			sqlCommand.append("j.code_level " + sortByCodeLevel);
			sqlCommand.append("," + "u.full_name " + sortByFullName);
			sqlCommand.append("," + "dj.end_date " + sortByEndDate);
			break;
		// ưu tiên theo end_date
		case Constants.END_DATE:
			sqlCommand.append("ORDER BY ");
			sqlCommand.append("dj.end_date " + sortByEndDate);
			sqlCommand.append("," + "u.full_name " + sortByFullName);
			sqlCommand.append("," + "j.code_level " + sortByCodeLevel);
			break;
		}
		// Trả về câu query dạng String
		return sqlCommand.toString();
	}
}
