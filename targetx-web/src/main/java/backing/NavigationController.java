package backing;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="navigateBean")
@SessionScoped

public class NavigationController {
   //private String targetUrl;
   private String pageToDisplay = "/system/empty.xhtml";

   public String getPageToDisplay(){
      return this.pageToDisplay;
   }
   
   public void setPageToDisplay(String pageToDisplay){
      this.pageToDisplay = pageToDisplay;
   }
}