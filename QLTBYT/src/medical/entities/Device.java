package medical.entities;

import medical.utils.Common;

public class Device {
	private int deviceId;
	private int fileId;
	private String deviceName;
	private String deviceModel;
	private String deviceSerial;
	private String department;
	private String brand;

	private String productDate;
	private String importDate;
	
	private String unit;
	private String status;
	private String source;
	private String file;
	
	private int amount;
	private int funcScore;
	private int appScore;
	private int maintainScore;
	private int histoScore;
	private int emScore;
	private String type;
	private String fre;
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getFuncScore() {
		return funcScore;
	}

	public void setFuncScore(int funcScore) {
		this.funcScore = funcScore;
	}

	public int getAppScore() {
		return appScore;
	}

	public void setAppScore(int appScore) {
		this.appScore = appScore;
	}

	public int getMaintainScore() {
		return maintainScore;
	}

	public void setMaintainScore(int maintainScore) {
		this.maintainScore = maintainScore;
	}

	public int getHistoScore() {
		return histoScore;
	}

	public void setHistoScore(int histoScore) {
		this.histoScore = histoScore;
	}

	public int getEmScore() {
		return emScore;
	}

	public void setEmScore(int emScore) {
		this.emScore = emScore;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Device() {
		super();
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceSerial() {
		return deviceSerial;
	}

	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = Common.encryptDate(productDate);
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = Common.encryptDate(importDate);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId + ", deviceName=" + deviceName + ", deviceModel=" + deviceModel
				+ ", deviceSerial=" + deviceSerial + ", department=" + department + ", brand=" + brand
				+ ", productDate=" + productDate + ", importDate=" + importDate + ", unit=" + unit + ", status="
				+ status + ", source=" + source + ", file=" + file + ", amount=" + amount + ", fileId="+fileId+"]";
	}

	/**
	 * @return the fileId
	 */
	public int getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the fre
	 */
	public String getFre() {
		return fre;
	}

	/**
	 * @param fre the fre to set
	 */
	public void setFre(String fre) {
		this.fre = fre;
	}
	


}
