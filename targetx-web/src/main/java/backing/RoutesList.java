package backing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.persistence.TemporalType;

import model.Driver;
import model.Geopoint;
import model.Route;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

import DTO.RouteDTO;
import DTO.RouteFromEntityDTO;
import beans.RoutesBean;

import com.gisfaces.model.Coordinate;
import com.gisfaces.model.GraphicsModel;
import com.gisfaces.model.MarkerGraphic;
import com.gisfaces.model.PolylineGraphic;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Point;
import utils.MonthsEnum;
@ManagedBean(name="routesList")
@ViewScoped
public class RoutesList implements Serializable{

	private static final long serialVersionUID = -7297484590290692485L;

	@EJB
	private RoutesBean routesBean;
	
	private List<RouteDTO> routesList;
	private Map<Driver,RouteDTO> routesListMap;
	private Date date;
	private Date dateFrom;
	private Date dateTo;
	private String searchDate;
	private String dateOption;
	private String typeOption;
	private RouteFromEntityDTO selectedRoute; 
	private String background;
	private double latitude;
	private double longitude;
	private int zoom;
	private double opacity;
	private String where;
	private GraphicsModel geoipGraphicsModel;
	private boolean detail;
	private Long selectedOrderId;
	private List<Geopoint> geopointList;
	private List<Geopoint> selectedGeopoint;
	private MonthsEnum selectedMonth; 
	private String selectedYear;
	private List monthsList = getMonths();
	private List<String> yearsList = getYears();
	
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
	}
	
	public void setMapData()
	{
		generateRouteMapBySource();
	}
	
	public void searchAction(){
		System.out.print("Akcja wyszukaj");
		if(typeOption.equals("D") && !dateOption.equals("M")){
			System.out.println("Szukam nazwiska:"+searchDate+" dnia:"+date.toString());
			routesList = routesBean.getRouteByDayAndDriver(date, searchDate);
		}else if(typeOption.equals("V") && dateOption.equals("B")){
			System.out.println("Szukam pojazdu:"+searchDate+" dnia:"+date.toString());
			routesList = routesBean.getRouteByDayRangeAndRegNum(dateFrom,dateTo, searchDate);
		}else if(typeOption.equals("V") && !dateOption.equals("M")){
			System.out.println("Szukam pojazdu:"+searchDate+" dnia:"+date.toString());
			routesList = routesBean.getRouteByDayAndRegNum(date, searchDate);
		}else if(dateOption.equals("A")){//data
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
			 if(typeOption.equals("V")){ // filtrowanie tras po miesiacu i pojezdzie
	            routesList = routesBean.getRouteByMonthAndYearAndRegNum(date, searchDate);
			 }else if(typeOption.equals("D")){
				 routesList = routesBean.getRouteByMonthAndDriver(date, searchDate);
			 }else{
				routesList = routesBean.getRouteByMonthAndYear(date);
			 }
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void generateRouteMapBySource(){
		
		PolylineGraphic polyline = new PolylineGraphic();
		geopointList = new ArrayList<Geopoint>();
		Long idRoute = null;
//		makeGeopointsInsert();
		geoipGraphicsModel = new GraphicsModel();
		if(getSelectedOrderId()!=null){
			idRoute = getSelectedOrderId();
		
		geopointList = routesBean.getGeopointsForRoute(idRoute);
		
		if(geopointList.size() > 0 && geopointList != null){
			MarkerGraphic startMarker = addStartRouteMarker(geopointList.get(0).getLat(), geopointList.get(0).getLon());
			MarkerGraphic stopMarker  = addStopRouteMarker(geopointList.get(geopointList.size() - 1).getLat(), geopointList.get(geopointList.size() - 1).getLon());
			this.geoipGraphicsModel.getGraphics().add(startMarker);
			this.geoipGraphicsModel.getGraphics().add(stopMarker);
		}
		for(Geopoint g:geopointList){
			polyline.getCoordinates().add(new Coordinate(g.getLat(), g.getLon()));	 
		}
		polyline.setStyle(com.gisfaces.model.LineStyle.SOLID);
		polyline.setColor("#0000FF");
		polyline.setOpacity(0.5);
		polyline.setWidth(5);
		polyline.getAttributes().put("Location", "The Main Street Bridge");
		polyline.getAttributes().put("Polyline Setting", "Value");
		polyline.getAttributes().put("setColor()", polyline.getColor());
		polyline.getAttributes().put("setOpacity()", polyline.getOpacity());
		polyline.getAttributes().put("setWidth()", polyline.getWidth());
		this.geoipGraphicsModel.getGraphics().add(polyline);
		this.geoipGraphicsModel.setName("Nazwa mapy");
		this.getGeoipGraphicsModel().setRefresh(true);
		}
	}
	
	private MarkerGraphic addStartRouteMarker(double lat, double lon){
		MarkerGraphic marker = new MarkerGraphic();
		marker.setCoordinate(new Coordinate(lat, lon));
		marker.setImage("http://maps.google.com/mapfiles/ms/icons/green.png");
		marker.setHeight(32);
		marker.setWidth(32);
		marker.setDraggable(true);
		return marker;
	}
	
	private MarkerGraphic addStopRouteMarker(double d, double e){
		MarkerGraphic marker = new MarkerGraphic();
		marker.setCoordinate(new Coordinate(d, e));
		marker.setImage("http://maps.google.com/mapfiles/ms/icons/red.png");
		marker.setHeight(32);
		marker.setWidth(32);
		marker.setDraggable(true);
		return marker;
	}
	
	public void makeGeopointsInsert(){
		try (
			BufferedReader br = new BufferedReader(new FileReader("D:\\geopoints.txt"))) {
//			PrintWriter pw = new PrintWriter(new FileWriter("d:\\out.txt"));
		    String line;
		    int idGeop = 168;
		    while ((line = br.readLine()) != null) {
		      String[] strArr =  line.split(",");
		      double lon = Double.valueOf(strArr[0]);
		      double lat = Double.valueOf(strArr[1]);
//		      System.out.println("INSERT INTO public.geopoints(idgeopoints, idsystem, iddriver, idroutes, lat, lon, gas, maxspeed, datetime, idvehicle, distance)VALUES ("+idGeop+", 2, 10, 15, "+lat+", "+lon+", 35, 54, '2016-04-25', 5, 30);");
		      String insert = "INSERT INTO public.geopoints(idgeopoints, idsystem, iddriver, idroutes, lat, lon, gas, maxspeed, datetime, idvehicle, distance)VALUES ("+idGeop+", 2, 10, 15, "+lat+", "+lon+", 35, 54, '2016-04-25', 5, 30);";
//		      pw.write(insert);
		      idGeop++;
		    }
//		    pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	

	public List<String> getYears(){
		int yearStart = 2013;
		int yearEnd = DateTime.now().year().get();
		List <String> yearList = new ArrayList<String>();
		for(int i = yearStart;yearStart<yearEnd;i++){
			yearList.add(String.valueOf(i));
			yearStart++;
		}
		return yearList;
	}
	
	public static List getMonths() {
		List items = new ArrayList();
		for (MonthsEnum month : MonthsEnum.values()) {
			items.add(new SelectItem(month, MonthsEnum.getText(month)));
		}
		return items;
	}
	
	public void prepareViewFromPayroll() {  
	    System.out.println("zaznaczone:"+selectedOrderId);
	    boolean exit = false;
	    for(RouteDTO route:routesList){
	    	List<RouteFromEntityDTO> routeList = route.getRouteList();
	    	for(RouteFromEntityDTO r:routeList){
	    		if(r.getIDRoutes() == selectedOrderId){
	    			selectedRoute = r;
	    			exit = true;
	    			break;
		    	}	
	    	}
	    	if(exit){
	    		setMapData();
	    		break;
	    	}
	    }
//	    RequestContext context = RequestContext.getCurrentInstance();
//	    context.execute("PF('workTimeDialogVar').show();");
	}
	
	public GraphicsModel getGeoipGraphicsModel()
	{
		return geoipGraphicsModel;
	}
	
	public void setGeoipGraphicsModel(GraphicsModel geoipGraphicsModel) {
		this.geoipGraphicsModel = geoipGraphicsModel;
	}

	public void rowSelected(SelectEvent event) {
	    selectedRoute = (RouteFromEntityDTO) event.getObject();
	    System.out.println("zaznaczone:"+selectedRoute.getIDRoutes());
	}
	
	public void rowUnselect(UnselectEvent event) {  
		selectedRoute = (RouteFromEntityDTO) event.getObject();
	    System.out.println("zaznaczone:"+selectedRoute.getIDRoutes());
	}

	public void prepareViewFromPayrollAction() { 
		System.out.println("prepareViewFromPayrollAction");
	}
	
	
	public List<String> fromEnum(String cname) {
        List<String> names = new ArrayList<>();
        try {
            Class c = Class.forName(cname);
            Object[] r = c.getEnumConstants();
            if (r != null) {
                for (Object o : r) {
                    names.add(o.toString());
                }
            }
        } catch (ClassNotFoundException ex) {
        	ex.printStackTrace();
        }
        return names;
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
	 
	 public void generateRouteMap(){
		 Kml kml = KmlFactory.createKml();
		 Document doc = kml.createAndSetDocument().withName("MyMarkers");
		 
		 doc.createAndAddStyle()
		 .withId("startIconStyle")
		 .withIconStyle(new IconStyle().withIcon(new Icon().withHref("http://www.gpsies.com/images/car.png")));
		 
//		 LineStyle lineStyle = new LineStyle();
//		 lineStyle.setColor("blue");
//		 lineStyle.setWidth(5);
		 
//		 Style startIconStyle = new Style();
//		 Icon startIcon = new Icon();
//		 startIcon.setHref("http://www.gpsies.com/images/car.png");
//		 startIconStyle.createAndSetIconStyle().setIcon(startIcon);
		 
		 List<Geopoint> geopointList = new ArrayList<Geopoint>();
		 long idRoute = 2;
		 geopointList = routesBean.getGeopointsForRoute(idRoute);
		 if(!geopointList.isEmpty()){
			Geopoint startPoint = geopointList.get(0);
			doc.createAndAddPlacemark()
			 .withName("Start trasy")
	         .withDescription("Dane poczatkowe")
	         .withStyleUrl("#startIconStyle")
	         .createAndSetPoint().addToCoordinates(startPoint.getLat(), startPoint.getLon());
	         
			 if(geopointList.size()>1){
				 Geopoint endPoint = geopointList.get(geopointList.size() - 1);
				 doc.createAndAddPlacemark()
				 .withName("Koniec trasy")
		         .withDescription("Dane koncowe")
		         .withStyleUrl("#startIconStyle")
		         .createAndSetPoint().addToCoordinates(endPoint.getLat(), endPoint.getLon());
			 }
		 }

		 Point routePoint = KmlFactory.createPoint();
		 for(Geopoint point:geopointList){
			routePoint.getCoordinates().add(new de.micromata.opengis.kml.v_2_2_0.Coordinate(point.getLat(), point.getLon()));
		 }
		 doc.createAndAddPlacemark().createAndSetMultiGeometry().createAndAddLineString().setCoordinates(routePoint.getCoordinates());
		 
		 try {
			kml.marshal(System.out);
			File kmlFile = new File("d:\\advancedexample2.kml");
			kml.marshal(kmlFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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

	public RouteFromEntityDTO getSelectedRoute() {
		if(selectedRoute!=null){
			System.out.println("Zaaznaczona sciezka:"+selectedRoute.getIDRoutes());	
		}	
		return selectedRoute;
	}
	public void setSelectedRoute(RouteFromEntityDTO selectedRoute) {
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

	public List getMonthsList() {
		return monthsList;
	}

	public void setMonthsList(List monthsList) {
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
	
}
