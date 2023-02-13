package medical.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import medical.entities.Device;
import medical.entities.User;
import medical.utils.Common;
import medical.utils.Constants;

public class DeviceDaoImpl extends BaseDaoImpl {

	public User getUserByLoginName(String loginName) throws SQLException, ClassNotFoundException {
		User user = null;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT login_name, role ");
				sqlCommand.append("FROM user WHERE login_name = ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setString(++index, loginName);
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					user = new User();
					user.setLoginName(rs.getString("login_name"));
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
				sqlCommand.append("SELECT login_name, password, salt FROM user ");
				sqlCommand.append("WHERE login_name = BINARY ? AND role = ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				ps.setString(++index, loginName);
				ps.setInt(++index, rule);
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					user = new User();
					user.setLoginName(rs.getString("login_name"));
					user.setPassword(rs.getString("password"));
					user.setSalt(rs.getString("salt"));
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

	public int getTotalDevices(String deviceName)
			throws SQLException, ClassNotFoundException {
		int totalRecords = 0;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT COUNT(dv_id) AS total_device FROM device dv ");
				sqlCommand.append("WHERE 1 = 1 ");

				if (!Common.checkIsEmpty(deviceName)) {
					sqlCommand.append("AND dv.dv_name like ? ");
				}

				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				if (!deviceName.isEmpty()) {
					ps.setString(++index, "%" + deviceName + "%");
				}

				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					totalRecords = rs.getInt("total_device");
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

	public List<Device> getListDevices(int offset, int limit, String deviceName,
			String sortByDeviceName) throws SQLException, ClassNotFoundException {
		List<Device> listDevices = new ArrayList<>();
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT dv_id, dv_name, department, dv_brand, product_date, import_date, status, func_score, app_score, maintain_score, histo_score, em_score, type, fre ");
				sqlCommand.append("FROM device ");
				sqlCommand.append("WHERE 1=1 ");

				// Nếu có nhập fullName
				if (!Common.checkIsEmpty(deviceName)) {
					sqlCommand.append("AND dv_name like ? ");
				}

				sqlCommand.append("ORDER BY ");
				sqlCommand.append("dv_name " + sortByDeviceName );
				sqlCommand.append(" LIMIT ? OFFSET ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				if (!deviceName.isEmpty()) {
					ps.setString(++index, "%" + deviceName + "%");
				}
				ps.setInt(++index, limit);
				ps.setInt(++index, offset);
				
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
								
				while (rs.next()) {
					// Khởi tạo đối tượng UserInfor
					Device device = new Device();
					device.setDeviceId(rs.getInt("dv_id"));
					device.setDeviceName(rs.getString("dv_name"));
					device.setDepartment(rs.getString("department"));
					device.setBrand(rs.getString("dv_brand"));
					device.setProductDate(rs.getString("product_date"));
					device.setImportDate(rs.getString("import_date"));
					device.setStatus(rs.getString("status"));
					device.setFuncScore(rs.getInt("func_score"));
					device.setAppScore(rs.getInt("app_score"));
					device.setMaintainScore(rs.getInt("maintain_score"));
					device.setHistoScore(rs.getInt("histo_score"));
					device.setEmScore(rs.getInt("em_score"));
					device.setType(rs.getString("type"));
					device.setFre(rs.getString("fre"));
					listDevices.add(device);
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
		return listDevices;
	}
	
	public List<Device> getDevicesExportFile() throws SQLException, ClassNotFoundException {
		List<Device> listDevices = new ArrayList<>();
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT dv_id, dv_name, dv_model, dv_serial, department, dv_brand, product_date, import_date, ");
				sqlCommand.append("unit, amount, source, status, func_score, app_score, maintain_score, histo_score, em_score, type, fre ");
				sqlCommand.append("FROM device ");
//				sqlCommand.append("WHERE dv_file = file.file_id, file.user_id = user.user_id and user.user_id = ?");

//				// Nếu có nhập fullName
//				if (!Common.checkIsEmpty(deviceName)) {
//					sqlCommand.append("AND dv_name like ? ");
//				}
//
//				sqlCommand.append("ORDER BY ");
//				sqlCommand.append("dv_name " + sortByDeviceName );
//				sqlCommand.append(" LIMIT ? OFFSET ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				
				System.out.println(ps);
				
//				if (!deviceName.isEmpty()) {
//					ps.setString(++index, "%" + deviceName + "%");
//				}
//
//				ps.setInt(++index, fileId);
//				ps.setInt(++index, userId);
				
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
								
				while (rs.next()) {
					// Khởi tạo đối tượng UserInfor
					Device device = new Device();
					device.setDeviceId(rs.getInt("dv_id"));
					device.setDeviceName(rs.getString("dv_name"));
					device.setDeviceModel(rs.getString("dv_model"));
					device.setDeviceSerial(rs.getString("dv_serial"));
					device.setDepartment(rs.getString("department"));
					device.setBrand(rs.getString("dv_brand"));
					device.setProductDate(rs.getString("product_date"));
					device.setImportDate(rs.getString("import_date"));

					device.setUnit(rs.getString("unit"));
					device.setAmount(rs.getInt("amount"));
					device.setSource(rs.getString("source"));
					device.setStatus(rs.getString("status"));
					
					device.setFuncScore(rs.getInt("func_score"));
					device.setAppScore(rs.getInt("app_score"));
					device.setMaintainScore(rs.getInt("maintain_score"));
					device.setHistoScore(rs.getInt("histo_score"));
					device.setEmScore(rs.getInt("em_score"));
					device.setType(rs.getString("type"));
					device.setFre(rs.getString("fre"));


					listDevices.add(device);
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
		
		return listDevices;
	}

	public Device getDeviceById(int deviceId) throws SQLException, ClassNotFoundException {
		Device device = null;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append(
						"SELECT dv.dv_id, dv.dv_name, dv.dv_model, dv.dv_serial, dv.department, dv.dv_brand, ");
				sqlCommand.append(
						"dv.product_date, dv.import_date, dv.unit, dv.status, dv.source, f.file_name, dv.amount, ");
				sqlCommand.append(
						"dv.func_score, dv.app_score, dv.maintain_score, dv.histo_score, dv.em_score, dv.type, dv.fre ");
				sqlCommand.append("FROM device dv ");
				sqlCommand.append("LEFT JOIN file f ON f.file_id = dv.dv_file ");

				sqlCommand.append("WHERE dv.dv_id = ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setInt(++index, deviceId);
				
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					// Khởi tạo đối tượng Device
					device = new Device();
					// Set các giá trị từ đối tượng ResultSet cho Device
					device.setDeviceId(rs.getInt("dv_id"));
					device.setDeviceName(rs.getString("dv_name"));
					device.setDeviceModel(rs.getString("dv_model"));
					device.setDeviceSerial(rs.getString("dv_serial"));
					device.setDepartment(rs.getString("department"));
					device.setBrand(rs.getString("dv_brand"));
					device.setProductDate(rs.getString("product_date"));
					device.setImportDate(rs.getString("import_date"));
					device.setUnit(rs.getString("unit"));
					device.setStatus(rs.getString("status"));
					device.setSource(rs.getString("source"));
					device.setFile(rs.getString("file_name"));
					
					device.setAmount(rs.getInt("amount"));
					device.setFuncScore(rs.getInt("func_score"));
					device.setAppScore(rs.getInt("app_score"));
					device.setMaintainScore(rs.getInt("maintain_score"));
					device.setHistoScore(rs.getInt("histo_score"));
					device.setEmScore(rs.getInt("em_score"));
					device.setType(rs.getString("type"));
					device.setFre(rs.getString("fre"));

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
		return device;
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

	public boolean checkExistDeviceById(int deviceId) throws SQLException, ClassNotFoundException {
		boolean exist = false;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT dv_id FROM device ");
				sqlCommand.append("WHERE dv_id = ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setInt(++index, deviceId);
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					exist = true;
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
		return exist;
	}

	public void updateDevice(Device device) throws SQLException, ClassNotFoundException {
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append(
						"UPDATE device SET dv_name = ?, department = ?, dv_brand = ?, ");

				sqlCommand.append(
						"product_date = ?, import_date = ?, status = ?, ");
				
				sqlCommand.append(
						"func_score = ?, app_score = ?, maintain_score = ?, histo_score = ?, em_score = ?, type = ?, fre = ? ");
				
				sqlCommand.append("WHERE dv_id = ? ");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setString(++index, device.getDeviceName());
				ps.setString(++index, device.getDepartment());
				ps.setString(++index, device.getBrand());

				ps.setString(++index, device.getProductDate());
				ps.setString(++index, device.getImportDate());
				ps.setString(++index, device.getStatus());
				
				ps.setInt(++index, device.getFuncScore());
				ps.setInt(++index, device.getAppScore());
				ps.setInt(++index, device.getMaintainScore());
				ps.setInt(++index, device.getHistoScore());
				ps.setInt(++index, device.getEmScore());
				ps.setString(++index, device.getType());
				ps.setString(++index, device.getFre());
				ps.setInt(++index, device.getDeviceId());
				
				System.out.println("Câu update : " + ps);
				
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
	

	public void deleteDevice(int deviceId) throws SQLException, ClassNotFoundException {
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("DELETE FROM device ");
				sqlCommand.append("WHERE dv_id = ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setInt(++index, deviceId);
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
	
	public int getTotalDevicesSameType(String deviceName)
			throws SQLException, ClassNotFoundException {
		int totalRecords = 0;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT COUNT(dv_id) AS total_device FROM device dv ");
				sqlCommand.append("WHERE dv.dv_name = ?");

				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());

				ps.setString(++index, deviceName);

				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					totalRecords = rs.getInt("total_device");
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
	
	public boolean insertDevice(Device device) throws SQLException, ClassNotFoundException {
		boolean insert = false;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("INSERT INTO device(dv_name, dv_model, dv_serial, ");
				sqlCommand.append("department, dv_brand, product_date, import_date, unit, status, source, dv_file, amount )");
				sqlCommand.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setString(++index, device.getDeviceName());
				ps.setString(++index, device.getDeviceModel());
				ps.setString(++index, device.getDeviceSerial());
				ps.setString(++index, device.getDepartment());
				ps.setString(++index, device.getBrand());
				ps.setString(++index, device.getProductDate());
				ps.setString(++index, device.getImportDate());
				ps.setString(++index, device.getUnit());
				ps.setString(++index, device.getStatus());
				ps.setString(++index, device.getSource());
				ps.setString(++index, device.getFile());
				ps.setInt(++index, device.getAmount());
				
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

	public int getTotalDevicesInFile(int fileID) throws SQLException, ClassNotFoundException {
		int totalRecords = 0;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT COUNT(dv_id) AS total_device FROM device dv ");
				sqlCommand.append("WHERE dv.dv_file = ?");

				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());

				ps.setInt(++index, fileID);

				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					totalRecords = rs.getInt("total_device");
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

	public List<Device> getListDevicesInFile(int offset, int limit, int fileID, String sortByDeviceName) throws SQLException, ClassNotFoundException {
		List<Device> listDevices = new ArrayList<>();
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT dv_id, dv_name, department, dv_brand, product_date, import_date, status, func_score, app_score, maintain_score, histo_score, em_score, type, fre ");
				sqlCommand.append("FROM device ");
				sqlCommand.append("WHERE 1 = 1 ");

					sqlCommand.append("AND dv_file = ? ");

				sqlCommand.append("ORDER BY ");
				sqlCommand.append("dv_name " + sortByDeviceName );
				sqlCommand.append(" LIMIT ? OFFSET ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());

					ps.setInt(++index, fileID);


				ps.setInt(++index, limit);
				ps.setInt(++index, offset);
				
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
								
				while (rs.next()) {
					// Khởi tạo đối tượng UserInfor
					Device device = new Device();
					device.setDeviceId(rs.getInt("dv_id"));
					device.setDeviceName(rs.getString("dv_name"));
					device.setDepartment(rs.getString("department"));
					device.setBrand(rs.getString("dv_brand"));
					device.setProductDate(rs.getString("product_date"));
					device.setImportDate(rs.getString("import_date"));
					device.setStatus(rs.getString("status"));
					device.setFuncScore(Common.convertStringToInteger(rs.getString("func_score"), Constants.DEFAULT_VALUE));
					device.setAppScore(Common.convertStringToInteger(rs.getString("app_score"), Constants.DEFAULT_VALUE));
					device.setMaintainScore(Common.convertStringToInteger(rs.getString("maintain_score"), Constants.DEFAULT_VALUE));
					device.setHistoScore(Common.convertStringToInteger(rs.getString("histo_score"), Constants.DEFAULT_VALUE));
					device.setEmScore(Common.convertStringToInteger(rs.getString("em_score"), Constants.DEFAULT_VALUE));
					device.setType(rs.getString("type"));
					device.setFre(rs.getString("fre"));

					listDevices.add(device);
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
		return listDevices;
	}
	
}
