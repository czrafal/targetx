package beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import model.Geopoint;

@Stateless
public class CombustionChartBean{

	@PersistenceContext(unitName="CarMonitoring")
	EntityManager entityManager;

	private List<Geopoint> listaDanych;
	
	@SuppressWarnings("unchecked")
	public List<Geopoint> getCombustionData(Long idSystem, Long idRoute){
		Query query = entityManager.createNamedQuery("Geopoint.findByIDRoute");
		query.setParameter("IDSystem", idSystem);
		query.setParameter("IDRoutes", idRoute);
		listaDanych = query.getResultList();
		return listaDanych;
	}
	
	@SuppressWarnings("unchecked")
	public List<Geopoint> getCombustionDataByRouteBetweenTime(Long idSystem, Long idRoute, String timeFrom, String timeTo){
		int minutesFrom = 0;
		int minutesTo = 0;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Calendar calFrom = Calendar.getInstance();
		Date dateFrom = null;
		Date dateTo = null;
		try {
			dateFrom = df.parse(timeFrom);
			dateTo = df.parse(timeTo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calFrom.setTime(dateFrom);
		Calendar calTo = Calendar.getInstance();
		calTo.setTime(dateTo);
		minutesFrom = calFrom.get(Calendar.HOUR_OF_DAY) * 60 + calFrom.get(Calendar.MINUTE);
		minutesTo = calTo.get(Calendar.HOUR_OF_DAY) * 60 + calTo.get(Calendar.MINUTE);
		Query query = entityManager.createNamedQuery("Geopoint.findByIDRouteAndTime");
		query.setParameter("IDSystem", idSystem);
		query.setParameter("IDRoutes", idRoute);
		query.setParameter("timeFrom", minutesFrom);
		query.setParameter("timeTo",  minutesTo);
//		query.setParameter("timeFrom",  new Date(timeFrom), TemporalType.TIME);
//		query.setParameter("timeTo",  new Date(timeTo), TemporalType.TIME);
		listaDanych = query.getResultList();
		return listaDanych;
	}
	
}
