package backing;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import pojos.DriverInfo;
import beans.CacheDriversSingleton;
import beans.VehiclesListBean;

@ManagedBean(name = "vehicleListReal")
@ViewScoped
public class VehicleListReal implements Serializable {

	private static final long serialVersionUID = 7160455397627208242L;

	private List<DriverInfo> vehicleListReal;

	@EJB
	VehiclesListBean vehicleListBean;

	@EJB
	CacheDriversSingleton activeDriversList;
	
	private DriverInfo selectedCar;
	
	@PostConstruct
	public void vehicleListRealInit() {
		vehicleListReal = vehicleListBean.allDriverRealShow();
	}
	
	public void onRowSelect(SelectEvent event) {
		DriverInfo driver = ((DriverInfo) event.getObject());
		BigInteger driverID = ((DriverInfo) event.getObject()).getIDDriver();
        FacesMessage msg = new FacesMessage("Car Selected", String.valueOf(driver.getPosition().getLatitude()+" "+driver.getPosition().getLongitude() +" rej:"+driver.getVehicle().getRegNum()) +" iddriver:"+driverID);

        DriverInfo car = this.selectedCar;
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
   /*
    * Listener for driver list poll
    */
	public void vehicleListListener() {
		List<DriverInfo> activeDrivers = activeDriversList.getDriversCache(new Long(2));
		if (activeDrivers!=null) {
			vehicleListReal = activeDrivers;
//			mapBean.buildRealGraphicsModel();
//			mapBean.refreshGraphicsModel()
			com.gisfaces.model.map.Background.getSelectItems();
		}
	}

	public List<DriverInfo> getVehicleList() {
		List<DriverInfo> drivers = activeDriversList.getDriversCache(new Long(2));
		if (drivers != null) {
			vehicleListReal = drivers;
		}
		return vehicleListReal;
	}
	
	public void vehhicleListTest(){
		long id = 352848022867325l;
		activeDriversList.driverInfoByDeviceImei(id);
	}
	
	public void setVehicleList(List<DriverInfo> vehicleList) {
		this.vehicleListReal = vehicleList;
	}
	
	public List<DriverInfo> getVehicleListReal() {
		return vehicleListReal;
	}

	public void setVehicleListReal(List<DriverInfo> vehicleListReal) {
		this.vehicleListReal = vehicleListReal;
	}

	public DriverInfo getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(DriverInfo selectedCar) {
		this.selectedCar = selectedCar;
	}

}
