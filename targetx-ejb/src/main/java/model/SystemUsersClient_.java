package model;

import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-21T23:08:59.349+0200")
@StaticMetamodel(SystemUsersClient.class)
public class SystemUsersClient_ {
	public static volatile SingularAttribute<SystemUsersClient, Long> IDSystemClient;
	public static volatile SingularAttribute<SystemUsersClient, String> active;
	public static volatile SingularAttribute<SystemUsersClient, String> email;
	public static volatile SingularAttribute<SystemUsersClient, Long> IDSystem;
	public static volatile SingularAttribute<SystemUsersClient, Timestamp> lastLogin;
	public static volatile SingularAttribute<SystemUsersClient, String> login;
	public static volatile SingularAttribute<SystemUsersClient, String> password;
	public static volatile SingularAttribute<SystemUsersClient, Date> registerDate;
	public static volatile SingularAttribute<SystemUsersClient, String> role;
	public static volatile SingularAttribute<SystemUsersClient, SystemUser> systemUser;
}
