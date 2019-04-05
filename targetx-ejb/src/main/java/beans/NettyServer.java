package beans;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.*;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class NettyServer {
	/*ChannelFactory factory;
	ServerBootstrap bootstrap;
	InetSocketAddress socket = new InetSocketAddress(5555);
	public void startServer() throws Exception {
		 factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

	    bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() {
				return Channels.pipeline(new StringDecoder(),
						new StringEncoder(), new EchoServerHandler());
			}
		});

		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(socket);
		
		System.out.println("Server Started at!"+socket);
	}

	public void stopServer() {
		bootstrap.bind().close();
		
		System.out.println("Server Stoped!");
	}*/
}
