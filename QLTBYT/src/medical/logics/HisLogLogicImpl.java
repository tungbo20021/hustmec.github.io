/**
 * 
 */
package medical.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import medical.dao.HisLogDaoImpl;
import medical.entities.HisLog;

public class HisLogLogicImpl {

	HisLogDaoImpl hisLogDaoImpl = new HisLogDaoImpl();
	
	public boolean createHisLog(HisLog hisLog) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
		boolean create = hisLogDaoImpl.insertLogHis(hisLog);
		return create;
	}

	public int getTotalHisLog(String logTime) throws ClassNotFoundException, SQLException {
		int total = hisLogDaoImpl.getTotalHisLog(logTime);
		return total;
	}

	public List<HisLog> getListDevices(int offset, int limit, String logTime) throws ClassNotFoundException, SQLException {
		List<HisLog> lisHisLogs = hisLogDaoImpl.getListHisLogs(offset, limit, logTime);
		return lisHisLogs;
	}
}
