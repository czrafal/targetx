package beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton(name = "NettyEJB")
@Startup
public class NettyEJB {
	
	private NettyServer nettyServer;

	@PostConstruct
	void init() {
		try {
			new NettyServer().startServer();
			//nettyServer.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@PreDestroy
	public void stopNetty() {
		nettyServer.stopServer();
	}
}
