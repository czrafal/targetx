package beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.GpsDevice;

@Stateless
@LocalBean
public class LocatorBean {
	
	@PersistenceContext(unitName="CarMonitoring")
	private EntityManager entityManager;

    public void addNewDevice(GpsDevice newDevice){
    	entityManager.persist(newDevice);
    }
    
    @SuppressWarnings("unchecked")
    public List<GpsDevice> allDevicesGet(){
    	Query zapytanie = entityManager.createNamedQuery(model.GpsDevice.FIND_ALL_DEVICES);
		List<GpsDevice> vehicles = zapytanie.getResultList();
    	return vehicles;
    }
    
    public void updateDevice(GpsDevice device){
		entityManager.merge(device);
		System.out.println("Update device was request:"+device.getIDDevice());
		entityManager.flush();
	}
    
    public void removeDevice(GpsDevice id){
		GpsDevice deviceToRemove = entityManager.find(GpsDevice.class, id.getIDDevice());
		entityManager.remove(deviceToRemove);
		System.out.println("Device deleted:"+deviceToRemove.getImei());
	}
}
