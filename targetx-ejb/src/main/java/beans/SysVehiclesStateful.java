package beans;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class SysVehiclesStateful
 */
@Stateful
@LocalBean
public class SysVehiclesStateful {

	@EJB 
	DatabaseBean addGeopoint;
	
    public SysVehiclesStateful() {
 
    }

}
