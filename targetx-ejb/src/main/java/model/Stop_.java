package model;

import java.sql.Time;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-21T23:08:59.348+0200")
@StaticMetamodel(Stop.class)
public class Stop_ {
	public static volatile SingularAttribute<Stop, Long> IDStop;
	public static volatile SingularAttribute<Stop, Timestamp> dateTime;
	public static volatile SingularAttribute<Stop, Long> IDDriver;
	public static volatile SingularAttribute<Stop, Long> IDRoute;
	public static volatile SingularAttribute<Stop, Long> IDSystem;
	public static volatile SingularAttribute<Stop, Long> IDVehicle;
	public static volatile SingularAttribute<Stop, Double> lat;
	public static volatile SingularAttribute<Stop, Double> lon;
	public static volatile SingularAttribute<Stop, Time> time;
	public static volatile SingularAttribute<Stop, Driver> driver;
	public static volatile SingularAttribute<Stop, Route> route;
	public static volatile SingularAttribute<Stop, SystemUser> systemUser;
	public static volatile SingularAttribute<Stop, Vehicle> vehicle;
}
