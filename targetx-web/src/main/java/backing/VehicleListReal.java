package backing;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import pojos.DriverInfo;
import beans.CacheDriversSingleton;
import beans.DriverListBean;
import beans.VehiclesListBean;

@ManagedBean(name="vehicleListReal")
@SessionScoped
public class VehicleListReal implements Serializable{

	private static final long serialVersionUID = 7160455397627208242L;

	private List<DriverInfo> vehicleListReal;
	private List<DriverInfo> selectedCars;
	
	@EJB
	VehiclesListBean vehicleListBean;
	
	@EJB
	CacheDriversSingleton activeDriversList;
	{
	try {
		activeDriversList = (CacheDriversSingleton) new InitialContext().lookup("java:global/targetx-ear/targetx-ejb-1.0/CacheDriversSingleton");
	}
	catch (NamingException e) {
		e.printStackTrace();
	}  
	}
	@PostConstruct
	public void vehicleListRealInit(){
		vehicleListReal = vehicleListBean.allDriverRealShow();
//		vehicleListReal = vehicleListBean.allDriverRealShow("Select NEW pojos.DriverInfo(d.IDDriver, d.FName, d.LName, g.gas, g.maxspeed) from Driver d, Geopoint g where d.IDDriver = g.IDDriver and g.IDGeopoints = (SELECT distinct max(g1.IDGeopoints) from Geopoint g1 where g.IDDriver = g1.IDDriver group BY g1.IDDriver) and g.IDSystem = 2 group by d.IDDriver, g.gas, g.maxspeed, g.distance");
	}

	public List<DriverInfo> getVehicleList() {
		
		if(activeDriversList.getDriversCache(new Long(2)) != null){
			List<DriverInfo> activeDrivers = activeDriversList.getDriversCache(new Long(2));
			vehicleListReal = activeDrivers;
			/*
			for(DriverInfo driver : vehicleListReal){
				for(DriverInfo driverCache : activeDrivers){
					if(driver.getIDDriver().equals(driverCache.getIDDriver())){
						int index = vehicleListReal.indexOf(driver);
						driver.setGas(driverCache.getGas());
						driver.setMaxspeed(driverCache.getMaxspeed());
						vehicleListReal.set(index, driver);
					}
				}
			}*/	
		}
		
		return vehicleListReal;
	}

	public void setVehicleList(List<DriverInfo> vehicleList) {
		
		this.vehicleListReal = vehicleList;
	}

	public List<DriverInfo> getSelectedCars() {
		return selectedCars;
	}

	public void setSelectedCars(List<DriverInfo> selectedCars) {
		this.selectedCars = selectedCars;
	}
}
