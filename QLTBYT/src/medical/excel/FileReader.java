package medical.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import medical.entities.Device;
import medical.utils.Constants;

public class FileReader {

	    public static void main(String[] args) throws IOException {
	        String excelFilePath = "D:\\Quanlythietbi\\Servers\\ExcelFiles\\devices.xlsx";
	        FileReader reader = new FileReader();
	        List<Device> devices = reader.readExcel(excelFilePath);
	        for (Device device : devices) {
	            System.out.println(device.toString());
	        }
	    }
	 
	    public List<Device> readExcel(String excelFilePath) throws IOException {
	        List<Device> listDevices = new ArrayList<>();
	 
	        // Get file
	        File file = new File(excelFilePath);
	        FileInputStream fis = new FileInputStream(file);
	 
	        // Get workbook
	        XSSFWorkbook workbook = new XSSFWorkbook(fis);
	 
	        // Get sheet
	        XSSFSheet sheet = workbook.getSheetAt(0);
	 
	        // Get all rows
	        Iterator<Row> iterator = sheet.iterator();
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            if (nextRow.getRowNum() == 0) {
	                // Ignore header
	                continue;
	            }
	 
	            // Get all cells
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	 
	            // Read cells and set value for book object
	            Device device = new Device();
	            while (cellIterator.hasNext()) {
	                //Read cell
	                Cell cell = cellIterator.next();
	                
	                
	                
	                Object cellValue = getCellValue(cell);
	                if (cellValue == null || cellValue.toString().isEmpty()) {
	                    continue;
	                }
	                // Set value for book object
	                int columnIndex = cell.getColumnIndex();
	                switch (columnIndex) {
	                case Constants.COLUMN_DEVICE_STT:
	                    break;
	                case Constants.COLUMN_DEVICE_NAME:
	                    device.setDeviceName("" + getCellValue(cell));
	                    break;
	                case Constants.COLUMN_DEVICE_DEPARTMENT:
	                    device.setDepartment("" + getCellValue(cell));
	                    break;
	                case Constants.COLUMN_DEVICE_MODEL:
	                    device.setDeviceModel(""+ getCellValue(cell));
	                    break;
	                case Constants.COLUMN_DEVICE_SERIAL:
	                    device.setDeviceSerial("" + getCellValue(cell));
	                    break;
	                case Constants.COLUMN_DEVICE_BRAND:
	                    device.setBrand("" + getCellValue(cell));
	                    break;
	                case Constants.COLUMN_DEVICE_PRODUCT_DATE:
	                    device.setProductDate("" + getCellValue(cell));
	                    break;
	                case Constants.COLUMN_DEVICE_IMPORT_DATE:
	                    device.setImportDate("" +  getCellValue(cell));
	                    break;
	                case Constants.COLUMN_DEVICE_UNIT:
	                    device.setUnit("" + getCellValue(cell));
	                    break;
	                case Constants.COLUMN_DEVICE_AMOUNT:
	                    device.setAmount(new BigDecimal((double) cellValue).intValue());
	                    break;
	                case Constants.COLUMN_DEVICE_SOURCE:
	                    device.setSource("" + getCellValue(cell));
	                    break;
	                case Constants.COLUMN_DEVICE_STATUS:
	                    device.setStatus("" + getCellValue(cell));
	                    break;

	                default:
	                    break;
	                }
	 
	            }
	            listDevices.add(device);
	        }
	 
	        workbook.close();
	        fis.close();
	 
	        return listDevices;
	    }
	 
//	    // Get Workbook
//	    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
//	        Workbook workbook = null;
//	        if (excelFilePath.endsWith("xlsx")) {
//	            workbook = new XSSFWorkbook(inputStream);
//	        } else if (excelFilePath.endsWith("xls")) {
//	            workbook = new HSSFWorkbook(inputStream);
//	        } else {
//	            throw new IllegalArgumentException("The specified file is not Excel file");
//	        }
//	 
//	        return workbook;
//	    }
	 
	    // Get cell value
	    private static Object getCellValue(Cell cell) {
	        CellType cellType = cell.getCellType();
	        Object cellValue = null;
	        switch (cellType) {
	        case BOOLEAN:
	            cellValue = cell.getBooleanCellValue();
	            break;
	        case FORMULA:
	            Workbook workbook = cell.getSheet().getWorkbook();
	            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	            cellValue = evaluator.evaluate(cell).getNumberValue();
	            break;
	        case NUMERIC:
	            cellValue = cell.getNumericCellValue();
	            break;
	        case STRING:
	            cellValue = cell.getStringCellValue();
	            break;
	            
	        case _NONE:
	        case BLANK:
	        case ERROR:
	            break;
	        default:
	            break;
	        }
	 
	        return cellValue;
	    }
}
