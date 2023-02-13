package medical.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import medical.entities.Device;
import medical.utils.Common;
import medical.utils.Constants;

public class FileWriter {
	public void exportExcel(List<Device> devices) {
		System.out.println("Create file excel");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Device_Info");
		int rowNum = 0;

		// Header of file excel
		Row firstRow = sheet.createRow(rowNum++);
		Cell sttCell = firstRow.createCell(Constants.COLUMN_DEVICE_STT);
		sttCell.setCellValue("STT");
		Cell nameCell = firstRow.createCell(Constants.COLUMN_DEVICE_NAME);
		nameCell.setCellValue("Tên tài sản");
		Cell departmentCell = firstRow.createCell(Constants.COLUMN_DEVICE_DEPARTMENT);
		departmentCell.setCellValue("Khoa, phòng");
		Cell modelCell = firstRow.createCell(Constants.COLUMN_DEVICE_MODEL);
		modelCell.setCellValue("Model (Ký hiệu)");
		Cell serialCell = firstRow.createCell(Constants.COLUMN_DEVICE_SERIAL);
		serialCell.setCellValue("Số Serial (Số hiệu TSCD)");
		Cell brandCell = firstRow.createCell(Constants.COLUMN_DEVICE_BRAND);
		brandCell.setCellValue("Hãng SX/Nước SX");
		Cell productDateCell = firstRow.createCell(Constants.COLUMN_DEVICE_PRODUCT_DATE);
		productDateCell.setCellValue("Năm sản xuất");
		Cell importDateCell = firstRow.createCell(Constants.COLUMN_DEVICE_IMPORT_DATE);
		importDateCell.setCellValue("Tháng năm đưa vào sử dụng");
		Cell unitCell = firstRow.createCell(Constants.COLUMN_DEVICE_UNIT);
		unitCell.setCellValue("Đơn vị tính");
		Cell amountCell = firstRow.createCell(Constants.COLUMN_DEVICE_AMOUNT);
		amountCell.setCellValue("Số lượng");
		Cell sourceCell = firstRow.createCell(Constants.COLUMN_DEVICE_SOURCE);
		sourceCell.setCellValue("Nguồn gốc kinh phí");
		Cell statusCell = firstRow.createCell(Constants.COLUMN_DEVICE_STATUS);
		statusCell.setCellValue("Tình trạng khi nhận");

		Cell funcScore = firstRow.createCell(Constants.COLUMN_DEVICE_FUNC_SCORE);
		funcScore.setCellValue("Điểm chức năng");
		Cell appScore = firstRow.createCell(Constants.COLUMN_DEVICE_APP_SCORE);
		appScore.setCellValue("Điểm ứng dụng");
		Cell mainScore = firstRow.createCell(Constants.COLUMN_DEVICE_MAIN_SCORE);
		mainScore.setCellValue("Điểm bảo trì ");
		Cell hisScore = firstRow.createCell(Constants.COLUMN_DEVICE_HIS_SCORE);
		hisScore.setCellValue("Điểm lịch sử");
		Cell emScore = firstRow.createCell(Constants.COLUMN_DEVICE_EM_SCORE);
		emScore.setCellValue("Giá trị EM");
		Cell type = firstRow.createCell(Constants.COLUMN_DEVICE_TYPE);
		type.setCellValue("Loại");
		Cell fre = firstRow.createCell(Constants.COLUMN_DEVICE_FRE);
		fre.setCellValue("Tần suất bảo trì");

		for (int i = 0; i < devices.size(); i++) {
			Device device = devices.get(i);
			
			Row row = sheet.createRow(rowNum++);
			Cell cell0 = row.createCell(0);
			cell0.setCellValue("" + (i+1));
			
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(device.getDeviceName());
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(device.getDepartment());
			Cell cell3 = row.createCell(3);
			cell3.setCellValue(device.getDeviceModel());
			Cell cell4 = row.createCell(4);
			cell4.setCellValue(device.getDeviceSerial());
			Cell cell5 = row.createCell(5);
			cell5.setCellValue(device.getBrand());
			Cell cell6 = row.createCell(6);
			cell6.setCellValue(device.getProductDate());
			Cell cell7 = row.createCell(7);
			cell7.setCellValue(device.getImportDate());
			Cell cell8 = row.createCell(8);
			cell8.setCellValue(device.getUnit());
			Cell cell9 = row.createCell(9);
			cell9.setCellValue(device.getAmount());
			Cell cell10 = row.createCell(10);
			cell10.setCellValue(device.getSource());
			Cell cell11 = row.createCell(11);
			cell11.setCellValue(device.getStatus());
			Cell cell12 = row.createCell(12);
			cell12.setCellValue(device.getFuncScore());
			Cell cell13 = row.createCell(13);
			cell13.setCellValue(device.getAppScore());
			Cell cell14 = row.createCell(14);
			cell14.setCellValue(device.getMaintainScore());
			Cell cell15 = row.createCell(15);
			cell15.setCellValue(device.getHistoScore());
			Cell cell16 = row.createCell(16);
			cell16.setCellValue(device.getEmScore());
			Cell cell17 = row.createCell(17);
			cell17.setCellValue(device.getType());
			Cell cell18 = row.createCell(18);
			cell18.setCellValue(device.getFre());
		}

		
		
		try {
			File file = new File(Constants.FILE_EXPORT + Common.getDateNow() + Constants.TYPE_FILE_EXPORT);
			for (int i = 1; i < 10; i++) {

				if (file.exists()) {
					file = new File(
							Constants.FILE_EXPORT + Common.getDateNow() + " (" + i + ")" + Constants.TYPE_FILE_EXPORT);
				}
			}

			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}
}
