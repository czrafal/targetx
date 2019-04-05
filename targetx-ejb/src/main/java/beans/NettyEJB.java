package beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.traccar.ServerManager;

@Singleton(name = "NettyEJB")
@Startup
public class NettyEJB {
	
//	private NettyServer nettyServer;
    private ServerManager server;
	@PostConstruct
	void init() {
		try {
//			new NettyServer().startServer();
	        server = new ServerManager();
	        server.start();
	        System.out.println("Teltonika Protocol server started!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@PreDestroy
	public void stopNetty() {
//		nettyServer.stopServer();
	}
}
