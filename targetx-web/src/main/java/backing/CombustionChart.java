package backing;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import model.Geopoint;

import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import beans.CombustionChartBean;

@ManagedBean(name="combustionChart")
public class CombustionChart implements Serializable{

	private LineChartModel lineChartModel;
	
	@EJB
	private CombustionChartBean chartBean;
	
	@PostConstruct
	public void init(){
		lineChartModel = new LineChartModel();
		Long idr = new Long(10);
		List<Geopoint> combustionData = chartBean.getCombustionData(new Long(2),idr);
		LineChartSeries time = new LineChartSeries();  
        time.setLabel("Czas");  
        for(Geopoint g:combustionData){
        	time.set(g.getDateTime(), g.getGas()); 
        }
        lineChartModel.addSeries(time);  
	}

	public CombustionChartBean getChartBean() {
		return chartBean;
	}

	public LineChartModel getLineChartModel() {
		return lineChartModel;
	}

	public void setLineChartModel(LineChartModel lineChartModel) {
		this.lineChartModel = lineChartModel;
	}

	public void setChartBean(CombustionChartBean chartBean) {
		this.chartBean = chartBean;
	}
	
}
