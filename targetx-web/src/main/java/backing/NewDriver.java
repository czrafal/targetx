package backing;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import model.Driver;
import beans.NewDriverBean;

@ManagedBean(name="newDriver")
@RequestScoped
public class NewDriver {

	Driver driver = new Driver();

	@EJB
	private NewDriverBean newDriverBean;

	public void addNewDriver() {
		newDriverBean.addNewDriver(driver);
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public NewDriverBean getNewDriverBean() {
		return newDriverBean;
	}

	public void setNewDriverBean(NewDriverBean newDriverBean) {
		this.newDriverBean = newDriverBean;
	}
}
