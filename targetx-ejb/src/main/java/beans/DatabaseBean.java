package beans;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.traccar.model.Device;
import org.traccar.model.Position;
import org.traccar.model.VehicleStatusEnum;

import pojos.DriverInfo;
import model.Geopoint;
import model.Route;

/**
 * Session Bean implementation class AddGeopoint
 */
@Stateless(mappedName="addGeopoint")
@LocalBean
public class DatabaseBean {

	@EJB
	CacheDriversSingleton driversCache;
	@EJB
	private EntityConverterBean entitityConverterBean;
	
	@PersistenceContext(unitName="CarMonitoring")
	private EntityManager entityManager;
	
	public DatabaseBean(){
		
	}
	
    public void savePosition(Position position) {
    	try{
	    	System.out.println("addNewGeopoint!");
	//    	Device device = driversCache.deviceSessionByImei(String.valueOf(position.getDeviceId()));
	    	long deviceId = 352848022867325l;
	    	if(position == null){
	    		System.out.println("addNewGeopoint position is null");
	    	}
	        Context ctxgt = new InitialContext();	
	        driversCache = (CacheDriversSingleton) ctxgt.lookup("java:global/targetx-ear-1.0/targetx-web-1.0/CacheDriversSingleton");
	    	System.out.println("before get driver");
	    	DriverInfo driverInfo = driversCache.driverInfoByDeviceImei(position.getDeviceId());
	    	System.out.println("addNewGeopoint1!");
	    	driverInfo.setPosition(position);
	    	Geopoint geopoint = entitityConverterBean.convertPositionToGeopointEntity(position);
	    	System.out.println("addNewGeopoint2!");
	    	geopoint.setIDDriver(driverInfo.getIDDriver());
	    	System.out.println("addNewGeopoint3!");
	    	geopoint.setIDVehicle(driverInfo.getVehicle().getIDVehicle());
	    	System.out.println("addNewGeopoint4!");
	    	driverInfo.setGeopoint(geopoint);
	    	Route route = checkRouteCreateOrUpdate(driverInfo);
	    	System.out.println("addNewGeopoint5!");
	    	geopoint.setRoute(route);
	    	geopoint.setIDRoutes(route.getIDRoutes());
	    	entityManager.persist(geopoint);
	    	System.out.println("addNewGeopoint6!");
    	}catch(Exception e){
    		System.out.println("addNewGeopoint! manage error:"+e.getMessage());
    	}
    }
    
    private Route checkRouteCreateOrUpdate(DriverInfo driverInfo){
    	System.out.println("before get vehicleStatus");
    	VehicleStatusEnum vehicleStatus = driverInfo.getPosition().getVehicleStatus();
    	System.out.println("after get vehicle status "+vehicleStatus.name());
    	
    	if (driverInfo.getRoute() == null && vehicleStatus == VehicleStatusEnum.IGNITION_ON) {
    		Route newRoute = createRoute(driverInfo);
    		entityManager.persist(newRoute);
    		System.out.println("Create new route id "+ newRoute.getIDRoutes()+ " for device "+driverInfo.getDevice().getId());
    		driverInfo.getGeopoint().setIDRoutes(newRoute.getIDRoutes());
    		driverInfo.setRoute(newRoute);
    		driversCache.updateVehicleCash(driverInfo);
    		return newRoute;
    	} else if (driverInfo.getRoute().getDateStart() != null && driverInfo.getRoute().getDateEnd() == null) {
    		System.out.println("Update route id "+ driverInfo.getRoute().getIDRoutes()+ " for device "+driverInfo.getDevice().getId());
    		if (vehicleStatus == VehicleStatusEnum.IGNITION_OFF) {
    			Route endedRoute = endRoute(driverInfo);
    			driverInfo.setRoute(endedRoute);
    		}
    		driversCache.updateVehicleCash(driverInfo);
    		return driverInfo.getRoute();
    	}else if (driverInfo.getRoute().getDateStart() != null && driverInfo.getRoute().getDateEnd() != null) {
    		if (vehicleStatus == VehicleStatusEnum.IGNITION_OFF) {
    			Route newRoute = createRoute(driverInfo);
        		driverInfo.getGeopoint().setIDRoutes(newRoute.getIDRoutes());
        		entityManager.persist(newRoute);
        		System.out.println("Create new route id "+ newRoute.getIDRoutes()+ " for device "+driverInfo.getDevice().getId());
        		driverInfo.setRoute(newRoute);
        		driverInfo.getGeopoint().setIDRoutes(newRoute.getIDRoutes());
        		driversCache.updateVehicleCash(driverInfo);
        		return newRoute;
    		}
    	}
    	return driverInfo.getRoute();
    }
    
    private Route createRoute(DriverInfo driverInfo){
    	System.out.println("createRoute");
    	Route newRoute = new Route();
    	long millis = DateUtils.truncate(driverInfo.getPosition().getDeviceTime(), Calendar.MILLISECOND).getTime();
		Timestamp dateAstimestamp = new java.sql.Timestamp(millis);
		newRoute.setDateStart(dateAstimestamp);
		newRoute.setLat(driverInfo.getPosition().getLatitude());
		newRoute.setLon(driverInfo.getPosition().getLongitude());
		newRoute.setIDVehicle(driverInfo.getVehicle().getIDVehicle());
		newRoute.setIDDriver(driverInfo.getRoute().getIDDriver());
		return newRoute;
    }
    
    private Route endRoute(DriverInfo driver) {
    	Route route = driver.getRoute();
    	long millis = DateUtils.truncate(driver.getPosition().getDeviceTime(), Calendar.MILLISECOND).getTime();
		Timestamp dateAstimestamp = new java.sql.Timestamp(millis);
    	route.setDateEnd(dateAstimestamp);
    	Route endedRoute = entityManager.merge(route);
    	return endedRoute;
    }
    
    public void savePositions(List<Position> positions) {
		
	}
    
}
