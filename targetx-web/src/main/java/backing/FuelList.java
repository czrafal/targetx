package backing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import DTO.TankDTO;
import DTO.TankDeclareDTO;
import beans.TankListBean;
import pojos.DriverAndVehicle;

@ManagedBean
@ViewScoped
public class FuelList implements Serializable {
	
	private static final long serialVersionUID = -3404915432305442373L;

	@ManagedProperty(value = "#{driverList}")
	private DriverList drivers;
	private List<DriverAndVehicle> driversList; 
	private List<DriverAndVehicle> selectedDriver; 
	private String dateOption;
	private Date date;
	private Date dateAdd;
	private Date dateNotification;
	private Date dateFrom;
	private Date dateTo;
	private List<TankDTO> tankList;
	private TankDeclareDTO tankDeclareDTO = new TankDeclareDTO();
	
	@EJB
	private TankListBean tankBean;
	
	@PostConstruct
	public void init(){
		dateOption = "A";
		driversList = drivers.getDriverList();
	}

	public void searchAction() {
		if (dateOption.equals("A")) {// data
			tankList = fuelTankList(date);
		}
	}

	public void saveTankDeclaration(){

//		DriverAndVehicle vehicle = selectedDriver.get(0);
//		tankDeclareDTO.setIDVehicle(vehicle.getIDVehicle());
		tankBean.saveTankDeclaration(tankDeclareDTO);
	}
	
	public List<TankDTO> fuelTankList(Date tankDate){
		return tankBean.tankByDate(tankDate);
	}
	
	public DriverList getDrivers() {
		return drivers;
	}

	public void setDrivers(DriverList drivers) {
		this.drivers = drivers;
	}

	public List<DriverAndVehicle> getDriversList() {
		return driversList;
	}

	public void setDriversList(List<DriverAndVehicle> driversList) {
		this.driversList = driversList;
	}

	public List<DriverAndVehicle> getSelectedDriver() {
		return selectedDriver;
	}

	public void setSelectedDriver(List<DriverAndVehicle> selectedDriver) {
		this.selectedDriver = selectedDriver;
	}

	public Date getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	public Date getDateNotification() {
		return dateNotification;
	}

	public void setDateNotification(Date dateNotification) {
		this.dateNotification = dateNotification;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateOption() {
		return dateOption;
	}

	public void setDateOption(String dateOption) {
		this.dateOption = dateOption;
	}

	public List<TankDTO> getTankList() {
		return tankList;
	}

	public void setTankList(List<TankDTO> tankList) {
		this.tankList = tankList;
	}

	public TankDeclareDTO getTankDeclareDTO() {
		return tankDeclareDTO;
	}

	public void setTankDeclareDTO(TankDeclareDTO tankDeclareDTO) {
		this.tankDeclareDTO = tankDeclareDTO;
	}
	
}
