package medical.utils;

import java.io.IOException;
import java.util.Properties;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Đọc dữ liệu từ file database.properties
 *
 */
@SuppressWarnings("unchecked")
public class DatabaseProperties {
	// Khai báo map chứa dữ liệu đọc được từ file
	static private Map<String, String> data = new HashMap<String, String>();
	// Khai báo 1 khối static để đọc file properties
	static {
		// Khởi tạo đối tượng Properties
		Properties prop = new Properties();
		try {
			// Đọc file database.properties
			prop.load(DatabaseProperties.class.getResourceAsStream(("/database.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Danh sách Enumeration String chứa các key đọc được từ file properties
		Enumeration<String> enumeration = (Enumeration<String>) prop.propertyNames();
		// Nếu có dữ liệu trong danh sách Enumeration
		while (enumeration.hasMoreElements()) {
			// Thì lấy ra key
			String key = (String) enumeration.nextElement();
			// Set key với data tương ứng vào map
			data.put(key, prop.getProperty(key));
		}
	}

	/**
	 * Lấy dữ liệu từ map theo key
	 * 
	 * @param key - key trong Map
	 * @return String - Data lấy được theo key
	 */
	static public String getData(String key) {
		String string = "";
		// Nếu Map có key cần lấy dữ liệu
		if (data.containsKey(key)) {
			// Lấy dữ liệu trong Map theo key đó
			string = data.get(key);
		}
		return string;
	}
}
