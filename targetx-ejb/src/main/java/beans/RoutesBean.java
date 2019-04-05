package beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import model.Driver;
import model.Geopoint;
import model.Route;
import model.Vehicle;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import DTO.RouteDTO;
import DTO.RouteFromEntityDTO;
import DTO.WorkTimeDTO;

/**
 * Session Bean implementation class RoutesBean
 */
@Stateless
@LocalBean
public class RoutesBean {

	@PersistenceContext(unitName = "CarMonitoring")
	EntityManager entityManager;

	@EJB
	EntityConverterBean entityConverter;

	public List<RouteDTO> getRouteByDayAndDriver(Date date, String search) {
		List<Route> routeList = new ArrayList<Route>();
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		// RouteDTO routeDTO = new RouteDTO();
		Map<Driver, List<RouteFromEntityDTO>> routeMap = new HashMap<Driver, List<RouteFromEntityDTO>>();
		Query query = entityManager
				.createNamedQuery(Route.GET_ROUTE_BY_DAY_AND_DRIVER);
		query.setParameter("IDSystem", new Long(2));
		query.setParameter("dateStart", new Timestamp(date.getTime()),
				TemporalType.DATE);
		query.setParameter("LName", '%' + search + '%');
		routeList = query.getResultList();
		List<RouteDTO> routeListRetDTO = new ArrayList<RouteDTO>();
		// for (int i = 0; i < routeList.size(); i++) {
		// Route rout = routeList.get(i);
		// Driver driver = rout.getDriver();
		// routeDTO.setRoute(rout);
		// routeDTO.setDriver(driver);
		// routeListDTO.add(routeDTO);
		// }
		// return routeListDTO;

		for (int i = 0; i < routeList.size(); i++) {
			RouteDTO routeDTO = new RouteDTO();
			Route route = routeList.get(i);
			Driver driver = route.getDriver();
			routeDTO.setDriver(driver);
			RouteFromEntityDTO routeFromEntity = entityConverter
					.convertRouteEntityToDTO(route);
			routeDTO.setRoute(routeFromEntity);
			routeListDTO.add(routeDTO);
		}

		for (int i = 0; i < routeListDTO.size(); i++) {
			RouteDTO routeDTO = new RouteDTO();
			RouteFromEntityDTO route = routeListDTO.get(i).getRoute();
			Driver driver = route.getDriver();
			routeDTO.setDriver(driver);
			routeDTO.setRoute(route);
			if (routeMap.containsKey(driver)) {
				List<RouteFromEntityDTO> routeListMap = routeMap.get(driver);
				routeListMap.add(route);
				routeMap.put(driver, routeListMap);
			} else {
				List<RouteFromEntityDTO> routeListMap = new ArrayList<RouteFromEntityDTO>();
				routeListMap.add(route);
				routeMap.put(driver, routeListMap);
			}
		}

		for (Driver driver : routeMap.keySet()) {
			RouteDTO ret = new RouteDTO();
			ret.setDriver(driver);
			List<RouteFromEntityDTO> routesList = routeMap.get(driver);
			ret.setRouteList(routesList);
			routeListRetDTO.add(ret);
		}

		return routeListRetDTO;
	}

	public List<RouteDTO> getRouteByMonthAndDriver(Date date, String search) {
		List<Route> routeList = new ArrayList<Route>();
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		// RouteDTO routeDTO = new RouteDTO();
		Map<Driver, List<RouteFromEntityDTO>> routeMap = new HashMap<Driver, List<RouteFromEntityDTO>>();
		Query query = entityManager
				.createNamedQuery(Route.GET_ROUTE_BY_MONTH_AND_DRIVER);
		query.setParameter("IDSystem", new Long(2));
		query.setParameter("dateStart", new Timestamp(date.getTime()),
				TemporalType.DATE);
		query.setParameter("LName", '%' + search + '%');
		routeList = query.getResultList();
		List<RouteDTO> routeListRetDTO = new ArrayList<RouteDTO>();
		// for (int i = 0; i < routeList.size(); i++) {
		// Route rout = routeList.get(i);
		// Driver driver = rout.getDriver();
		// routeDTO.setRoute(rout);
		// routeDTO.setDriver(driver);
		// routeListDTO.add(routeDTO);
		// }
		// return routeListDTO;

		for (int i = 0; i < routeList.size(); i++) {
			RouteDTO routeDTO = new RouteDTO();
			Route route = routeList.get(i);
			Driver driver = route.getDriver();
			routeDTO.setDriver(driver);
			RouteFromEntityDTO routeFromEntity = entityConverter
					.convertRouteEntityToDTO(route);
			routeDTO.setRoute(routeFromEntity);
			routeListDTO.add(routeDTO);
		}

		for (int i = 0; i < routeListDTO.size(); i++) {
			RouteDTO routeDTO = new RouteDTO();
			RouteFromEntityDTO route = routeListDTO.get(i).getRoute();
			Driver driver = route.getDriver();
			routeDTO.setDriver(driver);
			routeDTO.setRoute(route);
			if (routeMap.containsKey(driver)) {
				List<RouteFromEntityDTO> routeListMap = routeMap.get(driver);
				routeListMap.add(route);
				routeMap.put(driver, routeListMap);
			} else {
				List<RouteFromEntityDTO> routeListMap = new ArrayList<RouteFromEntityDTO>();
				routeListMap.add(route);
				routeMap.put(driver, routeListMap);
			}
		}

		for (Driver driver : routeMap.keySet()) {
			RouteDTO ret = new RouteDTO();
			ret.setDriver(driver);
			List<RouteFromEntityDTO> routesList = routeMap.get(driver);
			ret.setRouteList(routesList);
			routeListRetDTO.add(ret);
		}

		return routeListRetDTO;
	}

	@SuppressWarnings("unchecked")
	public List<WorkTimeDTO> getWorkTimeByDayRangeAndDriver(Date dateStart,
			Date dateEnd, String name) {
		List<WorkTimeDTO> workTimeDTOList = new ArrayList<WorkTimeDTO>();
		List<WorkTimeDTO> workTimeDTORetList = new ArrayList<WorkTimeDTO>();
		Map<Driver, WorkTimeDTO> retMap = new HashMap<Driver, WorkTimeDTO>();
		Query query = entityManager
				.createNamedQuery(Route.GET_WORKTIME_BY_DAY_RANGE_AND_NAME);

		if (dateStart != null && dateEnd != null) {
			query.setParameter("IDSystem", Long.valueOf(2));
			query.setParameter("dateStart", new Date(dateStart.getTime()),
					TemporalType.DATE);
			query.setParameter("dateEnd", new Date(dateEnd.getTime()),
					TemporalType.DATE);
			query.setParameter("LName", name.trim());
			List<Object[]> workTimeObjList = query.getResultList();
			for (Object obj[] : workTimeObjList) {
				WorkTimeDTO workTimeDTO = new WorkTimeDTO();
				Route route = (Route) obj[0];
				RouteFromEntityDTO routeFromEntity = entityConverter
						.convertRouteEntityToDTO(route);
				Driver driver = route.getDriver();
				Date datestart = (Date) obj[1];
				Date dateend = (Date) obj[2];
				Double fuelSum = (Double) obj[3];
				Double distanceSum = (Double) obj[4];
				workTimeDTO.setRoute(routeFromEntity);
				workTimeDTO.setDriver(driver);
				workTimeDTO.setDateStart(datestart);
				workTimeDTO.setDateEnd(dateend);
				workTimeDTO.setDistanceSum(distanceSum);
				// workTimeDTO.setFuelEnd(fuelSum);
				workTimeDTOList.add(workTimeDTO);
				if (retMap.containsKey(driver)) {
					WorkTimeDTO newDTO = retMap.get(driver);
					newDTO.setDistanceSum(distanceSum + newDTO.getDistanceSum());
					// newDTO.setFuelEnd(fuelSum+newDTO.getFuelEnd());
					retMap.put(driver, newDTO);
				} else {
					retMap.put(driver, workTimeDTO);
				}

			}
		}
		workTimeDTORetList = new ArrayList<WorkTimeDTO>(retMap.values());
		return workTimeDTORetList;
	}

	/*
	 * Filtrowanie trasy po dacie i numerze rejestracyjnym
	 */
	@SuppressWarnings("unchecked")
	public List<RouteDTO> getRouteByDayAndRegNum(Date date, String search) {
		List<Route> routeList = new ArrayList<Route>();
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		List<RouteDTO> routeListRetDTO = new ArrayList<RouteDTO>();
		Query query = entityManager.createNamedQuery(Route.GET_ROUTE_BY_DAY_AND_VEHICLE_REG);
		query.setParameter("IDSystem", new Long(2));
		query.setParameter("dateStart", new Timestamp(date.getTime()), TemporalType.DATE);
		query.setParameter("regNumber", search.trim());
		routeList = query.getResultList();
		for (int i = 0; i < routeList.size(); i++) {
			RouteDTO routeDTO = new RouteDTO();
			Route rout = routeList.get(i);
			Driver driver = rout.getDriver();
			RouteFromEntityDTO routeFromEntity = entityConverter.convertRouteEntityToDTO(rout);
			routeDTO.setRoute(routeFromEntity);
			routeDTO.setDriver(driver);
			routeListDTO.add(routeDTO);
		}

		Map<Driver, List<RouteFromEntityDTO>> routeMap = routeListDTOToRouteMap(routeListDTO);
		for (Driver driver : routeMap.keySet()) {
			RouteDTO ret = new RouteDTO();
			ret.setDriver(driver);
			List<RouteFromEntityDTO> routesList = routeMap.get(driver);
			ret.setRouteList(routesList);
			routeListRetDTO.add(ret);
		}
		return routeListRetDTO;
	}

	/*
	 * Filtrowanie trasy po dacie i numerze rejestracyjnym - pierwsza wersja
	 */
	@SuppressWarnings("unchecked")
	public List<RouteDTO> getRouteByDayAndRegNumBackup(Date date, String search) {
		List<Route> routeList = new ArrayList<Route>();
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		RouteDTO routeDTO = new RouteDTO();
		Query query = entityManager.createNamedQuery(Route.GET_ROUTE_BY_DAY_AND_VEHICLE_REG);
		query.setParameter("IDSystem", new Long(2));
		query.setParameter("dateStart", new Timestamp(date.getTime()), TemporalType.DATE);
		query.setParameter("regNumber", search.trim());
		routeList = query.getResultList();
		for (int i = 0; i < routeList.size(); i++) {
			Route rout = routeList.get(i);
			Driver driver = rout.getDriver();
			RouteFromEntityDTO routeFromEntity = entityConverter.convertRouteEntityToDTO(rout);
			routeDTO.setRoute(routeFromEntity);
			routeDTO.setDriver(driver);
			routeListDTO.add(routeDTO);
		}
		return routeListDTO;
	}

	public Map<Driver, List<RouteFromEntityDTO>> routeListDTOToRouteMap (
		List<RouteDTO> routeListDTO) {Map<Driver, List<RouteFromEntityDTO>> routeMap = new HashMap<Driver, List<RouteFromEntityDTO>>();
		for (int i = 0; i < routeListDTO.size(); i++) {
			RouteDTO routeDTO = new RouteDTO();
			RouteFromEntityDTO route = routeListDTO.get(i).getRoute();
			Driver driver = route.getDriver();
			routeDTO.setDriver(driver);
			routeDTO.setRoute(route);
			if (routeMap.containsKey(driver)) {
				List<RouteFromEntityDTO> routeListMap = routeMap.get(driver);
				routeListMap.add(route);
				routeMap.put(driver, routeListMap);
			} else {
				List<RouteFromEntityDTO> routeListMap = new ArrayList<RouteFromEntityDTO>();
				routeListMap.add(route);
				routeMap.put(driver, routeListMap);
			}
		}
		return routeMap;
	}

	@SuppressWarnings("unchecked")
	public List<RouteDTO> getRouteByDay(Date date) {
		List<Route> routeList = new ArrayList<Route>();
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		Query query = entityManager.createNamedQuery(Route.GET_ROUTE_BY_DAY);

		if (date != null) {
			query.setParameter("IDSystem", Long.valueOf(2));
			query.setParameter("dateStart", new Date(date.getTime()),TemporalType.DATE);
			routeList = query.getResultList();
			for (int i = 0; i < routeList.size(); i++) {
				RouteDTO routeDTO = new RouteDTO();
				Route route = routeList.get(i);
				Driver driver = route.getDriver();
				routeDTO.setDriver(driver);
				route.setVehicle(route.getVehicle());
				RouteFromEntityDTO routeFromEntiry = entityConverter.convertRouteEntityToDTO(route);
				routeDTO.setRoute(routeFromEntiry);
				routeListDTO.add(routeDTO);
			}
		}
		return routeListDTO;
	}

	/*
	 * Filtrowanie tras po miesiacu i roku
	 */
	@SuppressWarnings("unchecked")
	public List<RouteDTO> getRouteByMonthAndYear(Date date) {
		List<Route> routeList = new ArrayList<Route>();
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		Query query = entityManager.createNamedQuery(Route.GET_ROUTE_BY_MONTH_AND_YEAR);

		if (date != null) {
			query.setParameter("IDSystem", Long.valueOf(2));
			query.setParameter("dateStart", new Date(date.getTime()), TemporalType.DATE);
			routeList = query.getResultList();
			routeListDTO = routeConverter(routeList);
		}
		return routeListDTO;
	}

	/*
	 * Filtrowanie tras po miesiacu, roku, i nr rejestracji
	 */
	@SuppressWarnings("unchecked")
	public List<RouteDTO> getRouteByMonthAndYearAndRegNum(Date date, String reg) {
		List<Route> routeList = new ArrayList<Route>();
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		Query query = entityManager.createNamedQuery(Route.GET_ROUTE_BY_MONTH_AND_YEAR_AND_REG);

		if (date != null) {
			query.setParameter("IDSystem", Long.valueOf(2));
			query.setParameter("dateStart", new Date(date.getTime()), TemporalType.DATE);
			query.setParameter("regNumber", reg.trim());
			routeList = query.getResultList();
			routeListDTO = routeConverter(routeList);
		}
		return routeListDTO;
	}

	@SuppressWarnings("unchecked")
	public Map<Driver, RouteDTO> getRouteByDayAsMap(Date date) {
		List<Route> routeList = new ArrayList<Route>();
		Map<Driver, RouteDTO> routeMap = new HashMap<Driver, RouteDTO>();
		Query query = entityManager.createNamedQuery(Route.GET_ROUTE_BY_DAY);
		if (date != null) {
			query.setParameter("IDSystem", Long.valueOf(2));
			query.setParameter("dateStart", new Date(date.getTime()), TemporalType.DATE);
			routeList = query.getResultList();
			for (int i = 0; i < routeList.size(); i++) {
				RouteDTO routeDTO = new RouteDTO();
				Route route = routeList.get(i);
				Driver driver = route.getDriver();
				routeDTO.setDriver(driver);
				RouteFromEntityDTO routeFromEntity = entityConverter.convertRouteEntityToDTO(route);
				routeDTO.setRoute(routeFromEntity);
				routeMap.put(driver, routeDTO);
			}
		}
		return routeMap;
	}

	/*
	 * Filtrowanie tras po jednej dacie
	 */
	@SuppressWarnings("unchecked")
	public List<RouteDTO> getRouteByDayDetails(Date date) {
		List<Route> routeList = new ArrayList<Route>();
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		List<RouteDTO> routeListRetDTO = new ArrayList<RouteDTO>();
		Map<Driver, List<RouteFromEntityDTO>> routeMap = new HashMap<Driver, List<RouteFromEntityDTO>>();
		Query query = entityManager.createNamedQuery(Route.GET_ROUTE_BY_DAY);

		if (date != null) {
			query.setParameter("IDSystem", Long.valueOf(2));
			query.setParameter("dateStart", new Date(date.getTime()), TemporalType.DATE);
			routeList = query.getResultList();

			for (int i = 0; i < routeList.size(); i++) {
				RouteDTO routeDTO = new RouteDTO();
				Route route = routeList.get(i);
				Driver driver = route.getDriver();
				routeDTO.setDriver(driver);
				// Period period = new Period(new
				// DateTime(routeDTO.getRoute().getDateStart()), new
				// DateTime(routeDTO.getRoute().getDateEnd()));
				// System.out.println(PeriodFormat.getDefault().print(period));
				// routeDTO.setTimePeriodString(PeriodFormat.getDefault().print(period));
				RouteFromEntityDTO routeFromEntity = entityConverter.convertRouteEntityToDTO(route);
				routeDTO.setRoute(routeFromEntity);
				routeListDTO.add(routeDTO);
			}

			for (int i = 0; i < routeListDTO.size(); i++) {
				RouteDTO routeDTO = new RouteDTO();
				RouteFromEntityDTO route = routeListDTO.get(i).getRoute();
				Driver driver = route.getDriver();
				routeDTO.setDriver(driver);
				routeDTO.setRoute(route);
				if (routeMap.containsKey(driver)) {
					List<RouteFromEntityDTO> routeListMap = routeMap.get(driver);
					routeListMap.add(route);
					routeMap.put(driver, routeListMap);
				} else {
					List<RouteFromEntityDTO> routeListMap = new ArrayList<RouteFromEntityDTO>();
					routeListMap.add(route);
					routeMap.put(driver, routeListMap);
				}
			}

			for (Driver driver : routeMap.keySet()) {
				RouteDTO ret = new RouteDTO();
				ret.setDriver(driver);
				List<RouteFromEntityDTO> routesList = routeMap.get(driver);
				ret.setRouteList(routesList);
				routeListRetDTO.add(ret);
			}
		}
		return routeListRetDTO;
	}

	@SuppressWarnings("unchecked")
	public List<RouteDTO> routeConverter(List<Route> routeList) {
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		List<RouteDTO> routeListRetDTO = new ArrayList<RouteDTO>();
		Map<Vehicle, List<RouteFromEntityDTO>> routeMap = new HashMap<Vehicle, List<RouteFromEntityDTO>>();

		PeriodFormatter daysHoursMinutes = new PeriodFormatterBuilder()
				.appendDays().appendSuffix(" day", "d").appendSeparator(" ")
				.appendHours().appendSuffix(" hour", "h").appendSeparator(" ")
				.appendMinutes().appendSuffix(" minute", "m")
				.appendSeparator(" ").appendSeconds()
				.appendSuffix(" second", "s").toFormatter();

		if (routeList != null) {
			for (int i = 0; i < routeList.size(); i++) {
				RouteDTO routeDTO = new RouteDTO();
				Route route = routeList.get(i);
				Driver driver = route.getDriver();
				routeDTO.setDriver(driver);
				RouteFromEntityDTO routeFromEntity = entityConverter.convertRouteEntityToDTO(route);
				routeDTO.setRoute(routeFromEntity);
				routeDTO.setVehicle(route.getVehicle());
				routeListDTO.add(routeDTO);
			}

			for (int i = 0; i < routeListDTO.size(); i++) {
				RouteFromEntityDTO route = routeListDTO.get(i).getRoute();
				Vehicle vehicle = route.getVehicle();
				if (route.getDateStart() != null && route.getDateEnd() != null) {
					Period period = new Period(new DateTime(route.getDateStart()), new DateTime(route.getDateEnd()));
					System.out.println(daysHoursMinutes.print(period));
					route.setTimePeriodString(daysHoursMinutes.print(period));
				}
				if (routeMap.containsKey(vehicle)) {
					List<RouteFromEntityDTO> routeListMap = routeMap.get(vehicle);
					routeListMap.add(route);
					routeMap.put(vehicle, routeListMap);
				} else {
					List<RouteFromEntityDTO> routeListMap = new ArrayList<RouteFromEntityDTO>();
					routeListMap.add(route);
					routeMap.put(vehicle, routeListMap);
				}
			}

			for (Vehicle vehicle : routeMap.keySet()) {
				RouteDTO ret = new RouteDTO();
				ret.setVehicle(vehicle);
				List<RouteFromEntityDTO> routesList = routeMap.get(vehicle);
				Double distance = new Double(0);
				for (RouteFromEntityDTO route : routesList) {
					if (route.getDateStart() != null && route.getDateEnd() != null) {
						ret.setDateStart(route.getDateStart());
						ret.setDateEnd(route.getDateEnd());
					}
					if (route.getDistanceSum() != null) {
						distance += route.getDistanceSum();
					}
				}
				ret.setDistanceSum(distance);
				ret.setRouteList(routesList);
				routeListRetDTO.add(ret);
			}
		}
		return routeListRetDTO;
	}

	/*
	 * Filtrowanie czasu pracy przez zakres dat Filtrowanie tras po zakresie dat
	 */
	@SuppressWarnings("unchecked")
	public List<RouteDTO> getRouteByDayRange(Date dateStart, Date dateEnd) {
		List<Route> routeList = new ArrayList<Route>();
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		List<RouteDTO> routeListRetDTO = new ArrayList<RouteDTO>();
		Map<Vehicle, List<RouteFromEntityDTO>> routeMap = new HashMap<Vehicle, List<RouteFromEntityDTO>>();
		Query query = entityManager.createNamedQuery(Route.GET_ROUTE_BY_DAY_RANGE);

		if (dateStart != null && dateEnd != null) {
			query.setParameter("IDSystem", Long.valueOf(2));
			query.setParameter("dateStart", new Date(dateStart.getTime()), TemporalType.DATE);
			query.setParameter("dateEnd", new Date(dateEnd.getTime()), TemporalType.DATE);
			routeList = query.getResultList();
			PeriodFormatter daysHoursMinutes = new PeriodFormatterBuilder()
					.appendDays().appendSuffix(" day", "d")
					.appendSeparator(" ").appendHours()
					.appendSuffix(" hour", "h").appendSeparator(" ")
					.appendMinutes().appendSuffix(" minute", "m")
					.appendSeparator(" ").appendSeconds()
					.appendSuffix(" second", "s").toFormatter();

			for (int i = 0; i < routeList.size(); i++) {
				RouteDTO routeDTO = new RouteDTO();
				Route route = routeList.get(i);
				Driver driver = route.getDriver();
				routeDTO.setDriver(driver);
				RouteFromEntityDTO routeFromEntity = entityConverter.convertRouteEntityToDTO(route);
				routeDTO.setRoute(routeFromEntity);
				routeDTO.setVehicle(route.getVehicle());
				routeListDTO.add(routeDTO);
			}

			for (int i = 0; i < routeListDTO.size(); i++) {
				RouteFromEntityDTO route = routeListDTO.get(i).getRoute();
				Vehicle vehicle = route.getVehicle();
				if (route.getDateStart() != null && route.getDateEnd() != null) {
					Period period = new Period(new DateTime(route.getDateStart()), new DateTime(route.getDateEnd()));
					// System.out.println(daysHoursMinutes.print(period));
					route.setTimePeriodString(daysHoursMinutes.print(period));
				}
				if (routeMap.containsKey(vehicle)) {
					List<RouteFromEntityDTO> routeListToMap = routeMap.get(vehicle);
					routeListToMap.add(route);
					routeMap.put(vehicle, routeListToMap);
				} else {
					List<RouteFromEntityDTO> routeListMap = new ArrayList<RouteFromEntityDTO>();
					routeListMap.add(route);
					routeMap.put(vehicle, routeListMap);
				}
			}

			for (Vehicle vehicle : routeMap.keySet()) {
				RouteDTO ret = new RouteDTO();
				ret.setVehicle(vehicle);
				List<RouteFromEntityDTO> routesList = routeMap.get(vehicle);
				Double distance = new Double(0);
				for (RouteFromEntityDTO route : routesList) {
					if (route.getDateStart() != null && route.getDateEnd() != null) {
						ret.setDateStart(route.getDateStart());
						ret.setDateEnd(route.getDateEnd());
					}
					if (route.getDistanceSum() != null) {
						distance += route.getDistanceSum();
					}
				}
				ret.setDistanceSum(distance);
				ret.setRouteList(routesList);
				routeListRetDTO.add(ret);
			}
		}
		return routeListRetDTO;
	}

	@SuppressWarnings("unchecked")
	public List<RouteDTO> getRouteByDayRangeAndRegNum(Date dateStart, Date dateEnd, String regNum) {
		List<Route> routeList = new ArrayList<Route>();
		List<RouteDTO> routeListDTO = new ArrayList<RouteDTO>();
		List<RouteDTO> routeListRetDTO = new ArrayList<RouteDTO>();
		Map<Driver, List<RouteFromEntityDTO>> routeMap = new HashMap<Driver, List<RouteFromEntityDTO>>();
		Query query = entityManager.createNamedQuery(Route.GET_ROUTE_BY_DAY_RANGE_AND_REG);

		if (dateStart != null && dateEnd != null) {
			query.setParameter("IDSystem", Long.valueOf(2));
			query.setParameter("dateStart", new Date(dateStart.getTime()), TemporalType.DATE);
			query.setParameter("dateEnd", new Date(dateEnd.getTime()), TemporalType.DATE);
			query.setParameter("regNumber", regNum.trim());
			routeList = query.getResultList();

			for (int i = 0; i < routeList.size(); i++) {
				RouteDTO routeDTO = new RouteDTO();
				Route route = routeList.get(i);
				RouteFromEntityDTO routeFromEntityDTO = entityConverter.convertRouteEntityToDTO(route);
				Driver driver = route.getDriver();
				routeDTO.setDriver(driver);
				routeDTO.setRoute(routeFromEntityDTO);
				routeListDTO.add(routeDTO);
			}

			for (int i = 0; i < routeListDTO.size(); i++) {
				RouteDTO routeDTO = new RouteDTO();
				RouteFromEntityDTO route = routeListDTO.get(i).getRoute();
				Driver driver = route.getDriver();
				routeDTO.setDriver(driver);
				routeDTO.setRoute(route);
				if (routeMap.containsKey(driver)) {
					List<RouteFromEntityDTO> routeListMap = routeMap.get(driver);
					routeListMap.add(route);
					routeMap.put(driver, routeListMap);
				} else {
					List<RouteFromEntityDTO> routeListMap = new ArrayList<RouteFromEntityDTO>();
					routeListMap.add(route);
					routeMap.put(driver, routeListMap);
				}
			}

			for (Driver driver : routeMap.keySet()) {
				RouteDTO ret = new RouteDTO();
				ret.setDriver(driver);
				List<RouteFromEntityDTO> routesList = routeMap.get(driver);
				ret.setRouteList(routesList);
				routeListRetDTO.add(ret);
			}
		}
		return routeListRetDTO;
	}

	public List<WorkTimeDTO> getWorkTime(Date start, Date end, boolean detail) {
		List<WorkTimeDTO> workTimeDTOList = new ArrayList<WorkTimeDTO>();
		List<WorkTimeDTO> workTimeDTORetList = new ArrayList<WorkTimeDTO>();
		Map<Vehicle, WorkTimeDTO> retMap = new HashMap<Vehicle, WorkTimeDTO>();
		if (start != null && end == null) {
			Query query = entityManager.createNamedQuery(Route.GET_ROUTE_BY_DAY_SUMMARY);
			query.setParameter("IDSystem", Long.valueOf(2));
			query.setParameter("date", new Date(start.getTime()), TemporalType.DATE);
			List<Object[]> workTimeObjList = query.getResultList();
			for (Object obj[] : workTimeObjList) {
				WorkTimeDTO workTimeDTO = new WorkTimeDTO();
				Route route = (Route) obj[0];
				RouteFromEntityDTO routeFromEntity = entityConverter.convertRouteEntityToDTO(route);
				Driver driver = route.getDriver();
				Date dateStart = (Date) obj[1];
				Date dateEnd = (Date) obj[2];
				Double fuelSum = (Double) obj[3];
				Double distanceSum = (Double) obj[4];
				workTimeDTO.setRoute(routeFromEntity);
				workTimeDTO.setDriver(driver);
				workTimeDTO.setDateStart(dateStart);
				workTimeDTO.setDateEnd(dateEnd);
				workTimeDTO.setDistanceSum(distanceSum);
				workTimeDTO.setFuelEnd(fuelSum);
				Vehicle vehicle = route.getVehicle();
				workTimeDTO.setVehicle(vehicle);
				workTimeDTOList.add(workTimeDTO);
				if (retMap.containsKey(vehicle)) {
					WorkTimeDTO newDTO = retMap.get(vehicle);
					newDTO.setDistanceSum(distanceSum + newDTO.getDistanceSum());
					newDTO.setFuelEnd(fuelSum + newDTO.getFuelEnd());
					retMap.put(vehicle, newDTO);
				} else {
					retMap.put(vehicle, workTimeDTO);
				}
				/*
				 * for(int i=0;i<workTimeDTOList.size();i++){ WorkTimeDTO
				 * timeDTO = workTimeDTOList.get(i); for(int
				 * p=0;p<workTimeDTOList.size();p++){
				 * if(workTimeDTOList.get(i).getDriver() == driver &&
				 * workTimeDTOList
				 * .get(i).getRoute().getIDRoutes()!=route.getIDRoutes()){
				 * WorkTimeDTO newDTO = new WorkTimeDTO(); newDTO = workTimeDTO;
				 * newDTO.setDistanceSum(distanceSum+timeDTO.getDistanceSum());
				 * newDTO.setFuelEnd(fuelSum+timeDTO.getFuelEnd());
				 * workTimeDTORetList.add(i, newDTO); }else{
				 * workTimeDTORetList.add(timeDTO); } }
				 */
			}
		}
		workTimeDTORetList = new ArrayList<WorkTimeDTO>(retMap.values());
		return workTimeDTORetList;
	}

	public List<Geopoint> getGeopointsForRoute(long idRoute) {
		Query query = entityManager.createNamedQuery(Geopoint.GET_GEOPOINTS_BY_ROUTE);
		query.setParameter("IDSystem", Long.valueOf(2));
		query.setParameter("IDRoutes", idRoute);
		List<Geopoint> geopointList = query.getResultList();
		return geopointList;
	}
}
