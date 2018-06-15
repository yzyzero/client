package com.xyd.transfer.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.ResourceLeakDetector;

import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author yangzy QQ51511793
 * @version 2.0 Create at 2018-1-15
 */
public class IMPManager {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
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

	public IMPManager(){
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
				IMPClientHandler handler = new IMPClientHandler(this);
				
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
								pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 10, 2, 0, 0));
								pipeline.addLast(handler);//3
								pipeline.addLast(new ReadTimeoutHandler(timeout));
							}
						});
				bootstrap.remoteAddress(host, port);
				ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
				//
				ChannelFuture future = bootstrap.connect();
				logger.info("client connect to host:{}, port:{}", host, port);
				future.addListener(new IMPConnectionListener(this));
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

	}
	
	public void delay(){
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
