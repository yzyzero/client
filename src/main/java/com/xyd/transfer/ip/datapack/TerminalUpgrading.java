package com.xyd.transfer.ip.datapack;

import org.apache.commons.codec.DecoderException;
import io.netty.buffer.ByteBuf;

/**
 * 序号	语法 				长度（字节） 		编码规则
 * 1 	物理地址编码长度 		1 				物理地址编码的长度。
 * 2 	物理地址编码 			物理地址编码长度	终端唯一标识，出厂时生成，固定不变。
 * 3 	软件升级模式 			1				0：强行升级，不比对版本号；
 * 											1：高版本升级，当新软件版本比现有终端版本高时执行软件升级；
 * 											2：给指定版本升级，终端版软件本与指定的版本（旧软件版本）一致时才升级到新版本。
 * 4 	新软件版本号 			4 				待升级的软件版本号。
 * 5 	旧软件版本号 			4				软件升级模式为0 和1 时无意义，为2时有效。
 * 6 	升级文件MD5 校验码 	32 				用于终端下载完成后对文件进行校验。
 * 7 	升级文件URL长度 		1 				升级文件URL 地址的长度。
 * 8 	升级文件URL地址 		升级文件URL长度	升级文件URL 地址。
 * 9 	预留 				12 				保留字段。
 */
public class TerminalUpgrading extends SendPack {

	@Override
	protected PackType getType() {
		return PackType.REQUEST;
	}

	@Override
	protected int generateBuf(ByteBuf buf) throws DecoderException {
		// TODO 未完成打包代码
		return 0;
	}

	@Override
	public OperationType getOperation() {
		return OperationType.UPGRADING;
	}

}
