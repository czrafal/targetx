package backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import model.Geopoint;

import org.joda.time.DateTime;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import beans.CombustionChartBean;
 
@ManagedBean
@ViewScoped
public class ChartView implements Serializable {
 
	private static final long serialVersionUID = 4483473431778113373L;

	private LineChartModel areaModel;
	private List<String> timeStart;
	private List<String> timeEnd;
	private List<String> timeList = new ArrayList<String>();
	
    @EJB
    CombustionChartBean chartBean;
    private List<Geopoint> combustionData;
    
    @PostConstruct
    public void init() {
    	combustionData = chartBean.getCombustionData(new Long(2), new Long(12));
        createAreaModel();
    }
 
    public void recreateAreaModel(){
    	String timeFrom = timeStart.get(0);
    	String timeTo = timeEnd.get(0);
    	combustionData = chartBean.getCombustionDataByRouteBetweenTime(new Long(2), new Long(12), timeFrom, timeTo);
    	createAreaModel();
    }
    
    public LineChartModel getAreaModel() {
        return areaModel;
    }
     
    @SuppressWarnings("unchecked")
	private void createAreaModel() {
        areaModel = new LineChartModel();
        LineChartSeries time = new LineChartSeries();  
        time.setLabel("Paliwo[l]");  
        for(Geopoint g:combustionData){
        	DateTime pointTime = new DateTime(g.getDateTime());
        	String strPointTime = pointTime.getHourOfDay()+":"+pointTime.getMinuteOfHour();
        	time.set(strPointTime, g.getGas());
        	timeList.add(strPointTime);
        }
//        List<Object> keys = new ArrayList<Object>(time.getData().keySet());
//        timeList = keys;
//        List<String> ls = Lists.transform(keys, Functions.toStringFunction()); 
        areaModel.addSeries(time);  
        areaModel.setTitle("Wykres spalania");
        areaModel.setLegendPosition("ne");
        areaModel.setStacked(true);
        areaModel.setShowPointLabels(true);
         
        Axis xAxis = new CategoryAxis("Godzina");
        areaModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = areaModel.getAxis(AxisType.Y);
        yAxis.setLabel("Litry");
        yAxis.setMin(0);
        yAxis.setMax(300);
    }

	public List<Geopoint> getCombustionData() {
		return combustionData;
	}

	public void setCombustionData(List<Geopoint> combustionData) {
		this.combustionData = combustionData;
	}

	public List<String> getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(List<String> timeStart) {
		this.timeStart = timeStart;
	}

	public List<String> getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(List<String> timeEnd) {
		this.timeEnd = timeEnd;
	}

	public List<String> getTimeList() {
		return timeList;
	}

	public void setTimeList(List<String> timeList) {
		this.timeList = timeList;
	}
     
}