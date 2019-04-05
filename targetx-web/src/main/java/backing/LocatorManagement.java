package backing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import model.GpsDevice;

import org.primefaces.event.RowEditEvent;

import beans.LocatorBean;

@ManagedBean(name = "locatorManagement")
@RequestScoped
public class LocatorManagement {
	
	@EJB
	private LocatorBean locatorBean;
	private List<GpsDevice> locatorList = new ArrayList<GpsDevice>();
	private GpsDevice gpsDevice = new GpsDevice(); 
	private List<String> locatorStringList = new ArrayList<String>();
	private GpsDevice selectedDevice = new GpsDevice();
	
	@PostConstruct
	public void init(){
		locatorList = locatorBean.allDevicesGet();
	}
	
	public void addLocator(){
		locatorBean.addNewDevice(gpsDevice);
		locatorList.add(gpsDevice);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sukces", "Dodano nowy lokalizator.")); 
	}

	public void deleteDevice(GpsDevice id){  
        locatorBean.removeDevice(id);
        locatorList.remove(id);
    }
	
	public void onEdit(RowEditEvent event) {  
	     GpsDevice device = ((GpsDevice) event.getObject());   
	     locatorBean.updateDevice(device);
	}  
	
	public GpsDevice getGpsDevice() {
		return gpsDevice;
	}

	public void setGpsDevice(GpsDevice gpsDevice) {
		this.gpsDevice = gpsDevice;
	}

	public List<GpsDevice> getLocatorList() {
		return locatorList;
	}

	public void setLocatorList(ArrayList<GpsDevice> locatorList) {
		this.locatorList = locatorList;
	}

	public List<String> getLocatorStringList() {
		return locatorStringList;
	}

	public void setLocatorStringList(List<String> locatorStringList) {
		this.locatorStringList = locatorStringList;
	}

	public GpsDevice getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(GpsDevice selectedDevice) {
		this.selectedDevice = selectedDevice;
	}
	
	
}
