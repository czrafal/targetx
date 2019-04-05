package pojos;

import java.math.BigInteger;

import model.Geopoint;
import model.Route;
import model.Vehicle;

import org.traccar.model.Device;
import org.traccar.model.Position;

public class DriverInfo {

	private BigInteger IDDriver; // vehicle driver?
	private Long IDSystem = 2l;
	private Route route;
	private double gas;
	private Integer maxspeed;
	private Geopoint geopoint;
	private boolean activeSession;
	private Device device;
	private Position position;
	private Vehicle vehicle; 
	
	public DriverInfo(){
		
	}
	
	public DriverInfo(BigInteger IDDriver, String FName, String LName, double gas, Integer maxspeed) {
		this.IDDriver = IDDriver;
		this.gas = gas;
		this.maxspeed = maxspeed;
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

	/*public double getLat() {
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
*/
/*	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}*/

	public boolean isActiveSession() {
		return activeSession;
	}

	public void setActiveSession(boolean activeSession) {
		this.activeSession = activeSession;
	}
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

/*	public String getVehicleRegNum() {
		return vehicleRegNum;
	}

	public void setVehicleRegNum(String vehicleRegNum) {
		this.vehicleRegNum = vehicleRegNum;
	}*/

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Geopoint getGeopoint() {
		return geopoint;
	}

	public void setGeopoint(Geopoint geopoint) {
		this.geopoint = geopoint;
	}

}
