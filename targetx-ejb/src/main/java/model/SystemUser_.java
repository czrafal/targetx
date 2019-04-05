package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-21T23:08:59.348+0200")
@StaticMetamodel(SystemUser.class)
public class SystemUser_ {
	public static volatile SingularAttribute<SystemUser, Long> IDSystem;
	public static volatile ListAttribute<SystemUser, Driver> drivers;
	public static volatile ListAttribute<SystemUser, Geopoint> geopoints;
	public static volatile ListAttribute<SystemUser, Route> routes;
	public static volatile ListAttribute<SystemUser, Stop> stops;
	public static volatile ListAttribute<SystemUser, SystemUsersClient> systemUsersClients;
	public static volatile ListAttribute<SystemUser, Vehicle> vehicles;
}
