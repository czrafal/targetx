package backing;

import java.awt.Image;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.primefaces.event.SelectEvent;

import pojos.DriverInfo;
import beans.CacheDriversSingleton;

import com.gisfaces.event.MapClickEvent;
import com.gisfaces.event.MapExtentEvent;
import com.gisfaces.model.graphic.Graphic;
import com.gisfaces.model.graphic.GraphicsModel;
import com.gisfaces.model.graphic.MarkerGraphic;
import com.gisfaces.model.map.Coordinate;
import com.gisfaces.utilities.JSFUtilities;
 
@ManagedBean(name = "mapBean")
@ViewScoped
public class MapBean implements Serializable {

	private static final long serialVersionUID = 7319229460184237373L;
 
	@EJB
	CacheDriversSingleton activeDriversList;
	
	@ManagedProperty(value="#{realList}")
	VehicleListReal vehicleRealList;
	
	private GraphicsModel realGraphicsModel;
	private double latitude;
	private double longitude;
	private double zoom = 8;
	private DriverInfo selectedCar;
	
	public MapBean(){
		this.longitude = 17.27800000;
		this.latitude = 53.9645528;
	}
	
	@PostConstruct
	public void init(){
		this.buildRealGraphicsModel();
	}
	
	public void buildRealGraphicsModel()
	{
		List<DriverInfo> vehicleGeopointList = activeDriversList.getDriversCache(new Long(2));
		this.realGraphicsModel = new GraphicsModel();
		this.realGraphicsModel.setName("Pojazdy");
		List<Graphic> graphics = this.realGraphicsModel.getGraphics();
		for(DriverInfo geopoint:vehicleGeopointList){
			if((geopoint.getGeopoint() != null) && (geopoint.getGeopoint().getLat() !=0 && geopoint.getGeopoint().getLon() != 0)){
				graphics.add(builGraphicsModel(geopoint.getGeopoint().getLat(), geopoint.getGeopoint().getLon(), geopoint.getVehicle().getRegNum()));	
			}
		}
		refreshGraphicsModel();
	}

	public void refreshGraphicsModel(){
		this.realGraphicsModel.setRefresh(true);
	}
	
	private MarkerGraphic builGraphicsModel(double latitude, double longitude, String address)
	{
		MarkerGraphic marker = new MarkerGraphic();
		marker.setCoordinate(new Coordinate(latitude, longitude));
		marker.getAttributes().put("Pojazd", address);
		Image image = new ImageIcon("/images/red_baloon.png").getImage();
		marker.setImage("/images/red_baloon.png");
		marker.setHeight(64);
		marker.setWidth(64);
		marker.setDraggable(false);
		return marker;
	}
	
	public GraphicsModel getRealGraphicsModel() {
		return realGraphicsModel;
	}

	public void geoIpActionListener(ActionEvent event)
	{
		System.out.println("Map bean geocode ip action listener fired.");
		double latitude1 = this.latitude;
		double longitude1 = this.longitude;
		this.latitude = latitude1;
		this.longitude = longitude1;
	}
	
	public void setRealGraphicsModel(GraphicsModel realGraphicsModel) {
		this.realGraphicsModel = realGraphicsModel;
	}
	
	public void doMapClickListener(AjaxBehaviorEvent event)
	{
		MapClickEvent e = (MapClickEvent) event;
		String summary = "Map Click Event";
		String detail = String.format("Latitude='%s', Longitude='%s', Zoom='%s', Scale='%s', XMin='%s', YMin='%s', XMax='%s', YMax='%s', Height='%s', Width='%s', X='%s', Y='%s'", e.getLatitude(), e.getLongitude(), e.getZoom(), e.getScale(), e.getExtent().getXmin(), e.getExtent().getYmin(), e.getExtent().getXmax(), e.getExtent().getYmax(), e.getScreen().getHeight(), e.getScreen().getWidth(), e.getScreen().getX(), e.getScreen().getY());
        buildRealGraphicsModel();
		System.out.println(String.format("%s: %s", summary, detail));
	}
	
	public void doMapClickListener2()
	{
        buildRealGraphicsModel();
	}
	
	public void onRowSelect(SelectEvent event) {
		DriverInfo driver = ((DriverInfo) event.getObject());
		BigInteger driverID = ((DriverInfo) event.getObject()).getIDDriver();
		String regNum = StringUtils.defaultString(driver.getVehicle().getRegNum(), "");
		if(driver.getGeopoint() != null && driver.getGeopoint().getLat() != 0){
			this.latitude = driver.getGeopoint().getLat();
		}
		if(driver.getGeopoint() != null && driver.getGeopoint().getLon() != 0){
			this.longitude = driver.getGeopoint().getLon();
		}
        FacesMessage msg = new FacesMessage("Car Selected", latitude +" "+longitude +" rej:"+regNum +" zoom:"+zoom +" iddriver:"+driverID);
        this.realGraphicsModel.setRefresh(true);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

	public void doMapExtentListener(AjaxBehaviorEvent event)
	{
		MapExtentEvent e = (MapExtentEvent) event;
		String summary = "Map Extent Update Event";
		String detail = String.format("Latitude='%s', Longitude='%s', Zoom='%s', Scale='%s', XMin='%s', YMin='%s', XMax='%s', YMax='%s', Height='%s', Width='%s'", e.getLatitude(), e.getLongitude(), e.getZoom(), e.getScale(), e.getExtent().getXmin(), e.getExtent().getYmin(), e.getExtent().getXmax(), e.getExtent().getYmax(), e.getScreen().getHeight(), e.getScreen().getWidth());
		this.zoom = e.getZoom();
		System.out.println(String.format("%s: %s", summary, detail));
		JSFUtilities.addInfoMessage(summary, detail);
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

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	public DriverInfo getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(DriverInfo selectedCar) {
		this.selectedCar = selectedCar;
	}

	public VehicleListReal getVehicleRealList() {
		return vehicleRealList;
	}

	public void setVehicleRealList(VehicleListReal vehicleRealList) {
		this.vehicleRealList = vehicleRealList;
	}
	
}