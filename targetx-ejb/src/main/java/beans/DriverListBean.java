package beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.joda.time.Period;

import DTO.RouteDTO;
import DTO.RouteFromEntityDTO;
import DTO.WorkTimeDTO;
import pojos.DriverAndVehicle;
import model.Driver;
import model.Vehicle;
import model.Route;

@Stateless
public class DriverListBean {
	
	@PersistenceContext(unitName="CarMonitoring")
	EntityManager entityManager;
	
	@EJB
	private ConverterEntityToDTO converter; 
	
	@SuppressWarnings("unchecked")
	public List<Driver> allDriverShow(String findAllDrivers) {
		return entityManager.createNamedQuery(findAllDrivers).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<DriverAndVehicle> allDriversAndVehiclesShow(){
		return entityManager.createQuery("Select NEW pojos.DriverAndVehicle(d.IDDriver, d.IDSystem, v.IDVehicle,  d.FName, d.LName, d.phone, v.model, v.brand, v.regNum) from Driver d, Vehicle v where v.IDVehicle = d.IDVehicle and d.IDSystem = 2 group by d.IDDriver, v.IDVehicle, v.brand, v.model").getResultList();
	}
	
	public void updateDriver(Driver driver){
		driver.setIDVehicle(driver.getVehicle().getIDVehicle());
		entityManager.merge(driver);
		entityManager.flush();
	}
	
	public Driver removeDriver(Long id){
		Driver driverToRemove = entityManager.find(Driver.class, id);
		entityManager.remove(driverToRemove);
		System.out.println("usun¹³em kierowcê:"+driverToRemove.getLName()+" o ID:"+driverToRemove.getIDDriver());
		return driverToRemove;
	}
	
	public DriverAndVehicle getVehicleByRegNum(Long idSystem, Long idVehicle){
		Query zapytanie = entityManager.createQuery("Select NEW pojos.DriverAndVehicle(d.IDDriver, d.IDSystem, v.IDVehicle,  d.FName, d.LName, d.phone, v.model, v.brand, v.regNum) from Driver d, Vehicle v where v.IDVehicle = d.IDVehicle and d.IDSystem = :IDSystem and v.IDVehicle = :idVehicle group by d.IDDriver, v.IDVehicle, v.brand, v.model");
		zapytanie.setParameter("IDSystem", idSystem);
		zapytanie.setParameter("idVehicle", idVehicle);
		zapytanie.setMaxResults(1);
		DriverAndVehicle listOut = (DriverAndVehicle) zapytanie.getSingleResult();
		return listOut;
	}
	
	public List<RouteDTO> allDriversWorkTimeByDateRange(Date from, Date to){
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	    java.util.Date parsedDateStart = from;
	    java.util.Date parsedDateEnd = to;
//		try {
//			parsedDateStart = dateFormat.parse(from.g);
//			parsedDateEnd = dateFormat.parse("2013-04-29 00:00:00");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		
		Query query = entityManager.createQuery("SELECT r, d FROM Route r, Driver d where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD')  >= :dateStart and to_date(to_char(r.dateEnd, 'YYYY-MON-DD'), 'YYYY-MON-DD')  <= :dateEnd and r.IDDriver = d.IDDriver");
		query.setParameter("IDSystem", new Long(2));
		query.setParameter("dateStart", new Date(parsedDateStart.getTime()), TemporalType.DATE);
		query.setParameter("dateEnd", new Date(parsedDateEnd.getTime()), TemporalType.DATE);
//		query.setParameter("IDDriver", new Long(3));
		
	    List<Object[]> driverList = query.getResultList();
	    List<WorkTimeDTO> workList = converter.convertWorkingTimeToDTO(driverList);
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		
	    for(Object obj[] : driverList){
	    	RouteDTO routeDTO = new RouteDTO();
	    	Route route = (Route)obj[0];
	    	Driver driver = (Driver) obj[1];
			RouteFromEntityDTO routeDto = converter.convertRouteEntityToDTO(route);
	    	routeDTO.setRoute(routeDto);
			routeDTO.setDriver(driver);
			DateTime startTime = new DateTime(route.getDateStart());
	        DateTime endTime = new DateTime(route.getDateEnd());
	        Period p = new Period(startTime, endTime);
	        int hours = p.getHours();
	        routeDTO.setRouteDays(p.getDays());
	        routeDTO.setRouteHours(hours);
	        routeDTO.setRouteMinutes(p.getMinutes());
			routeListDTO.add(routeDTO);
	    }
	    return routeListDTO;
	}
	
	public void allDriversWorkTimeByDate(Date date){
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	    java.util.Date parsedDate = date;
		try {
			parsedDate = dateFormat.parse("2013-04-29 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Query query = entityManager.createQuery("SELECT r, d FROM Route r, Driver d where r.IDSystem = :IDSystem and to_date(to_char(r.dateStart, 'YYYY-MON-DD'), 'YYYY-MON-DD')  = :dateStart and r.IDDriver = d.IDDriver");
		query.setParameter("IDSystem", new Long(2));
		query.setParameter("dateStart", new Date(parsedDate.getTime()), TemporalType.DATE);
//		query.setParameter("IDDriver", new Long(3));
		
	    List<Object[]> driverList = query.getResultList();
	    List<WorkTimeDTO> workList = converter.convertWorkingTimeToDTO(driverList);
	    
	    for(Object obj[] : driverList){
	    	Route route = (Route)obj[0];
	    	Driver driver = (Driver) obj[1];
	    	System.out.println(driver.getFName());
	    	System.out.println(driver.getLName());
	    	System.out.println(route.getDistanceSum());
	    	System.out.println(route.getDateStart());
	    	System.out.println(route.getDateEnd());
	    }
	}
	
	@SuppressWarnings("unchecked")
	public List<Vehicle> allVehiclesGet(){
		return entityManager.createQuery("SELECT v from Vehicle v where v.IDSystem =2").getResultList();
	}
	
	public void addNewDriver(Driver driver){
		driver.setIDSystem(new Long(2));
		entityManager.persist(driver);
	}
}
