package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import DTO.TankDTO;
import DTO.TankDeclareDTO;

import model.Tank;
import model.TankDeclare;
import model.Vehicle;

@Stateless
public class TankListBean {

	@PersistenceContext(unitName="CarMonitoring")
	EntityManager entityManager;
	
	public List<TankDTO> tankByDate(Date tankDate){
		Map<Vehicle, List<Tank>> tankMap = new HashMap<Vehicle, List<Tank>>();
		Query query = entityManager.createNamedQuery(Tank.GET_TANK_BY_DAY);
		query.setParameter("IDSystem", new Long(2));
		query.setParameter("dateStart", new Date(tankDate.getTime()), TemporalType.DATE);
		List<Tank> tankList =  query.getResultList();
		List<TankDTO> tankListDTO = new ArrayList<TankDTO>();
		List<TankDTO> tankListRetDTO = new ArrayList<TankDTO>();
		for(Tank tank:tankList){
			TankDTO tankDTO = new TankDTO();
			tankDTO.setRoute(tank.getRoute());
			tankDTO.setVehicle(tank.getVehicle1());
			tankListDTO.add(tankDTO);
		}
		for(int i=0; i<tankListDTO.size(); i++){
			Vehicle veh = tankListDTO.get(i).getVehicle();
			Tank tank = tankListDTO.get(i).getTank();
			if(tankMap.containsKey(veh)){
				List<Tank> tankListMap = tankMap.get(veh);
				tankListMap.add(tank);
				tankMap.put(veh, tankListMap);
			}else{
				List<Tank> tankListMap = new ArrayList<Tank>();
				tankListMap.add(tank);
				tankMap.put(veh, tankListMap);			
			}
		}
		for(Vehicle vehicle:tankMap.keySet()){
			TankDTO tankDTO = new TankDTO();
			tankDTO.setVehicle(vehicle);
			List<Tank> tankListEntity = tankMap.get(vehicle);
			tankDTO.setTankList(tankListEntity);
			tankListRetDTO.add(tankDTO);
		}
		return tankListRetDTO;
	}
	
	public void saveTankDeclaration(TankDeclareDTO declare){
		TankDeclare tankDeclareEntity = new TankDeclare();
		tankDeclareEntity.setAdddate(declare.getAdddate());
		tankDeclareEntity.setIDSystem(new Long(2));
		tankDeclareEntity.setIDVehicle(declare.getIDVehicle());
		tankDeclareEntity.setLitresdeclare(declare.getLitresdeclare());
		tankDeclareEntity.setNotificationdate(declare.getNotificationdate());
		entityManager.persist(tankDeclareEntity);
	}
}
