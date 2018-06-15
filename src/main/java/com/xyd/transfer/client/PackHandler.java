package com.xyd.transfer.client;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.util.ReferenceCountUtil;

/**
 * @author yangzy QQ51511793
 * @version 2.0 Create at 2018-1-15
 */
public class PackHandler extends ChannelInboundHandlerAdapter { // (1)
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private ClientService client;
    
    public PackHandler(ClientService client){
    	this.client = client;
    }
    
    /**
     * 在程序运行中连接断掉需要重连
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        logger.info("client channel inactive");
        final EventLoop eventLoop = ctx.channel().eventLoop();  
        eventLoop.schedule(new Runnable() {  
          @Override  
          public void run() {  
            client.createBootstrap(new Bootstrap(), eventLoop);  
          }  
        }, 1L, TimeUnit.SECONDS);  
        super.channelInactive(ctx);  
    }
    /**
     * 重连成功以后需要初始化
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("client channel active");
        // Send the message to Server
        logger.info("client after connected...");
        client.afterConnected();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    	if(!(msg instanceof ByteBuf)){
    		logger.error("msg类型错误，不是ByteBuf.");
    		return;
    	}
        ByteBuf buf = (ByteBuf) msg;
        try {
        	byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
//        	IMPDataPackage pkg = new IMPDataPackage(req);
//        	int optcode = pkg.getOperateCode();
//    		String body = pkg.getDataBody();
//        	if(optcode == 0){
//	            logger.debug(body);
//        	}else{
//	            //String body = new String(req, 8, req.length-8, "UTF-8");
//	            //logger.info(body);
//        		client.getQueue().put(body);
//        	}
        }catch (Exception e){
            e.printStackTrace();
        }finally{
        	//ReferenceCountUtil.release(buf);//防止内存溢出
        	buf.release();//防止内存溢出
        	//ctx.close();//长连接不要关闭，短连接才关闭
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        logger.error("client caught exception", cause);
        ctx.close();
    }
}
