package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-21T23:08:59.323+0200")
@StaticMetamodel(Driver.class)
public class Driver_ {
	public static volatile SingularAttribute<Driver, Long> IDDriver;
	public static volatile SingularAttribute<Driver, String> FName;
	public static volatile SingularAttribute<Driver, Long> IDSystem;
	public static volatile SingularAttribute<Driver, Long> IDVehicle;
	public static volatile SingularAttribute<Driver, String> LName;
	public static volatile SingularAttribute<Driver, String> phone;
	public static volatile SingularAttribute<Driver, SystemUser> systemUser;
	public static volatile SingularAttribute<Driver, Vehicle> vehicle;
	public static volatile ListAttribute<Driver, Geopoint> geopoints;
	public static volatile ListAttribute<Driver, Route> routes;
	public static volatile ListAttribute<Driver, Stop> stops;
	public static volatile ListAttribute<Driver, Tank> tanks;
}
