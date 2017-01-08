package beans;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.beanutils.PropertyUtilsBean;

import model.Route;
import model.Driver;
import DTO.DriverDTO;
import DTO.RouteFromEntityDTO;
import DTO.WorkTimeDTO;

@Stateless
@LocalBean
public class ConverterEntityToDTO {

	public ConverterEntityToDTO() {

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
	
}
