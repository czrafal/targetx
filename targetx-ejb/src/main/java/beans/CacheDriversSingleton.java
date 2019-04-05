package beans;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.traccar.database.DeviceManager;
import org.traccar.model.Device;

import pojos.DriverInfo;

@Singleton
@LocalBean
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class CacheDriversSingleton {

	private ConcurrentHashMap<Long, List<DriverInfo>> driversCache = null;

	@EJB
	VehiclesListBean vehicleListBean;
    private Map<Long, Device> devicesById;
    private Map<String, Device> devicesByUniqueId;
    
	@PostConstruct
	public void initialize() {
		System.out.println("Utworzylem nowa mape cache'owania pojazdow");
		this.driversCache = new ConcurrentHashMap<>();
		this.driversCache.put(new Long(new String("2")),
				vehicleListBean.allDriverRealShow());

		if (devicesById == null) {
			devicesById = new ConcurrentHashMap<>();
		}
		if (devicesByUniqueId == null) {
			devicesByUniqueId = new ConcurrentHashMap<>();
		}
	}

	public boolean updateVehicleCash(DriverInfo driverInfo) {
		System.out.println("updateVehicleCash");
		boolean isTrue = false;
		DriverInfo driver = findVehicle(driverInfo);
		System.out.println("updateVehicleCash 2");
		List<DriverInfo> listDrivers = driversCache.get(driverInfo.getIDSystem());
		System.out.println("updateVehicleCash 3");
		listDrivers.set(listDrivers.indexOf(driver), driverInfo);
		System.out.println("updateVehicleCash 4");
		driversCache.replace(driverInfo.getIDSystem(), listDrivers);
		System.out.println("W mapie pojazdow znajduje sie:");

		for (Map.Entry<Long, List<DriverInfo>> entry : driversCache.entrySet()) {
			List<DriverInfo> value = entry.getValue();
			for (DriverInfo pojazd : value) {
				System.out.println("IDSystem:" + pojazd.getIDSystem()
						+ " IDDriver:" + pojazd.getIDDriver() + " Stan paliwa:"
						+ pojazd.getGas());
			}
		}
		return isTrue;
	}

	private DriverInfo findVehicle(DriverInfo driverInfo){
		DriverInfo newDriver = new DriverInfo();
		System.out.println("updateVehicleCash 5");
		if (driversCache.containsKey(driverInfo.getIDSystem())) {
			System.out.println("updateVehicleCash 6");
			List<DriverInfo> listVehicles = driversCache.get(driverInfo.getIDSystem());
			System.out.println("updateVehicleCash 7");
			for (DriverInfo info : listVehicles) {
				System.out.println(info.getVehicle().getIDVehicle());
				if (info.getVehicle() == driverInfo.getVehicle()) {
					System.out.println("updateVehicleCash 8");
					newDriver = info;
				}
			}
		} else {
			System.out.println("updateVehicleCash 9");
			newDriver = driverInfo;
		}
		System.out.println("return updateVehicleCash "+newDriver.getVehicle().getRegNum());
		return newDriver;
	}
	
	public Device deviceSessionByImei(String imei){
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDeviceByUniqueId(imei);
		return device;
	}
	
	public List<DriverInfo> getDriversCache(Long idSystem) {
		List<DriverInfo> listDrivers = driversCache.get(idSystem);
		return listDrivers;
	}

	public DriverInfo driverInfoByDeviceImei(long id) {
		System.out.println("driverInfoByDeviceImei id "+id);
    	List<DriverInfo> devices = getDriversCache(2L);
    	DriverInfo driver = new DriverInfo();
    	for(DriverInfo device:devices){
    		if((device.getDevice() != null) && (device.getDevice().getId() != 0)){
    			driver = device;
    		}
    	}
    	
    	System.out.println("driverInfoByDeviceImei driver "+driver.getDevice().getId());
        return driver;
    }
	
}
