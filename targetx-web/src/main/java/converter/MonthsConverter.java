package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import utils.MonthsEnum;

@FacesConverter(value = "monthsConverter")
public class MonthsConverter implements Converter {

    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        if (context == null) { throw new NullPointerException("context");}
        if (component == null) {throw new NullPointerException("component");}
        if(obj instanceof MonthsEnum)
        {
             return String.valueOf(MonthsEnum.get((MonthsEnum)obj));
        }else
        {   
            return "";
        }
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (context == null) { throw new NullPointerException("context");}
        if (component == null) {throw new NullPointerException("component");}

        MonthsEnum color = null;
        if (value != null && !value.equalsIgnoreCase("") && value.trim().length() > 0) {
            color = MonthsEnum.get(Integer.valueOf(value));
            if (color == null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown value", "The car is unknown!");
                throw new ConverterException(message);
            }
        }
        return color;
    }
}