package backing;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Driver;
import model.Vehicle;
import pojos.DriverAndVehicle;
import beans.DriverListBean;

@ManagedBean(name="driverList")
@ViewScoped
public class DriverList implements Serializable{
	
	private static final long serialVersionUID = 7965007201911390938L;
	
	private Driver driver = new Driver();
	private DriverAndVehicle driverAndVehicle = new DriverAndVehicle();
	private DriverAndVehicle selectedDriver;
	
	private List<DriverAndVehicle> driverList;
	private Vehicle selectedVehicle;
	private List<Vehicle> vehicleList;

	@EJB
	DriverListBean driverListBean;
	
	@PostConstruct
	public void init(){
		driverList = driverListBean.allDriversAndVehiclesShow();
		vehicleList = driverListBean.allVehiclesGet();
	}
	
	public Vehicle getSelectedVehicle() {
		return selectedVehicle;
	}

	public void setSelectedVehicle(Vehicle selectedVehicle) {
		this.selectedVehicle = selectedVehicle;
	}
	
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	public Driver getDriver(){
		return driver;
	}
	
	public void changeListener(){
		System.out.println("listenerttttttttttttttttttttttttttttttr");
	    System.out.println("LISTENER SAYS "+selectedVehicle.getRegNum());
	}
	
	public void addNewDriver() {
		driverAndVehicle.setFName(driver.getFName());
		driverAndVehicle.setLName(driver.getLName());
		driverAndVehicle.setIDDriver(driver.getIDDriver());
		driverAndVehicle.setIDSystem(driver.getIDSystem());
		driverAndVehicle.setPhone(driver.getPhone());
		System.out.println("Wybrany pojazd:"+selectedVehicle.getIDVehicle());
		if(selectedVehicle != null){
			driver.setIDVehicle(selectedVehicle.getIDVehicle());
		}
		driverListBean.addNewDriver(driver);
	}
	
	public DriverAndVehicle getSelectedDriver() {
		return selectedDriver;
	}
	
	public void setSelectedDriver(DriverAndVehicle selectedDriver) {
		this.selectedDriver = selectedDriver;
	}
	
	public DriverAndVehicle getDriverAndVehicle() {
		return driverAndVehicle;
	}

	public void setDriverAndVehicle(DriverAndVehicle driver) {
		this.driverAndVehicle = driver;
	}
	
	public List<DriverAndVehicle> getDriverList() {
		return driverList;
	}

	public void setDriverList(List<DriverAndVehicle> driverList) {
		this.driverList = driverList;
	}

	public List<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}
	
}
