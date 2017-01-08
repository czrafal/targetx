package beans;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Startup;

import pojos.DriverInfo;

@Singleton
@LocalBean
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class CacheDriversSingleton {

	private ConcurrentHashMap<BigInteger, List<DriverInfo>> driversCache = new ConcurrentHashMap<>();

	@EJB
	VehiclesListBean vehicleListBean;
	
	@PostConstruct
	public void initialize() {
		System.out.println("Utworzy�em now� map� cache'owania pojazdow");
		this.driversCache.put(new BigInteger(new String("2")), vehicleListBean.allDriverRealShow());
	}

	public boolean checkCache(Long idSystemUser, BigInteger idDriver, Timestamp dateTime, Double pozycja, Double stanPaliwa, int maxspeed) {

		boolean isTrue = false;

		if (driversCache.containsKey(idSystemUser)) {
			List<DriverInfo> listDrivers = driversCache.get(idSystemUser);
			for (DriverInfo info : listDrivers) {
				if (info.getIDDriver() == idDriver) {
					info.setIDDriver(idDriver);
					info.setIDSystem(idSystemUser);

					info.setGas(stanPaliwa);
					info.setMaxspeed(maxspeed);
					listDrivers.add(listDrivers.indexOf(info), info);
					driversCache.put(BigInteger.valueOf(idSystemUser), listDrivers);
					isTrue = true;
				}
			}

		} else {
			List<DriverInfo> listDrivers = new LinkedList<>();
			DriverInfo driverInfo = new DriverInfo();
			driverInfo.setIDDriver(idDriver);
			driverInfo.setIDSystem(idSystemUser);
			driverInfo.setMaxspeed(maxspeed);
			driverInfo.setGas(stanPaliwa);
			listDrivers.add(driverInfo);
			isTrue = false;

			driversCache.put(idDriver, listDrivers);
		}

		System.out.println("W mapie pojazd�w znajduje si�:");

		for (Map.Entry<BigInteger, List<DriverInfo>> entry : driversCache.entrySet()) {
			List<DriverInfo> value = entry.getValue();
			for (DriverInfo pojazd : value) {
				System.out.println("IDSystem:" + pojazd.getIDSystem()
						+ " IDDriver:" + pojazd.getIDDriver() + " Stan paliwa:"
						+ pojazd.getGas());
			}
		}
		return isTrue;
	}

	public List<DriverInfo> getDriversCache(Long idSystem) {
		List<DriverInfo> listDrivers = driversCache.get(new BigInteger(new String("2")));
		return listDrivers;
	}
}
