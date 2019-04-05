package beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Vehicle;

/**
 * Session Bean implementation class NewVehicleBean
 */
@Stateless
@LocalBean
public class NewVehicleBean {

	@PersistenceContext(unitName="CarMonitoring")
	private EntityManager entityManager;

    public void addNewVehilce(Vehicle newVehicle){
    	newVehicle.setIDSystem(new Long(2));
    	entityManager.persist(newVehicle);
    }
    
    @SuppressWarnings("unchecked")
    public List<Vehicle> allVehiclesGet(){
    	Query zapytanie = entityManager.createNamedQuery(model.Vehicle.FIND_ALL_VEHICLES);
    	//zapytanie.setParameter("IDSystem", 2);
		List<Vehicle> vehicles = zapytanie.getResultList();
    	return vehicles;
    }
    
    public void updateVehicle(Vehicle vehicle){
		entityManager.merge(vehicle);
		System.out.println("Wykonalem update kierowcy nowy pojazd id:"+vehicle.getIDVehicle());
		entityManager.flush();
	}
    
    public void removeVehicle(Vehicle id){
		Vehicle vehicleToRemove = entityManager.find(Vehicle.class, id.getIDVehicle());
		entityManager.remove(vehicleToRemove);
		System.out.println("usunalem kierowce:"+vehicleToRemove.getBrand()+" o ID:"+vehicleToRemove.getIDVehicle());
	}
}
