package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-21T23:08:59.350+0200")
@StaticMetamodel(Vehicle.class)
public class Vehicle_ {
	public static volatile SingularAttribute<Vehicle, Long> IDVehicle;
	public static volatile SingularAttribute<Vehicle, String> brand;
	public static volatile SingularAttribute<Vehicle, Date> buyDate;
	public static volatile SingularAttribute<Vehicle, Date> checkEnd;
	public static volatile SingularAttribute<Vehicle, Date> checkStart;
	public static volatile SingularAttribute<Vehicle, Double> combustion;
	public static volatile SingularAttribute<Vehicle, Long> IDSystem;
	public static volatile SingularAttribute<Vehicle, String> model;
	public static volatile SingularAttribute<Vehicle, Date> OCDateEnd;
	public static volatile SingularAttribute<Vehicle, Date> OCDateStart;
	public static volatile SingularAttribute<Vehicle, String> regNum;
	public static volatile ListAttribute<Vehicle, Driver> drivers;
	public static volatile ListAttribute<Vehicle, Geopoint> geopoints;
	public static volatile ListAttribute<Vehicle, Route> routes;
	public static volatile ListAttribute<Vehicle, Stop> stops;
	public static volatile ListAttribute<Vehicle, Tank> tanks1;
	public static volatile ListAttribute<Vehicle, Tank> tanks2;
	public static volatile SingularAttribute<Vehicle, SystemUser> systemUser;
	public static volatile SingularAttribute<Vehicle, GpsDevice> gpsDevice;
}
