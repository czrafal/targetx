package pojos;

import java.math.BigInteger;

public class DriverInfo {

	private BigInteger IDDriver;
	private Long IDSystem;
	private Long IDVehicle;
	private Long IDRoutes;
	
	private String FName;
	private String LName;
	private double gas;
	private Integer maxspeed;
	private double lat;
	private double lon;
	
	public DriverInfo(){
		
	}
	
	public DriverInfo(BigInteger IDDriver, String FName, String LName, double gas, Integer maxspeed) {
		super();
		this.IDDriver = IDDriver;
		this.FName = FName;
		this.LName = LName;
		this.gas = gas;
		this.maxspeed = maxspeed;
	}

	public Long getIDRoutes() {
		return IDRoutes;
	}

	public void setIDRoutes(Long iDRoutes) {
		IDRoutes = iDRoutes;
	}	

	public BigInteger getIDDriver() {
		return IDDriver;
	}

	public void setIDDriver(BigInteger iDDriver) {
		IDDriver = iDDriver;
	}

	public Long getIDSystem() {
		return IDSystem;
	}

	public void setIDSystem(Long iDSystem) {
		IDSystem = iDSystem;
	}

	public Long getIDVehicle() {
		return IDVehicle;
	}

	public void setIDVehicle(Long iDVehicle) {
		IDVehicle = iDVehicle;
	}

	public String getFName() {
		return FName;
	}

	public void setFName(String fName) {
		FName = fName;
	}

	public String getLName() {
		return LName;
	}

	public void setLName(String lName) {
		LName = lName;
	}

	public double getGas() {
		return gas;
	}

	public void setGas(double gas) {
		this.gas = gas;
	}

	public Integer getMaxspeed() {
		return maxspeed;
	}

	public void setMaxspeed(Integer maxspeed) {
		this.maxspeed = maxspeed;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
	
}
