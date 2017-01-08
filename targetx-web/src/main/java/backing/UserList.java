package backing;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.SystemUser;
import beans.SystemUserListBean;

@ManagedBean(name = "userList")
@ViewScoped
public class UserList {

	private List<SystemUser> systemUserList;

	@EJB
	SystemUserListBean systemUserListBean;
	
	@PostConstruct
	public void init(){
		systemUserList = systemUserListBean.allSystemUser("SystemUsers.findAllUsers");
	}
	
	public List<SystemUser> getSystemUserList() {
		return systemUserList;
	}

	public void setSystemUserList(List<SystemUser> systemUserList) {
		this.systemUserList = systemUserList;
	}
}
