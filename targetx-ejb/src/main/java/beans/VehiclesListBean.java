package beans;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pojos.DriverInfo;
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
		List<Object[]> driverInfoList = entityManager.createNativeQuery("Select d.IDDriver, d.fname, d.lname, g.gas, g.maxspeed, g.lat, g.lon from Drivers d LEFT OUTER JOIN Geopoints g on d.IDDriver = g.IDDriver and d.IDSystem = 2 and g.IDGeopoints in (SELECT distinct max(g1.IDGeopoints) from Geopoints g1 where g.IDDriver = g1.IDDriver OR g1.IDSystem is null group BY g1.IDDriver)").getResultList();
		for(Object[] obj:driverInfoList){
			DriverInfo driverObj = new DriverInfo();
			if(obj[0]!=null){
				driverObj.setIDDriver((BigInteger) obj[0]);	
			}
			if(obj[1]!=null){
				driverObj.setFName((String) obj[1]);	
			}
			if(obj[2]!=null){
				driverObj.setLName((String) obj[2]);	
			}
			if(obj[3]!=null){
				driverObj.setGas((double) obj[3]);	
			}
			if(obj[4]!=null){
				driverObj.setMaxspeed((int) obj[4]);	
			}
			if(obj[5]!=null){
				driverObj.setLat((double) obj[5]);	
			}
			if(obj[6]!=null){
				driverObj.setLon((double) obj[6]);	
			}
			driverInfoRetList.add(driverObj);
		}
		return driverInfoRetList; 
	}
}
