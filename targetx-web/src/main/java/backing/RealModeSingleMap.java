package backing;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;

@ManagedBean(name="realModeSingle")
@ViewScoped
public class RealModeSingleMap {

    private MapModel mapModel;
    private LatLng start;
    private LatLng end;
    
    public LatLng getStart() {
		return start;
	}

	public void setStart(LatLng start) {
		this.start = start;
	}

	public LatLng getEnd() {
		return end;
	}

	public void setEnd(LatLng end) {
		this.end = end;
	}

	public RealModeSingleMap() {
        mapModel = new DefaultMapModel();
        start = new LatLng(36.879466, 30.667648);
        end = new LatLng(36.879703, 30.706707); 
    }

	public MapModel getMapModel() {
		return mapModel;
	}

	public void setEmptyModel(MapModel emptyModel) {
		this.mapModel = emptyModel;
	}
}
