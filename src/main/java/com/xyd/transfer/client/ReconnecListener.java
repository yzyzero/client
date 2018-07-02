package com.xyd.transfer.client;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
/**
 * Netty Client启动的时候需要重连
 * @author yangzy QQ51511793
 * @version 2.0 Create at 2018-1-15
 *
 */
public class ReconnecListener implements ChannelFutureListener {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private ClientService client;

	public ReconnecListener(ClientService client) {
		this.client = client;
	}

	@Override
	public void operationComplete(ChannelFuture channelFuture) throws Exception {
		if (!channelFuture.isSuccess()) {
			logger.info("Reconnect......");
			final EventLoop loop = channelFuture.channel().eventLoop();
			loop.schedule(new Runnable() {
				@Override
				public void run() {
					client.createBootstrap(new Bootstrap(), loop);
				}
			}, 5L, TimeUnit.SECONDS);
		}
	}
}
