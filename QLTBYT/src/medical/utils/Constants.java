package medical.utils;

/**
 * Các biến Constant dùng chung trong Project
 *
 */
public class Constants {
	// URL trang jsp và các servlet
	public static final String LOGIN_JSP = "/jsp/Login.jsp";
	public static final String LIST_USER_JSP = "/jsp/list_user.jsp";
	public static final String LIST_DEVICE_JSP = "/jsp/list_device.jsp";
	public static final String LIST_DEVICE_INFILE_JSP = "/jsp/list_device_infile.jsp";

	public static final String LIST_HISLOG_JSP = "/jsp/list_hislog.jsp";
	public static final String LIST_FILE_JSP = "/jsp/list_file.jsp";

	public static final String LIST_CALENDAR_DEVICE_JSP = "/jsp/list_calendar_device.jsp";
	public static final String LIST_ACCESSORY_JSP = "/jsp/list_accessory.jsp";
	public static final String LIST_DEPARTMENT_JSP = "/jsp/list_department.jsp";
	public static final String LIST_SCHEDULE_JSP = "/jsp/list_maintenance_schedule.jsp";
	public static final String LIST_DEVICE_TYPE_JSP = "/jsp/list_device_type.jsp";
	public static final String LIST_PROVIDER_JSP = "/jsp/list_provider.jsp";
	public static final String ADD_USER_JSP = "/jsp/add_user.jsp";
	public static final String ADD_DEVICE_JSP = "/jsp/add_device.jsp";
	public static final String ADD_ACCESSORY_JSP = "/jsp/add_accessory.jsp";
	public static final String ADD_DEPARTMENT_JSP = "/jsp/add_department.jsp";
	public static final String ADD_DEVICE_TYPE_JSP = "/jsp/add_device_type.jsp";
	public static final String ADD_PROVIDER_JSP = "/jsp/add_provider.jsp";
	public static final String CONFIRM_DATA_USER_JSP = "/jsp/confirm_data_user.jsp";
	public static final String CONFIRM_DATA_DEVICE_JSP = "/jsp/confirm_data_device.jsp";
	public static final String CONFIRM_DATA_ACCESSORY_JSP = "/jsp/confirm_data_accessory.jsp";
	public static final String CONFIRM_DATA_DEPARTMENT_JSP = "/jsp/confirm_data_department.jsp";
	public static final String CONFIRM_DATA_DEVICE_TYPE_JSP = "/jsp/confirm_data_device_type.jsp";
	public static final String CONFIRM_DATA_PROVIDER_JSP = "/jsp/confirm_data_provider.jsp";
	public static final String DETAIL_DATA_USER_JSP = "/jsp/detail_data_user.jsp";
	public static final String DETAIL_DATA_DEVICE_JSP = "/jsp/detail_data_device.jsp";
	public static final String SUCCESS_JSP = "/jsp/Success.jsp";
	public static final String SYSTEM_ERROR_URL = "/jsp/System_Error.jsp";
	public static final String LOGIN_URL = "/Login.do";
	public static final String LOGOUT_URL = "/Logout.do";
	public static final String LIST_USER_URL = "/ListUser.do";
	public static final String ADD_USER_CONFIRM_URL = "/AddUserOK.do";
	public static final String ADD_DEVICE_CONFIRM_URL = "/AddDeviceOK.do";
	public static final String ADD_ACCESSORY_CONFIRM_URL = "/AddAccessoryOK.do";
	public static final String ADD_DEPARTMENT_CONFIRM_URL = "/AddDepartmentOK.do";
	public static final String ADD_DEVICE_TYPE_CONFIRM_URL = "/AddDeviceTypeOK.do";
	public static final String ADD_PROVIDER_CONFIRM_URL = "/AddProviderOK.do";
	public static final String EDIT_USER_CONFIRM_URL = "/EditUserOK.do";
	public static final String EDIT_ACCESSORY_CONFIRM_URL = "/EditAccessoryOK.do";
	public static final String EDIT_DEVICE_CONFIRM_URL = "/EditDeviceOK.do";
	public static final String SUCCESS_URL = "/Success.do";
	public static final String SYSTEM_ERROR_DO = "/SystemError";
	
	public static final String IMPORT_FILE_URL = "/jsp/import_excel.jsp";
	public static final String DIR_FILE_EXCELL = "C:/Users/Thi/eclipse-workspace/Servers/ExcelFiles";
	public static final String FILE_EXPORT = "C:/Users/Thi/Downloads/ListDevices";
	public static final String TYPE_FILE_EXPORT = ".xlsx";

	
	public static final String HANDOVER_IMAGE_URL = "/jsp/Handover_image.jsp";
	// Cờ đánh dấu đã qua trang
	public static final String FLAG = "flag";
	// Mess thông báo lỗi validate
	public static final String ER001_LOGIN_NAME = "ER001_LOGIN_NAME";
	public static final String ER001_PASSWORD = "ER001_PASSWORD";
	public static final String ER001_FULL_NAME = "ER001_FULL_NAME";
	public static final String ER001_DEPARTMENT_NAME = "ER001_DEPARTMENT_NAME";
	public static final String ER001_ADDRESS = "ER001_ADDRESS";
	public static final String ER001_EMAIL = "ER001_EMAIL";
	public static final String ER001_TEL = "ER001_TEL";
	public static final String ER001_TOTAL = "ER001_TOTAL";
	public static final String NO_CHOOSE_DEPARTMENT = "NO_CHOOSE_DEPARTMENT";
	public static final String ER003_LOGIN_NAME = "ER003_LOGIN_NAME";
	public static final String ER003_EMAIL = "ER003_EMAIL";
	public static final String ER004_GROUP = "ER004_GROUP";
	public static final String ER004_LEVEL_JAPAN = "ER004_LEVEL_JAPAN";
	public static final String ER005_EMAIL = "ER005_EMAIL";
	public static final String ER005_TEL = "ER005_TEL";
	public static final String ER006_FULL_NAME_255 = "ER006_FULL_NAME_255";
	public static final String ER006_FULL_NAME_KANA_255 = "ER006_FULL_NAME_KANA_255";
	public static final String ER006_EMAIL_100 = "ER006_EMAIL_100";
	public static final String ER006_TEL_14 = "ER006_TEL_14";
	public static final String ER006_TOTAL_9 = "ER006_TOTAL_9";
	public static final String ER007_LOGIN_NAME = "ER007_LOGIN_NAME";
	public static final String ER007_PASSWORD = "ER007_PASSWORD";
	public static final String ER008_PASSWORD = "ER008_PASSWORD";
	public static final String ER009 = "ER009";
	public static final String ER011_BIRTH_DAY = "ER011_BIRTH_DAY";
	public static final String ER011_START_DATE = "ER011_START_DATE";
	public static final String ER011_END_DATE = "ER011_END_DATE";
	public static final String ER012 = "ER012";
	public static final String ER017 = "ER017";
	public static final String ER018 = "ER018";
	public static final String ER019 = "ER019";
	public static final String ER020 = "ER020";
	public static final String ER016_ACCOUNT_LOGIN = "ER016";
	// Mess thông báo lỗi không tìm thấy user ở MH ADM002
	public static final String NO_USER_FOUND = "MSG005";
	public static final String NO_DEPARTMENT_FOUND = "MSG006";
	// Mess thông báo có muốn xóa user hay không ở MH ADM005
	public static final String CONFIRM_DELETE_USER = "MSG004";
	// Mess thông báo lỗi ở MH System_Error
	public static final String ERROR_SYSTEM_MESS = "ER015";
	public static final String USER_NOT_EXIST = "ER013";
	public static final String CANT_DELETE_ADMIN = "ER020";
	// Mess thông báo add/update/delete thành công ở MH ADM006
	public static final String ADD_SUCCESSFUL = "MSG001";
	public static final String UPDATE_SUCCESSFUL = "MSG002";
	public static final String UPDATE_ACCESSORY_SUCCESSFUL = "MSG002_ACCESSORY";
	public static final String UPDATE_DEVICE_SUCCESSFUL = "MSG002_DEVICE";
	public static final String DELETE_SUCCESSFUL = "MSG003";
	public static final String DELETE_DEVICE_SUCCESSFUL = "MSG003_DEVICE";
	public static final String IMPORT_DEVICE_SUCCESSFUL = "MSG_IMPORT_DEVICE";
	public static final String EXPORT_DEVICE_SUCCESSFUL = "MSG_EXPORT_DEVICE";


	
	// Tên cột trong DB
	public static final String COLUMN_LOGIN_NAME = "login_name";
	public static final String COLUMN_FULL_NAME = "full_name";
	public static final String EMAIL = "email";
	public static final String COLUMN_CODE_LEVEL = "code_level";
	public static final String COLUMN_END_DATE = "end_date";
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	// key lấy tên DB
	public static final String DATABASE_NAME = "database_name";
	// Action
	public static final String ACTION = "action";
	public static final String SEARCH_ACTION = "search";
	public static final String BACK_ACTION = "back";
	public static final String EDIT_ACTION = "edit";
	public static final String SORT_ACTION = "sort";
	public static final String PAGING_ACTION = "paging";
	public static final String CONFIRM_ACTION = "confirm";
	public static final String ICON_NEXT = ">>";
	public static final String ICON_PREVIOUS = "<<";

	// Dữ liệu config
	public static final int LIMIT_PAGE = Integer.parseInt(ConfigProperties.getData("limit_page"));
	public static final int LIMIT_RECORD = Integer.parseInt(ConfigProperties.getData("limit_record"));
	public static final int FROM_YEAR = Integer.parseInt(ConfigProperties.getData("start_year"));
	public static final String RULE_ADMIN = "Admin";
	public static final String RULE_USER = "User";

	// Name Attribute
	public static final String USER_ID = "userId";
	public static final String LOGIN_NAME = "loginName";
	public static final String PASSWORD = "password";
	public static final String FULLNAME = "fullName";
	public static final String GROUP_ID = "groupId";
	public static final String FULLNAME_KANA = "fullNameKana";
	public static final String YEAR_BIRTHDAY = "yearBirthDay";
	public static final String MONTH_BIRTHDAY = "monthBirthDay";
	public static final String DAY_BIRTHDAY = "dayBirthDay";
	public static final String YEAR_STARTDATE = "yearStartDate";
	public static final String MONTH_STARTDATE = "monthStartDate";
	public static final String DAY_STARTDATE = "dayStartDate";
	public static final String YEAR_ENDDATE = "yearEndDate";
	public static final String MONTH_ENDDATE = "monthEndDate";
	public static final String DAY_ENDDATE = "dayEndDate";
	public static final String TEL = "tel";
	public static final String PASSWORD_CONFIRM = "passwordConfirm";
	public static final String COLE_LEVEL = "codeLevel";
	public static final String TOTAL = "total";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String TOTAL_PAGE = "totalPage";
	public static final String PAGE = "page";
	public static final String SORT_TYPE = "sortType";
	public static final String SORT_BY_FULL_NAME = "sortByFullName";
	public static final String SORT_BY_CODE_LEVEL = "sortByCodeLevel";
	public static final String SORT_BY_END_DATE = "sortByEndDate";
	public static final String CODE_LEVEL = "codeLevel";
	public static final String END_DATE = "endDate";
	public static final String NO_USER_FOUND_MESS = "noUserFoundMess";
	public static final String LIST_ERROR_MESS = "listErrorMess";
	public static final String MESS = "mess";
	public static final String NEXT = "next";
	public static final String PREVIOUS = "previous";
	public static final String NEXT_PAGE = "nextPage";
	public static final String PREVIOUS_PAGE = "previousPage";
	public static final String LIST_PAGING = "listPaging";
	public static final String LIST_GROUPS = "listGroups";
	public static final String LIST_USER_INFOR = "listUserInfor";
	public static final String USER_INFOR = "userInfor";
	public static final String LIST_JAPANS = "listJapans";
	public static final String LIST_YEAR = "listYear";
	public static final String LIST_MONTH = "listMonth";
	public static final String LIST_DAY = "listDay";
	// Key động lấy theo thời gian thực
	public static final String DYNAMIC_KEY = "dynamicKey";
	// Mess xác nhận xóa
	public static final String MESS_CONFIRM_DELETE = "messConfirmDelete";
	// Constant validate
	public static final String BLANK = "";
	public static final int DEFAULT_VALUE = 0;
	public static final int MIN_VALUE_LOGIN_NAME = 4;
	public static final int MAX_VALUE_LOGIN_NAME = 15;
	public static final int MIN_VALUE_PASSWORD = 5;
	public static final int MAX_VALUE_PASSWORD = 15;
	public static final int MAX_VALUE_STRING = 255;
	public static final int MAX_VALUE_EMAIL = 100;
	public static final int MAX_VALUE_TEL = 14;
	public static final int MAX_VALUE_TOTAL = 9;

	// Format validate
	public static final String FORMAT_DATE = "yyyy/MM/dd";
	public static final String FORMAT_LOGIN_NAME = "^[a-zA-Z_][a-zA-Z0-9_]*$";
	public static final String FORMAT_FULL_NAME_KANA = "^[ァ-ンｧ-ﾝﾞﾟー　]*$";
	public static final String FORMAT_EMAIL = "^.*@\\.*.+$";
	public static final String FORMAT_TEL = "^[0-9]{9,12}$";
	public static final String FORMAT_NUMBER_HALF_SIZE = "^[0-9]+$";

	// Character encoding UTF-8
	public static final String CHARACTER_ENDCODING = "UTF-8";
	
	public static final int COLUMN_DEVICE_STT = 0;
	public static final int COLUMN_DEVICE_NAME = 1;
	public static final int COLUMN_DEVICE_DEPARTMENT = 2;
	public static final int COLUMN_DEVICE_MODEL = 3;
	public static final int COLUMN_DEVICE_SERIAL = 4;
	public static final int COLUMN_DEVICE_BRAND = 5;
	public static final int COLUMN_DEVICE_PRODUCT_DATE = 6;
	public static final int COLUMN_DEVICE_IMPORT_DATE = 7;
	public static final int COLUMN_DEVICE_UNIT = 8;
	public static final int COLUMN_DEVICE_AMOUNT = 9;
	public static final int COLUMN_DEVICE_SOURCE = 10;
	public static final int COLUMN_DEVICE_STATUS = 11;
	public static final int COLUMN_DEVICE_FUNC_SCORE = 12;
	public static final int COLUMN_DEVICE_APP_SCORE = 13;
	public static final int COLUMN_DEVICE_MAIN_SCORE = 14;
	public static final int COLUMN_DEVICE_HIS_SCORE = 15;
	public static final int COLUMN_DEVICE_EM_SCORE = 16;
	public static final int COLUMN_DEVICE_TYPE = 17;
	public static final int COLUMN_DEVICE_FRE = 18;

}
