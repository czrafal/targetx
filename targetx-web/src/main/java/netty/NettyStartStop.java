package netty;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import beans.NettyEJB;

@ManagedBean(name="NettyStartStop")
@RequestScoped
public class NettyStartStop {

	@EJB
	private NettyEJB nettyEJB;
	 
	public void startNetty(){
		
	}
	
	public void stopNetty(){
		System.out.println("NettyStartStop.stopNetty()");
		nettyEJB.stopNetty();
	}
}
