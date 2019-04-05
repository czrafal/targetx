package beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Driver;

/**
 * Session Bean implementation class NewDriverBean
 */
@Stateless
public class NewDriverBean {

	@PersistenceContext(unitName="CarMonitoring")
	private EntityManager entityManager;

    public NewDriverBean() {

    }
    public void addNewDriver(Driver newDriver){
    	newDriver.setIDSystem(new Long(2));
    	System.out.println("IDSystem:"+newDriver.getIDSystem());
    	
    	System.out.println("Imie:"+newDriver.getFName());
    	System.out.println("Nazwisko:"+newDriver.getLName());
    	entityManager.persist(newDriver);
    }
}
