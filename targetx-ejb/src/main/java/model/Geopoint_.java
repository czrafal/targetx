package model;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-21T23:08:59.346+0200")
@StaticMetamodel(Geopoint.class)
public class Geopoint_ {
	public static volatile SingularAttribute<Geopoint, Long> IDGeopoints;
	public static volatile SingularAttribute<Geopoint, Timestamp> dateTime;
	public static volatile SingularAttribute<Geopoint, Double> distance;
	public static volatile SingularAttribute<Geopoint, Double> gas;
	public static volatile SingularAttribute<Geopoint, Long> IDDriver;
	public static volatile SingularAttribute<Geopoint, Long> IDRoutes;
	public static volatile SingularAttribute<Geopoint, Long> IDSystem;
	public static volatile SingularAttribute<Geopoint, Long> IDVehicle;
	public static volatile SingularAttribute<Geopoint, Double> lat;
	public static volatile SingularAttribute<Geopoint, Double> lon;
	public static volatile SingularAttribute<Geopoint, Integer> maxspeed;
	public static volatile SingularAttribute<Geopoint, Driver> driver;
	public static volatile SingularAttribute<Geopoint, Route> route;
	public static volatile SingularAttribute<Geopoint, SystemUser> systemUser;
	public static volatile SingularAttribute<Geopoint, Vehicle> vehicle;
}
