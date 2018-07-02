package com.xyd.transfer.client;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xyd.transfer.ip.PackEncodeException;
import com.xyd.transfer.ip.datapack.ClientHeartbeat;
import com.xyd.transfer.ip.datapack.SendPack;
import com.xyd.transfer.ip.datapack.Status;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.util.ResourceLeakDetector;

/**
 * @author yangzy QQ51511793
 * @version 2.0 Create at 2018-6-15
 */
public class ClientService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private AtomicInteger sessionid = new AtomicInteger(1);
	
	private String source;
	private String[] targets;
	private String physicalAddress;
	
	private String host;
	private int port;
	private String username;
	private String password;
	private boolean startup = true;
	private int timeout = 30;	//Unit: Second
	
//	private boolean heartbeat = false;
//	private Object obj = new Object();

	private SocketChannel socketChannel;
	
//	private final static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();

//	public LinkedBlockingQueue<String> getQueue() {
//		return queue;
//	}

	public ClientService(){
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String[] getTargets() {
		return targets;
	}

	public void setTargets(String[] targets) {
		this.targets = targets;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStartup() {
		return startup;
	}
	
	public void setStartup(boolean startup) {
		this.startup = startup;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
//	private static final int READ_IDEL_TIME_OUT = 4; // 读超时
//	private static final int WRITE_IDEL_TIME_OUT = 5;// 写超时
//	private static final int ALL_IDEL_TIME_OUT = 7; // 所有超时
    private EventLoopGroup group=new NioEventLoopGroup();
	
	public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {
		if (bootstrap != null) {
			try {
				PackHandler handler = new PackHandler(this);
				
				bootstrap.group(group).channel(NioSocketChannel.class)
						.option(ChannelOption.TCP_NODELAY, true)
						.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
						.handler(new ChannelInitializer<SocketChannel>() {
							@Override
							protected void initChannel(SocketChannel socketChannel) throws Exception {
								ChannelPipeline pipeline = socketChannel.pipeline();
//								pipeline.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
//										WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS)); // 1
//								pipeline.addLast(new HeartbeatServerHandler()); // 2
//								pipeline.addLast(new WriteTimeoutHandler(timeout));
								pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 10, 2, -12, 0));
								pipeline.addLast(handler);//3
								pipeline.addLast(new ReadTimeoutHandler(timeout));
							}
						});
				bootstrap.remoteAddress(host, port);
				ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
				//
				ChannelFuture future = bootstrap.connect();
				logger.info("client connect to host:{}, port:{}", host, port);
				future.addListener(new ReconnecListener(this));
				future.sync();
				if (future.isSuccess()) {
					socketChannel = (SocketChannel) future.channel();
					System.out.println("Connect to server. Succeed!  成功");
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bootstrap;
	}
	
	public void start(){
		if(startup){
			createBootstrap(new Bootstrap(), group);
		}else{
			logger.info("连接Socket已经关闭，请修改配置文件手工启动！");
		}
	}
	
	public void stop(){
		//强制注销以后不能自动登录
		this.setUsername(null);
		try {
	        // Wait until the connection is closed.
			socketChannel.closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
		}
	}
	
	public void afterConnected(){
		for(int i=0;i<1000000;i++) {
			sendHeartbeat();
			try {
				TimeUnit.MILLISECONDS.sleep(15000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void delay(){
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendHeartbeat() {
		int sessionID = sessionid.getAndIncrement();
//		String source = "069951010700100101";
//		String[] targets = {"010151010000000001"};
//		String physicalAddress = "170211010002";
		byte first = 1;
		
		if(source!=null && targets!=null &&physicalAddress!=null) {
//			if(sessionID==1) {
//				first = 1;
//			}else {
//				first = 2;
//			}
			SendPack pack = new ClientHeartbeat(sessionID, source, targets, Status.IDLE, first, physicalAddress);
			try {
				if(socketChannel.isActive()) {
					ByteBuf msg = pack.toBuffer();
					socketChannel.writeAndFlush(msg);
				}else {
					System.out.println("socketChannel is not active.");
				}
			} catch (PackEncodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			StringBuffer buf = new StringBuffer();
			buf.append("参数错误: ");
			buf.append("source=");
			buf.append(source);
			buf.append("; targets=");
			if(targets!=null && targets.length > 0) {
				for(String target: targets) {
					buf.append(target);
					buf.append(",");
				}
			}
			buf.append("; physicalAddress=");
			buf.append(physicalAddress);
			System.out.println(buf.toString());
		}
	}
	
}
