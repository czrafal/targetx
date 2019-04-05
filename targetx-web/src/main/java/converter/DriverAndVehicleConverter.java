package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Vehicle;
import beans.VehiclesListBean;

@FacesConverter(value="driverAndVehicleConv")
public class DriverAndVehicleConverter implements Converter{
	
	private VehiclesListBean bean = null;
	private Context context = null;
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
	
		try {
			 context = new InitialContext();
			 bean = (VehiclesListBean) context.lookup("java:global/targetx-ear-1.0/targetx-web-1.0/VehiclesListBean");
		}
		catch (NamingException e) {
			e.printStackTrace();
		}  
		
		return bean.getVehicleByRegNum(new Long(2), Long.parseLong(value));
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		
		Vehicle veh = (Vehicle)value;
		
		String regAsString = "";
		if (value != null){
			   regAsString = veh.getIDVehicle().toString();
		}
		return regAsString;
		
	}

}
