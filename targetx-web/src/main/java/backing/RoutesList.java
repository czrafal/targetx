package backing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import model.Driver;
import model.Geopoint;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import utils.MonthsEnum;
import DTO.RouteDTO;
import DTO.RouteFromEntityDTO;
import beans.RoutesBean;

import com.gisfaces.model.graphic.GraphicsModel;
import com.gisfaces.model.graphic.MarkerGraphic;
import com.gisfaces.model.graphic.PolylineGraphic;
import com.gisfaces.model.map.Coordinate;
import enums.DateFilterEnum;
import enums.TypeFilterEnum;

@ManagedBean(name = "routesList")
@ViewScoped
public class RoutesList implements Serializable {

	private static final long serialVersionUID = -7297484590290692485L;

	@EJB
	private RoutesBean routesBean;

	private List<RouteDTO> routesList;
	private Map<Driver, RouteDTO> routesListMap;
	private Date date;
	private Date dateFrom;
	private Date dateTo;
	private String searchDate;
	private String dateOption;
	private String typeOption;
	private RouteFromEntityDTO selectedRoute;
	private String background = "streets";
	private double latitude;
	private double longitude;
	private double routeLatitude = 0.0;
	private double routeLongitude = 0.0;
	private int zoom = 4;
	private double opacity = 1.0;
	private String where = "MAGNITUDE >= 2";
	private GraphicsModel geoipGraphicsModel;
	private boolean detail;
	private Long selectedOrderId;
	private List<Geopoint> geopointList;
	private List<Geopoint> selectedGeopoint;
	private MonthsEnum selectedMonth;
	private String selectedYear;
	private List<SelectItem> monthsList = getMonths();
	private List<String> yearsList = getYears();

	@ManagedProperty(value = "#{chartView}")
	private ChartView chartView;

	@PostConstruct
	public void init() {
		date = new java.util.Date();
		dateOption = DateFilterEnum.DATE.name();
		latitude = 53.972753;
		longitude = 17.281934;
		routesList = routesBean.getRouteByDay(date);
	}

	public void setMapData() {
		generateRouteMapBySource();
	}

	public void searchAction() {
		DateFilterEnum dateFilterEnum = DateFilterEnum.valueOf(dateOption);
		TypeFilterEnum typeFilterEnum = TypeFilterEnum.valueOf(typeOption);
		
		System.out.print("Akcja wyszukaj");
		if (typeFilterEnum.equals(TypeFilterEnum.DRIVER) && !dateFilterEnum.equals(DateFilterEnum.MONTH)) {
			System.out.println("Szukam nazwiska:" + searchDate + " dnia:"+ date.toString());
			routesList = routesBean.getRouteByDayAndDriver(date, searchDate);
		} else if (typeFilterEnum.equals(TypeFilterEnum.VEHICLE) && dateFilterEnum.equals(DateFilterEnum.DATERANGE)) {
			System.out.println("Szukam pojazdu:" + searchDate + " dnia:"+ date.toString());
			routesList = routesBean.getRouteByDayRangeAndRegNum(dateFrom, dateTo, searchDate);
		} else if (typeFilterEnum.equals(TypeFilterEnum.VEHICLE) && !dateFilterEnum.equals(DateFilterEnum.MONTH)) {
			System.out.println("Szukam pojazdu:" + searchDate + " dnia:"+ date.toString());
			routesList = routesBean.getRouteByDayAndRegNum(date, searchDate);
		} else if (dateFilterEnum.equals(DateFilterEnum.DATE)) {
			//routesList = routesBean.getRouteByDayDetails(date);
			routesList = routesBean.getRouteByDay(date);
		} else if (dateOption.equals(dateFilterEnum.equals(DateFilterEnum.DATERANGE))) {
			routesList = routesBean.getRouteByDayRange(dateFrom, dateTo);
		} else if (typeFilterEnum.equals(TypeFilterEnum.ALLROUTE)) {
			routesList = routesBean.getRouteByDay(date);
			routesBean.getRouteByDayAsMap(date);
		} else if (dateFilterEnum.equals(DateFilterEnum.MONTH)) {
			DateFormat sd = new SimpleDateFormat("MM-yyyy");
			int month = MonthsEnum.get(selectedMonth);
			date = new Date();
			try {
				date = sd.parse(month + "-" + selectedYear);
				if (typeFilterEnum.equals(TypeFilterEnum.VEHICLE)) {
					routesList = routesBean.getRouteByMonthAndYearAndRegNum(date, searchDate);
				} else if (typeFilterEnum.equals(TypeFilterEnum.DRIVER)) {
					routesList = routesBean.getRouteByMonthAndDriver(date, searchDate);
				} else {
					routesList = routesBean.getRouteByMonthAndYear(date);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	private void generateRouteMapBySource() {
		PolylineGraphic polyline = new PolylineGraphic();
		geopointList = new ArrayList<Geopoint>();
		Long idRoute = null;
		geoipGraphicsModel = new GraphicsModel();

		if (getSelectedOrderId() != null) {
			idRoute = getSelectedOrderId();
			geopointList = routesBean.getGeopointsForRoute(idRoute);
			if (!geopointList.isEmpty()) {
				setLatitude(geopointList.get(0).getLat());
				setLongitude(geopointList.get(0).getLon());
				MarkerGraphic startMarker = addStartRouteMarker(geopointList
						.get(0).getLat(), geopointList.get(0).getLon());
				MarkerGraphic stopMarker = addStopRouteMarker(
						geopointList.get(geopointList.size() - 1).getLat(),
						geopointList.get(geopointList.size() - 1).getLon());
				this.geoipGraphicsModel.getGraphics().add(startMarker);
				this.geoipGraphicsModel.getGraphics().add(stopMarker);
				for (Geopoint g : geopointList) {
					polyline.getCoordinates().add(
							new Coordinate(g.getLat(), g.getLon()));
				}
				polyline.setStyle(com.gisfaces.model.graphic.LineStyle.SOLID);
				polyline.setColor("#0000FF");
				polyline.setOpacity(0.5);
				polyline.setWidth(6);
				polyline.getAttributes().put("Location",
						"The Main Street Bridge");
				polyline.getAttributes().put("Polyline Setting", "Value");
				polyline.getAttributes().put("setColor()", polyline.getColor());
				polyline.getAttributes().put("setOpacity()",
						polyline.getOpacity());
				polyline.getAttributes().put("setWidth()", polyline.getWidth());
				this.geoipGraphicsModel.getGraphics().add(polyline);
				this.geoipGraphicsModel.setName("Nazwa mapy");
				this.routeLatitude = geopointList.get(0).getLat();
				this.routeLongitude = geopointList.get(0).getLon();
				this.getGeoipGraphicsModel().setRefresh(true);
			}
		}
	}

	private MarkerGraphic addStartRouteMarker(double lat, double lon) {
		MarkerGraphic marker = new MarkerGraphic();
		marker.setCoordinate(new Coordinate(lat, lon));
		marker.setImage("http://maps.google.com/mapfiles/ms/icons/green.png");
		marker.setHeight(32);
		marker.setWidth(32);
		marker.setDraggable(true);
		return marker;
	}

	private MarkerGraphic addStopRouteMarker(double d, double e) {
		MarkerGraphic marker = new MarkerGraphic();
		marker.setCoordinate(new Coordinate(d, e));
		marker.setImage("http://maps.google.com/mapfiles/ms/icons/red.png");
		marker.setHeight(32);
		marker.setWidth(32);
		marker.setDraggable(true);
		return marker;
	}

	public void makeGeopointsInsert() {
		try (BufferedReader br = new BufferedReader(new FileReader(
				"D:\\geopoints.txt"))) {
			// PrintWriter pw = new PrintWriter(new FileWriter("d:\\out.txt"));
			String line;
			int idGeop = 168;
			while ((line = br.readLine()) != null) {
				String[] strArr = line.split(",");
				double lon = Double.valueOf(strArr[0]);
				double lat = Double.valueOf(strArr[1]);
				// System.out.println("INSERT INTO public.geopoints(idgeopoints, idsystem, iddriver, idroutes, lat, lon, gas, maxspeed, datetime, idvehicle, distance)VALUES ("+idGeop+", 2, 10, 15, "+lat+", "+lon+", 35, 54, '2016-04-25', 5, 30);");
				String insert = "INSERT INTO public.geopoints(idgeopoints, idsystem, iddriver, idroutes, lat, lon, gas, maxspeed, datetime, idvehicle, distance)VALUES ("
						+ idGeop
						+ ", 2, 10, 15, "
						+ lat
						+ ", "
						+ lon
						+ ", 35, 54, '2016-04-25', 5, 30);";
				// pw.write(insert);
				idGeop++;
			}
			// pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getYears() {
		int yearStart = 2013;
		int yearEnd = DateTime.now().year().get();
		List<String> yearList = new ArrayList<String>();
		for (int i = yearStart; yearStart < yearEnd; i++) {
			yearList.add(String.valueOf(i));
			yearStart++;
		}
		return yearList;
	}

	public static List<SelectItem> getMonths() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (MonthsEnum month : MonthsEnum.values()) {
			items.add(new SelectItem(month, MonthsEnum.getText(month)));
		}
		return items;
	}

	public void prepareViewFromPayroll() {
		System.out.println("zaznaczone:" + selectedOrderId);
		boolean exit = false;
		for (RouteDTO route : routesList) {
			List<RouteFromEntityDTO> routeList = route.getRouteList();
			for (RouteFromEntityDTO searchedRoute : routeList) {
				if (searchedRoute.getIDRoutes() == selectedOrderId) {
					selectedRoute = searchedRoute;
					exit = true;
					break;
				}
			}
			if (exit) {
				setMapData();
				break;
			}
		}
		// RequestContext context = RequestContext.getCurrentInstance();
		// context.execute("PF('workTimeDialogVar').show();");
	}

	public GraphicsModel getGeoipGraphicsModel() {
		return geoipGraphicsModel;
	}

	public void setGeoipGraphicsModel(GraphicsModel geoipGraphicsModel) {
		this.geoipGraphicsModel = geoipGraphicsModel;
	}

	public void rowSelected(SelectEvent event) {
		selectedRoute = (RouteFromEntityDTO) event.getObject();
		System.out.println("zaznaczone:" + selectedRoute.getIDRoutes());
	}

	public void rowUnselect(UnselectEvent event) {
		selectedRoute = (RouteFromEntityDTO) event.getObject();
		System.out.println("zaznaczone:" + selectedRoute.getIDRoutes());
	}

	public void prepareViewFromPayrollAction() {
		System.out.println("prepareViewFromPayrollAction");
	}

	public void calculateTotal(Object valueOfThisSorting) {
		Double total = new Double(0);
		Integer totalHour = new Integer(0);
		Integer totalMinute = new Integer(0);
		Integer totalDay = new Integer(0);
		DateTime out = new DateTime();
		for (RouteDTO r : getRoutesList()) {
			if (r.getDriver().getLName().equals(valueOfThisSorting)) {
				total += ((RouteDTO) r).getRoute().getDistanceSum();
				r.setDistanceSum(total);
				DateTime startTime = new DateTime(((RouteDTO) r).getRoute()
						.getDateStart());
				DateTime endTime = new DateTime(((RouteDTO) r).getRoute()
						.getDateEnd());
				Period p = new Period(startTime, endTime);
				totalDay += p.getDays();
				r.setRouteDays(totalDay);
				totalHour += p.getHours();
				r.setRouteHours(totalHour);
				totalMinute += p.getMinutes();
				r.setRouteMinutes(totalMinute);
				calculateHours(r.getRouteMinutes());

				// dodane na szybko - sprawdzic
				out.plusHours(totalHour);
				out.plusMinutes(totalMinute);
			}
		}
	}

	public String convertTimeWithTimeZone(Timestamp time) {
		long longTime = time.getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.setTimeInMillis(longTime);
		return (cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
	}

	private String calculateHours(Integer minutes) {
		String hours = String.format("%d Minutes: %d:%02d Hours", minutes,
				(minutes / 60), (minutes % 60));
		System.out.println(String.format("%d Minutes: %d:%02d Hours", minutes,
				(minutes / 60), (minutes % 60)));
		return "";
	}

	public ChartView getChartView() {
		return chartView;
	}

	public void setChartView(ChartView chartView) {
		this.chartView = chartView;
	}

	public String getDateOption() {
		return dateOption;
	}

	public void setDateOption(String option) {
		this.dateOption = option;
	}

	public String getTypeOption() {
		return typeOption;
	}

	public void setTypeOption(String typeOption) {
		this.typeOption = typeOption;
	}

	public String getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(String searchData) {
		this.searchDate = searchData;
	}

	public List<RouteDTO> getRoutesList() {
		return routesList;
	}

	public RouteFromEntityDTO getSelectedRoute() {
		if (selectedRoute != null) {
			System.out.println("Zaaznaczona sciezka:"
					+ selectedRoute.getIDRoutes());
		}
		return selectedRoute;
	}

	public void setSelectedRoute(RouteFromEntityDTO selectedRoute) {
		if (selectedRoute != null) {
			System.out.println("Zaaznaczona sciezkaddddddd:"
					+ selectedRoute.getIDRoutes());
		}
		this.selectedRoute = selectedRoute;
	}

	public void setRoutesList(List<RouteDTO> routesList) {
		this.routesList = routesList;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date data) {
		this.date = data;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public Map<Driver, RouteDTO> getRoutesListMap() {
		return routesListMap;
	}

	public void setRoutesListMap(Map<Driver, RouteDTO> routesListMap) {
		this.routesListMap = routesListMap;
	}

	public boolean isDetail() {
		return detail;
	}

	public void setDetail(boolean detail) {
		this.detail = detail;
	}

	public Long getSelectedOrderId() {
		return selectedOrderId;
	}

	public void setSelectedOrderId(Long selectedOrderId) {
		this.selectedOrderId = selectedOrderId;
	}

	public List<Geopoint> getGeopointList() {
		return geopointList;
	}

	public void setGeopointList(List<Geopoint> geopointList) {
		this.geopointList = geopointList;
	}

	public List<Geopoint> getSelectedGeopoint() {
		return selectedGeopoint;
	}

	public void setSelectedGeopoint(List<Geopoint> selectedGeopoint) {
		this.selectedGeopoint = selectedGeopoint;
	}

	public MonthsEnum getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(MonthsEnum selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public List<SelectItem> getMonthsList() {
		return monthsList;
	}

	public void setMonthsList(List<SelectItem> monthsList) {
		this.monthsList = monthsList;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public List<String> getYearsList() {
		return yearsList;
	}

	public void setYearsList(List<String> yearsList) {
		this.yearsList = yearsList;
	}

	public double getRouteLatitude() {
		return routeLatitude;
	}

	public void setRouteLatitude(double routeLatitude) {
		this.routeLatitude = routeLatitude;
	}

	public double getRouteLongitude() {
		return routeLongitude;
	}

	public void setRouteLongitude(double routeLongitude) {
		this.routeLongitude = routeLongitude;
	}

}
