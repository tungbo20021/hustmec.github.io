package medical.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import medical.dao.FileDaoImpl;
import medical.entities.File;

public class FileLogicImpl {
	FileDaoImpl fileDaoImpl = new FileDaoImpl();
	
	public boolean createFile(String fileName, int userID) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
		boolean create = fileDaoImpl.insertFile(fileName, userID);
		return create;
	}
	
	public File getFileIdByFileName(String fileName) throws SQLException, ClassNotFoundException {
		File file = fileDaoImpl.getFileIdByFileName(fileName);
		return file;
	}

	public int getTotalFile() throws ClassNotFoundException, SQLException {
		int total = 0;
		total = fileDaoImpl.getTotalFile();
		return total;
	}

	public List<File> getListFiles(int offset, int limit) throws ClassNotFoundException, SQLException {
		List<File> listFiles = fileDaoImpl.getListFiles(offset, limit);
		return listFiles;
	}
	
	public List<File> getListFilesOfUser(int offset, int limit, int userId) throws ClassNotFoundException, SQLException {
		List<File> listFiles = fileDaoImpl.getListFilesOfUser(offset, limit, userId);
		return listFiles;
	}
}
