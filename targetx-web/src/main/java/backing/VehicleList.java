package backing;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Vehicle;
import beans.VehiclesListBean;

@ManagedBean(name="vehicleList")
@SessionScoped
public class VehicleList implements Serializable{
/*
 * Klasa wyswietlajaca tabelke z pojazdami zaraz na poczatku, po sprawdzeniu usunac ewnetualnie
 * przerobic na przeglady pojazdow
 */
	private static final long serialVersionUID = -2620841099499533628L;
	private List<Vehicle> vehicleList;
	private Vehicle vehicle = new Vehicle();

	@EJB
	VehiclesListBean vehicleListBean;
	
	@PostConstruct
	public void init(){
		vehicleList = vehicleListBean.allVehicleShow(Vehicle.FIND_ALL_VEHICLES);
	}

	public void addNewVehicle(){
		vehicleListBean.addNewVehicle(vehicle);
		vehicleList.add(vehicle);
	}
	
	public List<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
}
