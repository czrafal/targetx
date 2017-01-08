package beans;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Geopoint;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;


public class EchoServerHandler extends SimpleChannelHandler { 
	
	@EJB
	CacheDriversSingleton cacheDrivers;

	public EchoServerHandler(){
	
	}

	@Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) { 

    	String message = (String)e.getMessage();
    	String[] dane = message.split(":");
    	
    	if(dane[0].equals("1")){	
    		Geopoint newGeopoint = new Geopoint();
    		System.out.println("Drukuje wiadomoœæ:"+message);
    		newGeopoint.setDistance(23);
    		newGeopoint.setLat(Double.parseDouble(dane[4]));
    		newGeopoint.setLon(Double.parseDouble(dane[5]));
    		Date data = new Date();
    		data.getDate();
    		Timestamp dat = new Timestamp(data.getTime());
    		newGeopoint.setDateTime(dat);
    		newGeopoint.setGas(Double.parseDouble(dane[6]));
    		newGeopoint.setMaxspeed(Integer.parseInt(dane[7]));
    		newGeopoint.setIDSystem(new Long(dane[1]));
    		newGeopoint.setIDDriver(new Long(dane[2]));
    		newGeopoint.setIDRoutes(new Long(1));
    		newGeopoint.setIDVehicle(new Long(dane[3]));
    		try {

				Properties props = new Properties();
				props.setProperty("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory");

				Context ctxgt = new InitialContext(props);
				AddGeopoint addGeopoint = (AddGeopoint) ctxgt.lookup("java:global/TargetXEar/TargetXEJB/AddGeopoint");
				addGeopoint.addNewGeopoint(newGeopoint);
    		} catch (NamingException e1) {
				e1.printStackTrace();
			}
    	}
    	
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) { 
        e.getCause().printStackTrace();  
        Channel ch = e.getChannel();
        ch.close();
    }
}
