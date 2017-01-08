package backing;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import model.Driver;
import model.Vehicle;

import org.primefaces.event.RowEditEvent;

import pojos.DriverAndVehicle;
import beans.DriverListBean;

@ManagedBean(name="driverAndVehicleList")
@RequestScoped
public class DriverAndVehicleBean {

	//private static final long serialVersionUID = -4248248078204413721L;
	private List<Driver> driverList;
	private List<Vehicle> vehicleList;
	private Vehicle selectedVehicle;
	private DriverAndVehicle driverAndVehicle = new DriverAndVehicle();
	private Driver selectedDriver;
	private Driver newDriver = new Driver();
	
	
	@EJB
	DriverListBean driverListBean;
	
	public DriverListBean getDriverListBean() {
		return driverListBean;
	}

	public void setDriverListBean(DriverListBean driverListBean) {
		this.driverListBean = driverListBean;
	}

	@PostConstruct
	public void init(){
		//driverList = driverListBean.allDriverShow(Driver.FIND_ALL_DRIVERS);
		driverList = driverListBean.allDriverShow(model.Driver.FIND_ALL_DRIVERS);
		vehicleList = driverListBean.allVehiclesGet();
	}

	public void changeListener(){

	}
	
	public DriverAndVehicle getDriverAndVehicle() {
		return driverAndVehicle;
	}

	public void setDriverAndVehicle(DriverAndVehicle driver) {
		this.driverAndVehicle = driver;
	}
	
	public void addNewDriver() {
		System.out.println("Dodawanie nowego drivera!!!!");
		System.out.println("Driver fName:"+newDriver.getFName());
//		System.out.println("Driver lName:"+driver.getLName());
//		System.out.println("Driver phone:"+driver.getPhone());
//		System.out.println("Driver idVehicle:"+selectedVehicle.getIDVechicle());
////		System.out.println("aaaaaa"+driverAndVehicle.getBrand());
////		System.out.println(driverAndVehicle.getFName());
////		
//		driverAndVehicle.setFName(driver.getFName());
//		driverAndVehicle.setLName(driver.getLName());
//		driverAndVehicle.setIDDriver(driver.getIDDriver());
//		driverAndVehicle.setIDSystem(driver.getIDSystem());
//		driverAndVehicle.setPhone(driver.getPhone());
//		System.out.println("Wybrany pojazd:"+selectedVehicle.getIDVechicle());
//		
		if(selectedVehicle != null){
			newDriver.setIDVehicle(selectedVehicle.getIDVehicle());
		}
		
		driverListBean.addNewDriver(newDriver);
		driverList.add(newDriver);
	}
	
	 public Driver getNewDriver() {
		return newDriver;
	}

	public void setNewDriver(Driver newDriver) {
		this.newDriver = newDriver;
	}

	public void onEdit(RowEditEvent event) {  
	        FacesMessage msg = new FacesMessage("Zapisano zmian� dla ", ((Driver) event.getObject()).getLName());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
		 System.out.println("Zapisano zmian� ID pojazdu stare "+ ((Driver) event.getObject()).getIDVehicle());
	     Driver driver = ((Driver) event.getObject());   
		 System.out.println("Zapisano zmian� dla "+ driver.getLName());
	     System.out.println("Nazwisko po zmianie:"+driver.getFName());   
	     driverListBean.updateDriver(driver);
	    }  
	      
	    public void deleteDriver(Long id){  
	    	
	          System.out.println("Usuwam kierowc� o id:"+id);
	          Driver kierowca =  driverListBean.removeDriver(id);
	          driverList.remove(kierowca);
	    }
	        		
	 
	    public void onCancel(RowEditEvent event) {  
//	        FacesMessage msg = new FacesMessage("Anulowano zmian� dla ", ((Driver) event.getObject()).getLName());  
//	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	    	System.out.println("Anulowano zmian� dla "+ ((Driver) event.getObject()).getLName());
	    }  
	    public Driver getSelectedDriver() {
			return selectedDriver;
		}

		public void setSelectedDriver(Driver selectedDriver) {
			this.selectedDriver = selectedDriver;
		}

		public Vehicle getSelectedVehicle() {
			return selectedVehicle;
		}

		public void setSelectedVehicle(Vehicle vehicle) {
			this.selectedVehicle = vehicle;
		}

		public List<Vehicle> getVehicleList() {
			return vehicleList;
		}

		public void setVehicleList(List<Vehicle> vehicleList) {
			this.vehicleList = vehicleList;
		}

		public List<Driver> getDriverList() {
			return driverList;
		}

		public void setDriverList(List<Driver> driverList) {
			this.driverList = driverList;
		}

}
