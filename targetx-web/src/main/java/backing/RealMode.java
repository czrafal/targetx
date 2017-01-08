package backing;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;

@ManagedBean(name="realMode")
@ViewScoped
public class RealMode {

	private MapModel pointsModel;
	
	@PostConstruct
	public void init(){
		pointsModel = new DefaultMapModel();
		   LatLng coord1 = new LatLng(36.879466, 30.667648);  
	       LatLng coord2 = new LatLng(36.883707, 30.689216);  
	       LatLng coord3 = new LatLng(36.879703, 30.706707);  
	       
	      
	}

	public MapModel getPointsModel() {
		return pointsModel;
	}

	public void setPointsModel(MapModel pointsModel) {
		this.pointsModel = pointsModel;
	}
}
