package beans;
import javax.ejb.EJB;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
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
    	System.out.println("Drukuje wiadomosc:"+message);
        
        ChannelBuffer response = ChannelBuffers.directBuffer(1);
        if (message != null && message.trim().equalsIgnoreCase("352848022867325")) {
                response.writeByte(1);
        } else {
                response.writeByte(0);
        }
        ctx.getChannel().write(response);
    	
    /*	String[] dane = message.split(":");
    	
    	if(dane[0].equals("1")){	
    		Geopoint newGeopoint = new Geopoint();
    		System.out.println("Drukuje wiadomosc:"+message);
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
    		newGeopoint.setIDDriver(new BigInteger(dane[2]));
    		newGeopoint.setIDRoutes(new Long(1));
    		newGeopoint.setIDVehicle(new Long(dane[3]));
    		try {
				Context ctxgt = new InitialContext();
				AddGeopoint addGeopoint = (AddGeopoint) ctxgt.lookup("java:global/targetx-ear/targetx-web-1.0/AddGeopoint");
				addGeopoint.addNewGeopoint(newGeopoint);
    		} catch (NamingException e1) {
				e1.printStackTrace();
			}
    	}*/
    	
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) { 
        e.getCause().printStackTrace();  
        Channel ch = e.getChannel();
        ch.close();
    }
}
