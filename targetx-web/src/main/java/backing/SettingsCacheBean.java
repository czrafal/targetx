package backing;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import utils.MonthsEnum;

@ApplicationScoped
@ManagedBean(name="settingsCache")
public class SettingsCacheBean {

	 public MonthsEnum[] getMonths() {
	        return MonthsEnum.values();
	    }
}
