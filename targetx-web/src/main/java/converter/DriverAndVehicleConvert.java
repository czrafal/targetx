
package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import pojos.DriverAndVehicle;


import beans.DriverListBean;


@FacesConverter(value="driverAndVehicleConvert")
public class DriverAndVehicleConvert implements Converter{
	
	private DriverListBean bean = null;
	private Context context = null;
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		System.out.println("metoda getAsObject converter "+value);
		
		try {
			 context = new InitialContext();
			 bean = (DriverListBean) context.lookup("java:global/TargetXEar/TargetXEJB/DriverListBean");
		}
		
		catch (NamingException e) {
			e.printStackTrace();
		}  
		
		return bean.getVehicleByRegNum(new Long(2), Long.parseLong(value));
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		System.out.println("metoda getAsString converter ");
		DriverAndVehicle veh = (DriverAndVehicle)value;
		
		System.out.println("Vehicle value id:"+veh.getIDVehicle());
		System.out.println("Vehicle value regNum:"+veh.getRegNum());
		
		String regAsString = "";
		if (value != null){
			   regAsString = veh.getIDVehicle().toString();
			   System.out.println("ID pojazdu:"+regAsString);
		}
		return regAsString;
		
	}

}
