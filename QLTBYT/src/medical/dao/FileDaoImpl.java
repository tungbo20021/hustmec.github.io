package medical.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import medical.entities.File;
public class FileDaoImpl extends BaseDaoImpl {
	public boolean insertFile(String fileName, int userId) throws SQLException, ClassNotFoundException {
		boolean insert = false;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("INSERT INTO file (file_name, user_id) ");
				sqlCommand.append("VALUES (?, ?)");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setString(++index, fileName);
				ps.setInt(++index, userId);
				
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
	
	public File getFileIdByFileName(String fileName) throws SQLException, ClassNotFoundException {
		File file = null;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT file_id ");
				sqlCommand.append("FROM file WHERE file_name = ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setString(++index, fileName);

				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					file = new File();
					file.setFileName(fileName);
					file.setFileID(rs.getInt("file_id"));
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
		return file;
	}

	/**
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public int getTotalFile() throws SQLException, ClassNotFoundException {
		int totalRecords = 0;
		try {
			if (connectDB()) {

				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT COUNT(file_name) AS total_file FROM file f ");
				sqlCommand.append("WHERE 1 = 1 ");

				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());

				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					totalRecords = rs.getInt("total_file");
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

	public List<File> getListFiles(int offset, int limit) throws SQLException, ClassNotFoundException {
		List<File> listFiles = new ArrayList<File>();
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT f.file_id, f.file_name, f.user_id, u.login_name ");
				sqlCommand.append("FROM file f ");
				sqlCommand.append("LEFT JOIN user u ON u.user_id = f.user_id ");
				sqlCommand.append("WHERE 1 = 1 ");

				sqlCommand.append(" LIMIT ? OFFSET ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());

				ps.setInt(++index, limit);
				ps.setInt(++index, offset);
							
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
								
				while (rs.next()) {
					// Khởi tạo đối tượng UserInfor
					File file = new File();
					file.setFileID(rs.getInt("file_id"));
					file.setFileName(rs.getString("file_name"));
					file.setUserID(rs.getInt("user_id"));
					file.setUserName(rs.getString("login_name"));

					listFiles.add(file);
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
		return listFiles;
	}
	
	public List<File> getListFilesOfUser(int offset, int limit, int userId) throws SQLException, ClassNotFoundException {
		List<File> listFiles = new ArrayList<File>();
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT f.file_id, f.file_name, f.user_id, u.login_name ");
				sqlCommand.append("FROM file f ");
				sqlCommand.append("LEFT JOIN user u ON u.user_id = f.user_id ");
				sqlCommand.append("WHERE 1 = 1 ");
				sqlCommand.append("AND f.user_id = ?");
				sqlCommand.append(" LIMIT ? OFFSET ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				ps.setInt(++index, userId);
				ps.setInt(++index, limit);
				ps.setInt(++index, offset);
				
				System.out.println(ps);
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
								
				while (rs.next()) {
					// Khởi tạo đối tượng UserInfor
					File file = new File();
					file.setFileID(rs.getInt("file_id"));
					file.setFileName(rs.getString("file_name"));
					file.setUserID(rs.getInt("user_id"));
					file.setUserName(rs.getString("login_name"));

					listFiles.add(file);
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
		return listFiles;
	}
}
