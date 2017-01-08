package backing;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import model.Vehicle;

import org.primefaces.event.RowEditEvent;

import beans.NewVehicleBean;

@ManagedBean(name = "newVehicle")
@RequestScoped
public class NewVehicle {

	private Vehicle vehicle = new Vehicle();
	List<Vehicle> vehicleList;

	@EJB
	private NewVehicleBean newVehicleBean;
	
	@PostConstruct
	public void init(){
		vehicleList = newVehicleBean.allVehiclesGet();
	}
	
	public void addVehicle(){
		newVehicleBean.addNewVehilce(vehicle);
		vehicleList.add(vehicle);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sukces", "Dodano nowy pojazd.")); 
	}

	
	public List<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}
	 public void deleteVehicle(Vehicle id){  
         newVehicleBean.removeVehicle(id);
         vehicleList.remove(id);
   }
	
	public void onCancel(RowEditEvent event){
		System.out.println("Anulowa³em edycjê!");
	}
	
	 public void onEdit(RowEditEvent event) {  
	     Vehicle vehicle = ((Vehicle) event.getObject());   
	     newVehicleBean.updateVehicle(vehicle);
	    }  
	 
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public NewVehicleBean getNewVehicleBean() {
		return newVehicleBean;
	}

	public void setNewVehicleBean(NewVehicleBean newVehicleBean) {
		this.newVehicleBean = newVehicleBean;
	}
}
