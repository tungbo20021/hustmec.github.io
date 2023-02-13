package medical.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Các phương thức dùng chung trong project
 *
 *
 */
public class Common {

	/**
	 * 
	 * Mã hóa password theo thuật toán SHA-1
	 *
	 * @param String password - password cần mã hóa
	 * @param String salt - chuỗi động thêm vào password khi mã hóa
	 * @return String hashtext - password đã được mã hóa
	 * @throws NoSuchAlgorithmException - xảy ra khi không tìm thấy thuật toán SHA-1
	 */
	public static String encryptPassword(String password, String salt) throws NoSuchAlgorithmException {
		// Chuỗi mã hóa
		String hashtext = null;
		// Khai báo 1 mảng kiểu byte có độ dài 16
		byte[] arrByte = new byte[16];
		// Mã hóa salt thành 1 mảng byte
		arrByte = salt.getBytes();
		try {
			// Lấy thuật toán SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// Thêm salt
			md.update(arrByte);
			// Xử lý chuỗi đầu vào trả về dưới dạng mảng byte
			byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
			// chuyển đổi mảng thành các kí tự biểu diễn
			BigInteger no = new BigInteger(1, hashedPassword);
			// chuyển đổi kí tự biểu diễn thành chuỗi hex
			hashtext = no.toString(16);
			// Nếu chuỗi ít hơn 32 bit thì thêm kí tự 0 ở đầu
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			// Không tìm thấy thuật toán SHA-1 thì bắn ra NoSuchAlgorithmException
		} catch (NoSuchAlgorithmException e) {
			// Ghi log
			System.out.println(Common.class.getClass().getName() + " "
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			// Ném ngoại lệ
			throw e;
		}
		return hashtext;
	}

	/**
	 * 
	 * So sánh 2 chuỗi
	 *
	 * @param String input1 - chuỗi thứ nhất
	 * @param String input2 - chuỗi thứ hai
	 * @return true - nếu 2 chuỗi giống nhau
	 * @return false - nếu 2 chuỗi khác nhau
	 */
	public static boolean compareString(String input1, String input2) {
		boolean check = false;
		// Chuỗi đầu vào khác null thì mới thực hiện check
		if (input1 != null) {
			// hai chuỗi giống hệt nhau thì check = true
			if (input1.equals(input2)) {
				check = true;
			}
		}
		return check;
	}

	/**
	 * Replace kí tự đặc biệt của mệnh đề like trong câu SQL
	 * 
	 * @param String - chuỗi chưa được replace kí tự đặc biệt
	 * @return String - chuỗi đã được replace kí tự đặc biệt
	 */
	public static String replaceWildCard(String input) {
		// Chuỗi đầu vào khác null thì mới thực hiện replace
		if (!Common.checkIsEmpty(input)) {
			input = input.replace("\\", "\\\\");
			input = input.replace("%", "\\%");
			input = input.replace("_", "\\_");
		} else {
			input = "";
		}
		return input;
	}

	/**
	 * Tạo chuỗi paging ở màn hình ADM002
	 * 
	 * @param totalUser   - tổng số user
	 * @param limit       - số user tối đa trên 1 page
	 * @param currentpage - trang hiện tại hiển thị trên màn hình
	 * @return Danh sách các trang cần hiển thị ở chuỗi paging
	 */
	public static List<Integer> getListPaging(int totalUser, int limit, int currentPage) {
		// Khởi tạo listPaging
		List<Integer> listPaging = new ArrayList<>();
		// Nếu tổng số user > số user tối đa trên 1 page
		if (totalUser > limit) {
			// Tính tổng số page có được
			int totalPage = getTotalPage(totalUser, limit);
			// Tính toán Page nhỏ nhất trong chuỗi
			int minPage = ((currentPage - 1) / Constants.LIMIT_PAGE) * Constants.LIMIT_PAGE + 1;
			// Tính toán Page lớn nhất trong chuỗi
			int maxPage = minPage + Constants.LIMIT_PAGE - 1;
			// Nếu Page lớn nhất trong chuỗi > tổng số page thì Page lớn nhất = tổng số page
			if (maxPage > totalPage) {
				maxPage = totalPage;
			}
			// Add các page tìm được vào listPaging
			for (int i = minPage; i <= maxPage; i++) {
				listPaging.add(i);
			}
		}
		// Trả về chuỗi paging lấy được
		return listPaging;
	}

	/**
	 * Lấy offset - vị trí record cần bỏ qua trong câu sql
	 * 
	 * @param currentPage - Trang hiện tại
	 * @param limit       - số bản ghi tối đa trên 1 page
	 * @return int - vị trí cần lấy
	 */
	public static int getOffset(int currentPage, int limit) {
		int offset = (currentPage - 1) * limit;
		return offset;
	}

	/**
	 * Tính tổng số trang theo tổng số user
	 * 
	 * @param totalUser - tổng số user
	 * @param limit     - số bản ghi tối đa trên 1 page
	 * @return totalPage - tổng số trang
	 */
	public static int getTotalPage(int totalUser, int limit) {
		int totalPage = 0;
		if (totalUser % limit == 0) {
			// Nếu tổng số record chia hết cho limit
			// Thì số trang bằng thương
			totalPage = totalUser / limit;
		} else {
			// Nếu tổng số record không chia hết cho limit
			// Thì số trang bằng thương + 1
			totalPage = (totalUser / limit) + 1;
		}
		return totalPage;

	}

	/**
	 * Lấy danh sách các năm từ năm bắt đầu -> năm hiện tại + 1
	 * 
	 * @param fromYear - Năm bắt đầu
	 * @param toYear   - Năm hiện tại
	 * @return List<Integer> - Danh sách (năm bắt đầu) đến (năm hiện tại +1)
	 */
	public static List<Integer> getListYear(int fromYear, int toYear) {
		// Khai báo danh sách chứa các năm
		List<Integer> listYears = new ArrayList<>();
		for (int i = fromYear; i <= (toYear + 1); i++) {
			// Add năm vào danh sách
			listYears.add(i);
		}
		return listYears;
	}

	/**
	 * Lấy danh sách các tháng từ 1->12
	 * 
	 * @return List<Integer> - Danh sách tháng từ 1-12
	 */
	public static List<Integer> getListMonth() {
		// Khai báo danh sách chứa các tháng
		List<Integer> listMonths = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			// Add tháng vào list
			listMonths.add(i);
		}
		return listMonths;
	}

	/**
	 * Lấy danh sách các ngày từ 1->31
	 * 
	 * @return List<Integer> - Danh sách ngày từ 1-31
	 */
	public static List<Integer> getListDay() {
		// Khai báo danh sách chứa các ngày
		List<Integer> listDays = new ArrayList<>();
		for (int i = 1; i <= 31; i++) {
			// Add ngày vào list
			listDays.add(i);
		}
		return listDays;
	}

	/**
	 * Lấy năm hiện tại
	 * 
	 * @return date - năm hiện tại
	 */
	public static int getYearNow() {
		int date = Calendar.getInstance().get(Calendar.YEAR);
		return date;
	}

	/**
	 * Cộng các số năm, tháng, ngày thành 1 chuỗi ngày tháng có format yyyy/MM/dd
	 * 
	 * @param year  - năm
	 * @param month - tháng
	 * @param day   - ngày
	 * @return String - chuỗi ngày tháng có format yyyy/MM/dd
	 */
	public static String convertToString(int year, int month, int day) {
		// Khai báo StringBuilder để thực hiện append chuỗi
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.valueOf(year));
		stringBuilder.append("/");
		// Nếu month < 10 thì thêm số 0 ở đầu
		if (month < 10) {
			stringBuilder.append("0");
		}
		stringBuilder.append(String.valueOf(month));
		stringBuilder.append("/");
		// Nếu day < 10 thì thêm số 0 ở đầu
		if (day < 10) {
			stringBuilder.append("0");
		}
		stringBuilder.append(String.valueOf(day));
		// Trả về chuỗi ngày tháng: yyyy/MM/dd
		return stringBuilder.toString();
	}

	/**
	 * Kiểm tra chuỗi có rỗng hay không
	 * 
	 * @param String input
	 * @return true - Nếu chuỗi đó null hoặc rỗng
	 * @return false - Nếu chuỗi đó có kí tự
	 */
	public static boolean checkIsEmpty(String input) {
		boolean check = false;
		// Nếu chuỗi null hoặc không có kí tự nào thì check = true
		if (input == null || input.trim().isEmpty()) {
			check = true;
		}
		return check;
	}

	/**
	 * check độ dài chuỗi có nằm trong khoảng cho phép hay không
	 * 
	 * @param min    - số kí tự nhỏ nhất
	 * @param max    - số kí tự lớn nhất
	 * @param String input - chuỗi cần check
	 * @return true - nếu chuỗi đầu vào nằm trong khoảng cho phép
	 * @return false - nếu chuỗi đầu vào không nằm trong khoảng cho phép
	 */
	public static boolean checkLengthRange(int min, int max, String input) {
		boolean check = false;
		if (input.trim().length() >= min && input.trim().length() <= max) {
			check = true;
		}
		return check;
	}

	/**
	 * check độ dài chuỗi có vượt quá giới hạn hay không
	 * 
	 * @param max
	 * @param input
	 * @return true - nếu chuỗi <= độ dài max cho phép
	 * @return true - nếu chuỗi > độ dài max cho phép
	 */
	public static boolean checkMaxLength(int max, String input) {
		boolean check = false;
		if (input.trim().length() <= max) {
			check = true;
		}
		return check;
	}

	/**
	 * check chuỗi đầu vào có đúng định dạng cho phép hay không
	 * 
	 * @param input  - chuỗi đầu vào
	 * @param format - định dạng cần khớp
	 * @return true - nếu đúng định dạng
	 * @return true - nếu sai định dạng
	 */
	public static boolean checkFormat(String input, String format) {
		return input.matches(format);
	}

	/**
	 * check ngày tháng có hợp lệ hay không
	 * 
	 * @param date - ngày tháng kiểu String
	 * @return true - nếu ngày tháng hợp lệ
	 * @return false - nếu ngày tháng không hợp lệ
	 */
	public static boolean isDateValid(String date) {
		boolean check;
		// Set kiểu ngày tháng năm
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.FORMAT_DATE);
		// set false để kiểm tra tính hợp lệ của date.
		// VD: tháng 2 phải có 28-29 ngày, năm có 12 tháng,....
		simpleDateFormat.setLenient(false);
		try {
			// parse dateString thành kiểu Date
			// Nếu ngày tháng năm không hợp lệ, sẽ throw ra ParseException
			simpleDateFormat.parse(date);
			check = true;
		} catch (ParseException e) {
			check = false;
		}
		return check;
	}

	/**
	 * check kí tự có phải là kí tự halfsize hay không
	 * 
	 * @param input
	 * @return true - nếu là kí tự halfsize
	 * @return false - nếu không phải kí tự halfsize
	 */
	public static boolean checkCharacterHalfSize(String input) {
		boolean check = true;
		for (int i = 0; i <= input.length() - 1; i++) {
			// Kiểm tra kí tự bảng mã ASCII nếu > 127 thì không phải kí tự halfsize
			if (input.charAt(i) > 127) {
				check = false;
				break;
			}
		}
		return check;
	}

	/**
	 * Kiểm tra ngày hết hiệu lực lớn hơn ngày có hiệu lực hay không
	 * 
	 * @param startDate - ngày bắt đầu
	 * @param endDate   - ngày kết thúc
	 * @return true - nếu endDate > startDate
	 * @return false - nếu endDate <= startDate
	 */
	public static boolean checkEndDateMoreThanStartDate(String startDate, String endDate) {
		// Khởi tạo biến check
		boolean check = false;
		// So sánh 2 chuỗi
		int result = endDate.compareTo(startDate);
		// Nếu result > 0 thì endDate > startDate
		if (result > 0) {
			check = true;
		}
		return check;
	}

	/**
	 * Lấy time hiện tại tính đến mini second
	 * 
	 * @return thời gian kiểu String
	 */
	public static String getSalt() {
		// Lấy thời gian hiện tại đưa về String
		return java.time.LocalTime.now().toString();
	}

	public static String getDateNow() {
		// Lấy ngày hiện tại đưa về String
		return java.time.LocalDate.now().toString();
	}

	/**
	 * Convert kiểu string sang kiểu int
	 * 
	 * @param input - chuỗi đầu vào
	 * @return defaultValue - Giá trị mặc định(Nếu như không chuyển đổi được)
	 * @return output - Số đã được chuyển đổi
	 */
	public static int convertStringToInteger(String input, int defaultValue) {
		int output;
		try {
			// Thực hiện chuyển đổi
			output = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			// Nếu xảy ra ngoại lệ thì trả về giá trị mặc định
			output = defaultValue;
		}
		return output;
	}

	/**
	 * Add chuỗi vào list String nếu chuỗi đầu vào khác null và không rỗng
	 * 
	 * @param listString - List String để add
	 * @param input      - String cần add vào list
	 */
	public static void addStringToList(List<String> listString, String input) {
		// Nếu chuỗi không rỗng thì add chuỗi vào list
		if (!input.isEmpty()) {
			listString.add(input);
		}
	}

	public static String formatImportDate(String importDate) {
		String[] listTime = importDate.split("-");
		String afterFormat = listTime[2] + listTime[1] + listTime[0];
		return afterFormat;
	}

	public static String createLogTime() {
		String date = java.time.LocalDate.now().toString();
		String dateArray[] = date.split("-");
		String dateFormatted = dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0];
		return dateFormatted + "/" + java.time.LocalTime.now().toString();
	}

	public static String encryptDate(String date) {
		String result = date;
		if (date != "" && date != null) {
			if (date.endsWith(".0")) {
				result = date.substring(0, date.lastIndexOf("."));
			}
		}
		return result;
	}
}
