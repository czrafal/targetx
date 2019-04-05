package backing;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
 
@ManagedBean
@ViewScoped
public class SettingsBean implements Serializable {
     
    private int number;
 
    public int getNumber() {
        return number;
    }
 
    public void increment() {
        number++;
    }
}