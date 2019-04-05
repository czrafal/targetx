package backing;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
@ManagedBean(name="navigationBean")
@ViewScoped
public class NavigationBean implements Serializable{

	private static final long serialVersionUID = 5358503547698448180L;

	//private static final long serialVersionUID = 1L;
	private String pageName="/system/chicken.xhtml";
	private String headerName = "";
//	private String outcomePage = "";
	
	public NavigationBean() {
		headerName = "Wszystkie pojazdy";
	}

	public void doNav() {
		FacesContext context = FacesContext.getCurrentInstance();
//	    this.pageName = context.getExternalContext().getRequestParameterMap().get("page");
	    this.headerName = context.getExternalContext().getRequestParameterMap().get("header");
//	    this.outcomePage = this.pageName;
//	    this.outcomePage+="?faces-redirect=true";
//	    try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect(pageName);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	    System.out.println("pageName from parameter:"+pageName);
	    Map map = context.getExternalContext().getRequestParameterMap();
        pageName = (String) map.get("currentNav");
        System.out.println("pageName from currentNav:"+pageName);
	    System.out.println("header:"+headerName);
	}

	public String getPageName() {
	    return pageName;
	}

	public void setPageName(String pageName) {
	    this.pageName = pageName;
	}
	
	public String getHeaderName() {
	    return headerName;
	}

	public void setHeaderName(String headerName) {
	    this.headerName = headerName;
	}

//	public String getOutcomePage() {
//		return outcomePage;
//	}
//
//	public void setOutcomePage(String outcomePage) {
//		this.outcomePage = outcomePage;
//	}
}
