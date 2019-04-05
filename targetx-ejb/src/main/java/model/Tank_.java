package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-21T23:08:59.349+0200")
@StaticMetamodel(Tank.class)
public class Tank_ {
	public static volatile SingularAttribute<Tank, Long> IDTank;
	public static volatile SingularAttribute<Tank, Long> IDDriver;
	public static volatile SingularAttribute<Tank, Long> IDRoute;
	public static volatile SingularAttribute<Tank, Long> IDSystem;
	public static volatile SingularAttribute<Tank, Long> IDVehicle;
	public static volatile SingularAttribute<Tank, Double> lat;
	public static volatile SingularAttribute<Tank, Double> litres;
	public static volatile SingularAttribute<Tank, Double> lon;
	public static volatile SingularAttribute<Tank, Double> price;
	public static volatile SingularAttribute<Tank, Driver> driver;
	public static volatile SingularAttribute<Tank, Route> route;
	public static volatile SingularAttribute<Tank, Vehicle> vehicle1;
	public static volatile SingularAttribute<Tank, Vehicle> vehicle2;
}
