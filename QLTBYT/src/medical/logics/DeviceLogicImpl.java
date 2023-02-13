package medical.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import medical.dao.DeviceDaoImpl;
import medical.entities.Device;
import medical.entities.User;
import medical.utils.Common;

/**
 * Implement UserLogic để Xử lý logic cho các chức năng liên quan đến tbl_user
 *
 *
 */
public class DeviceLogicImpl {
	DeviceDaoImpl deviceDaoImpl = new DeviceDaoImpl();

	public boolean checkExistAccount(String loginName, String password, int rule)
			throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
		boolean check = false;
		// Lấy user có rule là admin theo login_name
		User user = deviceDaoImpl.getUserByLoginName(loginName, rule);
		// Nếu account có tồn tại
		if (user != null) {
			// Mã hóa password
			String passwordEncrypt = Common.encryptPassword(password, user.getSalt());
			// Kiểm tra so sánh password vừa mã hóa với password của admin lấy được
			check = Common.compareString(passwordEncrypt, user.getPassword());
		}
		return check;
	}

	public int getTotalDevices(String deviceName)
			throws SQLException, ClassNotFoundException {
		// Replace kí tự wildcard cho fullName
		deviceName = Common.replaceWildCard(deviceName);
		// Lấy về tổng số bản ghi user
		int totalRecords = deviceDaoImpl.getTotalDevices(deviceName);
		// Trả về tổng số bản ghi lấy được
		return totalRecords;
	}
	
	public int getTotalDevicesSameType(String deviceName) throws ClassNotFoundException, SQLException {
		int totalRecords = deviceDaoImpl.getTotalDevicesSameType(deviceName);
		return totalRecords;
	}

	public List<Device> getListDevices(int offset, int limit, String deviceName, String sortByDeviceName) throws SQLException, ClassNotFoundException {
		// Replace kí tự wildcard cho fullName
		deviceName = Common.replaceWildCard(deviceName);
		// Gọi method getListUsers() trả về listUserInfor
		List<Device> listDevices = deviceDaoImpl.getListDevices(offset, limit, deviceName, sortByDeviceName);
		// Trả về listUserInfor
		return listDevices;
	}
	public List<Device> getDevicesExportFile() throws ClassNotFoundException, SQLException {
		List<Device> listDevices = deviceDaoImpl.getDevicesExportFile();
		return listDevices;
	}

	public Device getDeviceById(int deviceId) throws SQLException, ClassNotFoundException {
		// Gọi getUserInforByUserId bên DAO trả về 1 UserInfor
		Device device = deviceDaoImpl.getDeviceById(deviceId);
		// Trả về userInfor
		return device;
	}

	public boolean checkExistLoginName(String loginName) throws SQLException, ClassNotFoundException {
		boolean check = false;
		// Lấy tblUser theo loginName
		User user = deviceDaoImpl.getUserByLoginName(loginName);
		// Nếu đã tồn tại user có loginName này trong DB thì check = true;
		if (user != null) {
			check = true;
		}
		return check;
	}

	public boolean checkExistEmail(String email) throws SQLException, ClassNotFoundException {
		boolean check = false;
		// Lấy tblUser theo email
		User user = deviceDaoImpl.getUserByEmail(email);
		// Nếu đã tồn tại user có email này trong DB thì check = true;
		if (user != null) {
			check = true;
		}
		return check;
	}

	public boolean checkExistEmail(String email, int userId) throws SQLException, ClassNotFoundException {
		boolean check = false;
		// Lấy tblUser theo email
		User user = deviceDaoImpl.getUserByEmail(email);
		// Nếu đã tồn tại user có email này trong DB thì check = true;
		if (user != null && user.getUserId() != userId) {
			check = true;
		}
		return check;
	}

	public boolean checkExistDevice(int deviceId) throws ClassNotFoundException, SQLException {
		return deviceDaoImpl.checkExistDeviceById(deviceId);
	}


	public boolean updateDevice(Device device) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
		boolean update = false;
		// Thực hiện update user
		deviceDaoImpl.updateDevice(device);
		update = true;
		// Trả về biến update
		return update;
	}


	public boolean deleteDevice(int deviceId) throws SQLException, ClassNotFoundException {
		boolean delete = false;
		// Thực hiện delete user
		deviceDaoImpl.deleteDevice(deviceId);
		delete = true;
		// Trả về biến delete
		return delete;
	}
	
	public boolean createDevice(Device device) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
		boolean create = deviceDaoImpl.insertDevice(device);
		return create;
	}

	public int getTotalDevicesInFile(int fileID) throws ClassNotFoundException, SQLException {
		int totalRecords = deviceDaoImpl.getTotalDevicesInFile(fileID);
		return totalRecords;
	}

	public List<Device> getListDevicesInFile(int offset, int limit, int fileID, String sortByDeviceName) throws ClassNotFoundException, SQLException {
		List<Device> listDevices = deviceDaoImpl.getListDevicesInFile(offset, limit, fileID, sortByDeviceName);
		return listDevices;
	}
}
