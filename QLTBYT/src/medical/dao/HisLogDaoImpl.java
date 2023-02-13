package medical.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import medical.entities.HisLog;
import medical.utils.Common;

public class HisLogDaoImpl extends BaseDaoImpl{
	public boolean insertLogHis(HisLog hisLog) throws SQLException, ClassNotFoundException {
		boolean insert = false;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("INSERT INTO loghistory(log_time, user_id )");
				sqlCommand.append("VALUES (?, ?)");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				// Set param
				ps.setString(++index, hisLog.getLogTime());
				ps.setInt(++index, hisLog.getUserId());
				
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

	public int getTotalHisLog(String logTime) throws SQLException, ClassNotFoundException {
		int totalRecords = 0;
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT COUNT(log_time) AS total_loghis FROM loghistory lh ");
				sqlCommand.append("WHERE 1 = 1 ");

				if (!Common.checkIsEmpty(logTime)) {
					sqlCommand.append("AND lh.log_time like ? ");
				}

				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				if (!logTime.isEmpty()) {
					ps.setString(++index, "%" + logTime + "%");
				}

				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
				while (rs.next()) {
					totalRecords = rs.getInt("total_loghis");
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

	public List<HisLog> getListHisLogs(int offset, int limit, String logTime) throws SQLException, ClassNotFoundException {
		List<HisLog> listHisLogs = new ArrayList<HisLog>();
		try {
			if (connectDB()) {
				// Biến index để setParam trong câu sql
				int index = 0;
				// Chuỗi StringBuilder chứa câu truy vấn
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append("SELECT lh.log_id, lh.log_time, lh.user_id, u.login_name ");
				sqlCommand.append("FROM loghistory lh ");
				sqlCommand.append("LEFT JOIN user u ON u.user_id = lh.user_id ");
				sqlCommand.append("WHERE 1 = 1 ");

				// Nếu có nhập fullName
				if (!Common.checkIsEmpty(logTime)) {
					sqlCommand.append("AND log_time like ? ");
				}

				sqlCommand.append(" LIMIT ? OFFSET ?");
				// Khởi tạo đối tượng PrepareStatement truyền vào câu truy vấn
				ps = conn.prepareStatement(sqlCommand.toString());
				if (!logTime.isEmpty()) {
					ps.setString(++index, "%" + logTime + "%");
				}

				ps.setInt(++index, limit);
				ps.setInt(++index, offset);
							
				// Thực thi câu truy vấn trả kết quả về cho đối tượng ResultSet
				rs = ps.executeQuery();
								
				while (rs.next()) {
					// Khởi tạo đối tượng UserInfor
					HisLog hisLog = new HisLog();
					hisLog.setLogId(rs.getInt("log_id"));
					hisLog.setLogTime(rs.getString("log_time"));
					hisLog.setUserId(rs.getInt("user_id"));
					hisLog.setUserName(rs.getString("login_name"));

					listHisLogs.add(hisLog);
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
		return listHisLogs;
	}
}
