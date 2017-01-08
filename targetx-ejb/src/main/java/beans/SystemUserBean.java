package beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.SystemUser;

/**
 * Session Bean implementation class SystemUserBean
 * Rejestracja u�ytkownika systemowego
 */
@Stateless
@LocalBean
public class SystemUserBean {

	@PersistenceContext(unitName="CarMonitoring")
	private EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public SystemUserBean() {
        // TODO Auto-generated constructor stub
    	System.out.println("SystemUserBean STARTED");
    }
    public void addSystemUser(SystemUser sysUser){
    	entityManager.persist(sysUser);
    }
    public void updateSystemUser(SystemUser sysUser){
    	entityManager.merge(sysUser);
    }
    public SystemUser getSystemUserById(int id){
    	return entityManager.find(SystemUser.class, id);
    }
    public SystemUser getSystemUserByLogin(String name){
    	return entityManager.find(SystemUser.class, name);
    }
    
	
}
