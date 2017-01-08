package model;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-21T23:08:59.347+0200")
@StaticMetamodel(Route.class)
public class Route_ {
	public static volatile SingularAttribute<Route, Long> IDRoutes;
	public static volatile SingularAttribute<Route, Timestamp> dateEnd;
	public static volatile SingularAttribute<Route, Timestamp> dateStart;
	public static volatile SingularAttribute<Route, Double> distanceSum;
	public static volatile SingularAttribute<Route, Double> fuelCostAll;
	public static volatile SingularAttribute<Route, Double> fuelCostPerKM;
	public static volatile SingularAttribute<Route, Double> fuelEnd;
	public static volatile SingularAttribute<Route, String> fuelGet;
	public static volatile SingularAttribute<Route, Double> fuelStart;
	public static volatile SingularAttribute<Route, Long> IDDriver;
	public static volatile SingularAttribute<Route, Long> IDSystem;
	public static volatile SingularAttribute<Route, Long> IDVehicle;
	public static volatile SingularAttribute<Route, Double> lat;
	public static volatile SingularAttribute<Route, Double> lon;
	public static volatile SingularAttribute<Route, String> stop;
	public static volatile SingularAttribute<Route, String> tank;
	public static volatile ListAttribute<Route, Geopoint> geopoints;
	public static volatile SingularAttribute<Route, Driver> driver;
	public static volatile SingularAttribute<Route, SystemUser> systemUser;
	public static volatile SingularAttribute<Route, Vehicle> vehicle;
	public static volatile ListAttribute<Route, Stop> stops;
	public static volatile ListAttribute<Route, Tank> tanks;
}
