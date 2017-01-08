package beans;

import java.math.BigInteger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Geopoint;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

/**
 * Session Bean implementation class AddGeopoint
 */
@Stateless(mappedName="addGeopoint")
@LocalBean
public class AddGeopoint {

	@EJB
	CacheDriversSingleton driversCache;
	
	@PersistenceContext(unitName="CarMonitoring")
	private EntityManager entityManager;
	
	public AddGeopoint(){
		
	}
	
    public void addNewGeopoint(Geopoint newGeopoint) {
    	System.out.println("addNewGeopoint!");
    	
    	driversCache.checkCache(newGeopoint.getIDSystem(), BigInteger.valueOf(newGeopoint.getIDDriver()), newGeopoint.getDateTime(), new Double(23), newGeopoint.getGas(), newGeopoint.getMaxspeed());
    	entityManager.persist(newGeopoint);
        entityManager.flush();
        
        PushContext pushContext = PushContextFactory.getDefault().getPushContext();
        pushContext.push("/msg" ,1);
    }
}
