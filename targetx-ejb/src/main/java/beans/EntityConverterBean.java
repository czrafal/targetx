package beans;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import model.Driver;
import model.Geopoint;
import model.Route;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.traccar.model.Position;

import DTO.DriverDTO;
import DTO.RouteFromEntityDTO;
import DTO.WorkTimeDTO;

@Stateless
@LocalBean
public class EntityConverterBean {

	public EntityConverterBean() {

	}

	/*
	 * Convert working time of driver from entity to dto
	 */
	public List<WorkTimeDTO> convertWorkingTimeToDTO(List<Object[]> driverList) {
		List<WorkTimeDTO> workTimeList = new ArrayList<WorkTimeDTO>();
		for (Object obj[] : driverList) {
			WorkTimeDTO dto = new WorkTimeDTO();
			Route route = (Route) obj[0];
			Driver driver = (Driver) obj[1];
			dto.setDriver(driver);
			RouteFromEntityDTO routeDto = convertRouteEntityToDTO(route);
			dto.setRoute(routeDto);
			workTimeList.add(dto);
		}
		return workTimeList;
	}
	
	public RouteFromEntityDTO convertRouteEntityToDTO(Route route){
		RouteFromEntityDTO routeEntityDTO = new RouteFromEntityDTO();
		PropertyUtilsBean prop = new PropertyUtilsBean();
		try {
			prop.copyProperties(routeEntityDTO, route);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		return routeEntityDTO;
	}
	
	public DriverDTO convertDriverEntityToDTO(Driver driver){
		DriverDTO driverDTO = new DriverDTO();
		PropertyUtilsBean prop = new PropertyUtilsBean();
		try {
			prop.copyProperties(driverDTO, driver);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		return driverDTO;
	}
	
	public Geopoint convertPositionToGeopointEntity(Position position){
		Geopoint geopoint = new Geopoint();
		geopoint.setLat(position.getLatitude());
		geopoint.setLon(position.getLongitude());
		Double maxSpeed = position.getSpeed();
		Timestamp timestamp = new Timestamp(position.getDeviceTime().getTime());
		geopoint.setMaxspeed(maxSpeed.intValue());
		geopoint.setDateTime(timestamp);
		geopoint.setIDSystem(2L);
		return geopoint;
	}
}
