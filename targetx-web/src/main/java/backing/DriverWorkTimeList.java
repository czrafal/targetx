package backing;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import model.Driver;
import model.Route;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

import utils.MonthsEnum;
import DTO.RouteDTO;
import DTO.WorkTimeDTO;
import beans.RoutesBean;

import com.gisfaces.model.Coordinate;
import com.gisfaces.model.GraphicsModel;
import com.gisfaces.model.MarkerGraphic;

@ManagedBean(name="driverWorkTimeList")
@ViewScoped
public class DriverWorkTimeList implements Serializable{

	private static final long serialVersionUID = -597055070687333005L;

	@EJB
	RoutesBean routesBean;
	
	private List<RouteDTO> routesList;
	private List<WorkTimeDTO> workTimeDTOList;
	private Map<Driver,RouteDTO> routesListMap;
	private Date date;
	private Date dateFrom;
	private Date dateTo;
	private String searchDate;
	private String dateOption;
	private String typeOption;
	private Route selectedRoute; 
	private MapModel routesModel;
	private String background;
	private double latitude;
	private double longitude;
	private int zoom;
	private double opacity;
	private String where;
	private GraphicsModel geoipGraphicsModel;
	private boolean detail;
	private MonthsEnum selectedMonth; 
	private String selectedYear;
	
	@ManagedProperty(value="#{chartView}")
	private ChartView chartView;
	
	@PostConstruct
	public void init(){
		date = new java.util.Date();
		dateOption = "A";
		dateOption = "A"; //data
		this.background = "streets";
		this.latitude = 53.972753;
		this.longitude = 17.281934;
		this.zoom = 4;
		this.opacity = 1.0;
		this.where = "MAGNITUDE >= 2";
		routesList = routesBean.getRouteByDay(date);
		routesModel = new DefaultMapModel();  
		setMapData();
	}
	
	public void setMapData()
	{
		// Create a new marker.
		MarkerGraphic marker = new MarkerGraphic();
		marker.setCoordinate(new Coordinate(latitude, longitude));
		geoipGraphicsModel = new GraphicsModel();
		// Add the new marker to the graphics model.
		this.getGeoipGraphicsModel().getGraphics().add(marker);
		// Refresh the graphics model.
		this.getGeoipGraphicsModel().setRefresh(true);
		this.zoom = 4;
	}
	
	public GraphicsModel getGeoipGraphicsModel()
	{
		return geoipGraphicsModel;
	}
	
	public void setGeoipGraphicsModel(GraphicsModel geoipGraphicsModel) {
		this.geoipGraphicsModel = geoipGraphicsModel;
	}

	public void rowSelected(SelectEvent event) {
	    selectedRoute = (Route) event.getObject();
	    System.out.println("zaznaczone:"+selectedRoute.getIDRoutes());
	}
	
	public void rowUnselect(UnselectEvent event) {  
		selectedRoute = (Route) event.getObject();
	    System.out.println("zaznaczone:"+selectedRoute.getIDRoutes());
	}
	
	public void searchAction(){
		System.out.print("Akcja wyszukaj");
		if(typeOption.equals("D") && dateOption.equals("B")){
			workTimeDTOList = routesBean.getWorkTimeByDayRangeAndDriver(dateFrom, dateTo, searchDate);
		}else if(typeOption.equals("D")){
			System.out.println("Szukam nazwiska:"+searchDate+" dnia:"+date.toString());
			routesList = routesBean.getRouteByDayAndDriver(date, searchDate);
		}else if(typeOption.equals("V")){
			System.out.println("Szukam pojazdu:"+searchDate+" dnia:"+date.toString());
		}else if(dateOption.equals("A") && detail == false){//data
			workTimeDTOList = routesBean.getWorkTime(date, dateTo, detail);
		}else if(dateOption.equals("A") && detail == true){
			routesList = routesBean.getRouteByDayDetails(date);
		}else if(dateOption.equals("B")){ //zakres dat
			routesList = routesBean.getRouteByDayRange(dateFrom, dateTo);
		}else if(typeOption.equals("A")){//data
			routesList = routesBean.getRouteByDay(date);
			routesBean.getRouteByDayAsMap(date);
		}else if(dateOption.equals("M")){
			DateFormat sd = new SimpleDateFormat("MM-yyyy");
			int month = MonthsEnum.get(selectedMonth);
			date = new Date();
			try {
				date = sd.parse(month+"-"+selectedYear);
				routesList = routesBean.getRouteByMonthAndYear(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	 public void calculateTotal(Object valueOfThisSorting) {
	      Double total = new Double(0);
	      Integer totalHour = new Integer(0);
	      Integer totalMinute = new Integer(0);
	      Integer totalDay = new Integer(0);
	      DateTime out = new DateTime();
	      for (RouteDTO r : getRoutesList()) {
	         if (r.getDriver().getLName().equals(valueOfThisSorting)) {
	            total +=((RouteDTO) r).getRoute().getDistanceSum();
	            r.setDistanceSum(total);
	            DateTime startTime = new DateTime(((RouteDTO) r).getRoute().getDateStart());
	            DateTime endTime = new DateTime(((RouteDTO) r).getRoute().getDateEnd());
	            Period p = new Period(startTime, endTime);
	            totalDay += p.getDays();
	            r.setRouteDays(totalDay);
	            totalHour += p.getHours();
	            r.setRouteHours(totalHour);
	            totalMinute += p.getMinutes();
	            r.setRouteMinutes(totalMinute);
	            calculateHours(r.getRouteMinutes());
	            
	            //dodane na szybko - sprawdzic
	  	      out.plusHours(totalHour);
		      out.plusMinutes(totalMinute);
	         }
	      }
	   }
	 
	 public String convertTimeWithTimeZone(Timestamp time){
		 	long longTime = time.getTime();
		    Calendar cal = Calendar.getInstance();
		    cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		    cal.setTimeInMillis(longTime);
		    return ( cal.get(Calendar.HOUR_OF_DAY) + ":"+ cal.get(Calendar.MINUTE));
		}
	 
	 private String calculateHours(Integer minutes){
		 String hours = String.format("%d Minutes: %d:%02d Hours", minutes, (minutes/60), (minutes%60));
		 System.out.println(
				    String.format("%d Minutes: %d:%02d Hours", minutes, (minutes/60), (minutes%60)));
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

	public Route getSelectedRoute() {
		if(selectedRoute!=null){
			System.out.println("Zaaznaczona sciezka:"+selectedRoute.getIDRoutes());	
		}	
		return selectedRoute;
	}

	public void setSelectedRoute(Route selectedRoute) {
		if(selectedRoute!=null){
			System.out.println("Zaaznaczona sciezkaddddddd:"+selectedRoute.getIDRoutes());		
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

	public MapModel getRoutesModel() {
		return routesModel;
	}

	public void setRoutesModel(MapModel routesModel) {
		this.routesModel = routesModel;
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

	public List<WorkTimeDTO> getWorkTimeDTOList() {
		return workTimeDTOList;
	}

	public void setWorkTimeDTOList(List<WorkTimeDTO> workTimeDTOList) {
		this.workTimeDTOList = workTimeDTOList;
	}
	  public int getRandomPrice() {
	        return (int) (Math.random() * 100000);
	    }

	public MonthsEnum getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(MonthsEnum selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}
}
