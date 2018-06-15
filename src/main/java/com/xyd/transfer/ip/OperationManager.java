package com.xyd.transfer.ip;

import io.netty.channel.socket.SocketChannel;

public interface OperationManager<T extends OperationProcessor> {
	T getProcessor(SocketChannel channel, String resourceCode);
}
