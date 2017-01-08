package backing;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import pojos.DriverInfo;

import com.gisfaces.model.Coordinate;
import com.gisfaces.model.Graphic;
import com.gisfaces.model.GraphicsModel;
import com.gisfaces.model.MarkerGraphic;
 
@ManagedBean(name = "mapBean")
@SessionScoped
public class MapBean implements Serializable {

	private static final long serialVersionUID = 7319229460184237373L;
 
	private GraphicsModel realGraphicsModel;

	@ManagedProperty(value="#{vehicleListReal}")
	private VehicleListReal vehicleList;
	
	public MapBean(){
		vehicleList = new VehicleListReal();
		this.buildRealGraphicsModel();
	}
	
	private void buildRealGraphicsModel()
	{
		List<DriverInfo> vehicleGeopointList = vehicleList.getVehicleList();
		this.realGraphicsModel = new GraphicsModel();
		this.realGraphicsModel.setName("Pojazdy");
		
		List<Graphic> graphics = this.realGraphicsModel.getGraphics();
		for(DriverInfo geopoint:vehicleGeopointList){
			if(geopoint.getLat()!=0 && geopoint.getLon()!=0){
				graphics.add(builGraphicsModel(geopoint.getLat(), geopoint.getLon(), geopoint.getLName()));	
			}
		}
	}

	private MarkerGraphic builGraphicsModel(double latitude, double longitude, String address)
	{
		MarkerGraphic marker = new MarkerGraphic();
		marker.setCoordinate(new Coordinate(latitude, longitude));
		marker.getAttributes().put("Address", address);
		marker.setImage("https://sites.google.com/site/kmlroutes01/ciezarowka.png");
		marker.setHeight(64);
		marker.setWidth(64);
		marker.setDraggable(true);
		return marker;
	}
	
	public GraphicsModel getRealGraphicsModel() {
		return realGraphicsModel;
	}

	public void setRealGraphicsModel(GraphicsModel realGraphicsModel) {
		this.realGraphicsModel = realGraphicsModel;
	}

	public VehicleListReal getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(VehicleListReal vehicleList) {
		this.vehicleList = vehicleList;
	}
	
}