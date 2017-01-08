package beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.SystemUser;

/**
 * Session Bean implementation class SystemUserListBean
 */

@Stateless
@LocalBean
public class SystemUserListBean {

	@PersistenceContext(unitName="CarMonitoring")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public SystemUserListBean() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<SystemUser> allSystemUser(String findAllUsers) {
		return entityManager.createNamedQuery(findAllUsers).getResultList();
	}
    public void updateSystemUser(SystemUser sysUser){
    	entityManager.merge(sysUser);
    }
    public SystemUser getSystemUserById(int id){
    	return entityManager.find(SystemUser.class, id);
    }
}
