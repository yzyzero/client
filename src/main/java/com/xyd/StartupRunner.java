package com.xyd;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.xyd.transfer.client.ClientService;

//import cn.tass.yingjgb.YingJGBCALLDLL;
//import org.apache.commons.codec.binary.Base64;

@Component
@Order(1)
public class StartupRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);
    
    private int threadCount = 1;
    
	@Value("${application.socket.user:server}")
	private String username;

	@Value("${application.socket.pass:123456}")
	private String password;

	@Value("${application.socket.host:127.0.0.1}")
	private String host = "127.0.0.1";

	@Value("${application.socket.port:8788}")
	private int port = 8788;
	
	@Value("${application.socket.startup:true}")
	private boolean startup = true;
	
    public void startIMPClient(){
    	try {
    		ClientService client = new ClientService();
    		client.setHost(host);
    		client.setPort(port);
    		client.setStartup(startup);
			System.out.println("IMPClient 连接服务器，主机"+client.getHost()+", 端口"+client.getPort() + ", 是否初始化后启动: " + startup);
			
    		client.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void run(String... args) throws Exception {
		if(args.length > 0) {
			try {
				if(StringUtils.isNumber(args[0])) {
					threadCount = Integer.parseInt(args[0]);
				}else {
					System.out.println("命令行参数: "+args[0]);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Startup Runner ......threadCount="+threadCount);
		
		// TODO 计算数字签名
//		byte[] hbBuf = {0x01, 0x02, 0x03, 0x04};
//		System.out.println(YingJGBCALLDLL.platformCalculateSignature(1, hbBuf));
		
		AtomicInteger x = new AtomicInteger(0);
		
		for(int i=0; i<threadCount; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						StringBuffer buf = new StringBuffer();
						buf.append(x.getAndIncrement());
						buf.append(": ");
						System.out.println(buf.toString());
						startIMPClient();
						
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
		}
	}

}
