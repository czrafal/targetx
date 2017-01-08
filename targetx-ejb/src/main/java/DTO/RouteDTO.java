package DTO;

import java.util.Date;
import java.util.List;

import model.Driver;
import model.Route;

public class RouteDTO extends VehicleDTO{
	
	private Driver driver;
	private RouteFromEntityDTO route;
	private Double distanceSum;
	private Integer routeTimeDays;
	private Integer routeTimeHours;
	private Integer routeTimeMinutes;
	private Integer routeDays;
	private Integer routeHours;
	private Integer routeMinutes;
	private List<RouteFromEntityDTO> routeList;
	private Date dateStart;
	private Date dateEnd;
	private String timePeriodString;
	
	public RouteDTO() {
	}
	
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public RouteFromEntityDTO getRoute() {
		return route;
	}
	public void setRoute(RouteFromEntityDTO route) {
		this.route = route;
	}
	public Double getDistanceSum() {
		return distanceSum;
	}
	public void setDistanceSum(Double distanceSum) {
		this.distanceSum = distanceSum;
	}
	public Integer getRouteTimeDays() {
		return routeTimeDays;
	}

	public void setRouteTimeDays(Integer routeTime) {
		this.routeTimeDays = routeTime;
	}

	public Integer getRouteTimeHours() {
		return routeTimeHours;
	}

	public void setRouteTimeHours(Integer routeTimeHours) {
		this.routeTimeHours = routeTimeHours;
	}

	public Integer getRouteTimeMinutes() {
		return routeTimeMinutes;
	}

	public void setRouteTimeMinutes(Integer routeTimeMinutes) {
		this.routeTimeMinutes = routeTimeMinutes;
	}

	public Integer getRouteDays() {
		return routeDays;
	}

	public void setRouteDays(Integer routeDays) {
		this.routeDays = routeDays;
	}

	public Integer getRouteHours() {
		return routeHours;
	}

	public void setRouteHours(Integer routeHours) {
		this.routeHours = routeHours;
	}

	public Integer getRouteMinutes() {
		return routeMinutes;
	}

	public void setRouteMinutes(Integer routeMinutes) {
		this.routeMinutes = routeMinutes;
	}

	public List<RouteFromEntityDTO> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<RouteFromEntityDTO> routeList) {
		this.routeList = routeList;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getTimePeriodString() {
		return timePeriodString;
	}

	public void setTimePeriodString(String timePeriodString) {
		this.timePeriodString = timePeriodString;
	}
	
}
