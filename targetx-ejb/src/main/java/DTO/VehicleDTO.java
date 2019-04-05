package DTO;

import java.util.Date;

public class VehicleDTO {

	
	private Long IDVehicle;
	private String brand;
	private Date buyDate;
	private Date checkEnd;
	private Date checkStart;
	private Double combustion;
	private Long IDSystem;
	private String model;
	private Date OCDateEnd;
	private Date OCDateStart;
	private String regNum;

	public VehicleDTO() {
	}

	public Long getIDVehicle() {
		return this.IDVehicle;
	}

	public void setIDVehicle(Long IDVehicle) {
		this.IDVehicle = IDVehicle;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Date getBuyDate() {
		return this.buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public Date getCheckEnd() {
		return this.checkEnd;
	}

	public void setCheckEnd(Date checkEnd) {
		this.checkEnd = checkEnd;
	}

	public Date getCheckStart() {
		return this.checkStart;
	}

	public void setCheckStart(Date checkStart) {
		this.checkStart = checkStart;
	}

	public Double getCombustion() {
		return this.combustion;
	}

	public void setCombustion(Double combustion) {
		this.combustion = combustion;
	}

	public Long getIDSystem() {
		return this.IDSystem;
	}

	public void setIDSystem(Long IDSystem) {
		this.IDSystem = IDSystem;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getOCDateEnd() {
		return this.OCDateEnd;
	}

	public void setOCDateEnd(Date OCDateEnd) {
		this.OCDateEnd = OCDateEnd;
	}

	public Date getOCDateStart() {
		return this.OCDateStart;
	}

	public void setOCDateStart(Date OCDateStart) {
		this.OCDateStart = OCDateStart;
	}

	public String getRegNum() {
		return this.regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}
	
}
