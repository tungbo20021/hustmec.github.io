package medical.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import medical.utils.DatabaseProperties;

/**
 * Implement BaseDao để xử lý việc kết nối tới Database
 *
 *
 */
public class BaseDaoImpl {
	// Khởi tạo các biến thuộc tính của class
	protected Connection conn = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	public boolean connectDB() throws SQLException, ClassNotFoundException {
		boolean check = false;
		try {
			// Đọc driver, url, tên user và password kết nối đến DB
			String driver = DatabaseProperties.getData("driver");
			String url = DatabaseProperties.getData("url");
			String user = DatabaseProperties.getData("user");
			String password = DatabaseProperties.getData("password");
			Class.forName(driver);
			// Truyền giá trị cho biến Connection
			conn = DriverManager.getConnection(url, user, password);
			// Nếu Connection khác null thì trả về true
			if (conn != null) {
				check = true;
			}
		} catch (SQLException | ClassNotFoundException e) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Ném ngoại lệ
			throw e;
		}
		return check;
	}

	public void closeConnection() throws SQLException {
		try {
			// Nếu Connection khác null và Connection chưa đóng thì đóng
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException se) {
			// Ghi log
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			// Ném ngoại lệ
			throw se;
		}

	}

	public Connection getConnection() {
		return conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public void disableAutoCommit() throws SQLException {
		try {
			// Set false để không tự động commit vào DB khi excute
			conn.setAutoCommit(false);
		} catch (SQLException se) {
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		}
	}

	public void commit() throws SQLException {
		try {
			// Thực hiện commit sự thay đổi dữ liệu vào DB
			conn.commit();
		} catch (SQLException se) {
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		}
	}

	public void rollback() throws SQLException {
		try {
			// Thực hiện rollback data khi gặp lỗi
			conn.rollback();
		} catch (SQLException se) {
			System.out.println(this.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + se.getMessage());
			throw se;
		}
	}

}
