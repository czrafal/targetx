package beans;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.traccar.model.Device;

import pojos.DriverInfo;
import model.Geopoint;
import model.Route;
import model.Vehicle;

@Stateless
public class VehiclesListBean {
	
	@PersistenceContext(unitName="CarMonitoring")
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Vehicle> allVehicleShow(String findAllVehicles) {
		return entityManager.createNamedQuery(findAllVehicles).getResultList();
	}
	
	public Vehicle getVehicleByRegNum(Long idSystem, Long idVehicle){
		System.out.println("metoda getVehicleByRegNum "+idSystem+" "+idVehicle);
		Query zapytanie = entityManager.createNamedQuery(Vehicle.FIND_VEHICLES_BY_REG);
		zapytanie.setParameter("IDSystem", idSystem);
		zapytanie.setParameter("idVehicle", idVehicle);
		
		Vehicle listOut = (Vehicle) zapytanie.getSingleResult();
		System.out.println("Z bazy pobra�em "+listOut.getBrand());
		return listOut;
	}
	
	public void addNewVehicle(Vehicle vehicle){
		
		System.out.println("Dodawanie nowego pojazdu:"+vehicle.getModel());
		
		entityManager.persist(vehicle);
	}
	
	@SuppressWarnings("unchecked")
	public List<DriverInfo> allDriverRealShow(){
		List<DriverInfo> driverInfoRetList = new ArrayList<DriverInfo>();
//		List<Object[]> driverInfoList = entityManager.createNativeQuery("Select v.*, g.*, r.idroutes from Geopoints g RIGHT JOIN Vehicles v on v.IDVehicle = g.IDVehicle and g.IDGeopoints = (SELECT max(g1.IDGeopoints) from Geopoints g1 where g.IDVehicle = g1.IDVehicle OR g1.IDGeopoints is null) LEFT JOIN Gpsdevices d on v.IDDevice = d.IDDevice LEFT JOIN Routes r on r.idroutes = g.idroutes").getResultList();
		List<Object[]> driverInfoList = entityManager.createQuery("Select v, g, r "
				+ "from Geopoint g RIGHT OUTER JOIN g.vehicle v "
				+ "LEFT JOIN v.gpsDevice d "
				+ "LEFT JOIN g.route r "
				+ "where g.vehicle = v and v.IDSystem = 2 "
				+ "and g.IDGeopoints = (SELECT max(g1.IDGeopoints) from Geopoint g1 where g.vehicle = g1.vehicle) OR g is null "
				+ "and v.gpsDevice = d and r = g.route OR r is null").getResultList();
		
		for(Object[] obj : driverInfoList){
			DriverInfo driverObj = new DriverInfo();
			Vehicle vehicle = (Vehicle)obj[0];
			Geopoint geopoint = (Geopoint)obj[1];	
			Route route = (Route)obj[2];
			driverObj.setIDDriver(BigInteger.ONE);
			if(vehicle!=null){
				driverObj.setVehicle(vehicle);
				Device device = new Device();
				if(vehicle.getGpsDevice() != null && StringUtils.isNotBlank(vehicle.getGpsDevice().getImei())){
					device.setId(Long.valueOf(vehicle.getGpsDevice().getImei()));	
				}
				driverObj.setDevice(device);
			}
			if(geopoint!=null){
				driverObj.setGas(geopoint.getGas());
				driverObj.setMaxspeed(geopoint.getMaxspeed());
				driverObj.setGeopoint(geopoint);
			}
			if(route!=null){
				driverObj.setRoute(route);
			}
			driverInfoRetList.add(driverObj);
		}
		return driverInfoRetList; 
	}
	
}
