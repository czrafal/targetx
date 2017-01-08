package backing;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.SystemUser;
import beans.SystemUserBean;

@ManagedBean(name = "userLogin")
@SessionScoped
public class UserLogin {
	
	private SystemUser systemUser = new SystemUser();
	
	@EJB
	private SystemUserBean systemUserBean;
	
	public String registerSystemUser(){
		
		systemUserBean.addSystemUser(systemUser);
		return "systemUsersList?faces-redirect=true";
	}

	public SystemUser getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}
	
	public SystemUserBean getSystemUserBean() {
		return systemUserBean;
	}
	
	public void setSystemUserBean(SystemUserBean systemUserBean) {
		this.systemUserBean = systemUserBean;
	}
}
