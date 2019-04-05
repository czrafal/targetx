package backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.GpsDevice;
import model.Vehicle;

import org.primefaces.event.RowEditEvent;

import beans.LocatorBean;
import beans.NewVehicleBean;

@ManagedBean(name = "newVehicle")
@SessionScoped
public class NewVehicle implements Serializable{

	private static final long serialVersionUID = -6962210488299583830L;
	private Vehicle vehicle = new Vehicle();
	private List<Vehicle> vehicleList;
	private List<GpsDevice> devicesList;
	private String imei = new String();
	private String iddevice = new String();
	private List<String> devicesStringList = new ArrayList<String>();
	private GpsDevice selectedDevice = new GpsDevice();
	
	@EJB
	private NewVehicleBean newVehicleBean;
	@EJB
	private LocatorBean locatorManagement;
	
	@PostConstruct
	public void init(){
		vehicleList = newVehicleBean.allVehiclesGet();
		devicesList = locatorManagement.allDevicesGet();
		locatorStringList();
	}
	
	public void addVehicle(){
		GpsDevice d = selectedDevice;
		newVehicleBean.addNewVehilce(vehicle);
		vehicleList.add(vehicle);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sukces", "Dodano nowy pojazd.")); 
	}

	public List<String> locatorStringList(){
		for(GpsDevice device:devicesList){
			devicesStringList.add(device.getImei());
		}
		return devicesStringList;
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
		System.out.println("Anulowalem edycję!");
	}
	
	public void onEdit(RowEditEvent event) {  
		System.out.println("Edytowałem imei!");
	     Vehicle vehicle = ((Vehicle) event.getObject());
	     GpsDevice dev = getSelectedDevice();
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

	public List<GpsDevice> getDevicesList() {
		return devicesList;
	}

	public void setDevicesList(List<GpsDevice> devicesList) {
		this.devicesList = devicesList;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getIddevice() {
		return iddevice;
	}

	public void setIddevice(String iddevice) {
		this.iddevice = iddevice;
	}

	public List<String> getDevicesStringList() {
		return devicesStringList;
	}

	public void setDevicesStringList(List<String> devicesStringList) {
		this.devicesStringList = devicesStringList;
	}

	public GpsDevice getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(GpsDevice selectedDevice) {
		this.selectedDevice = selectedDevice;
	}
	
}
