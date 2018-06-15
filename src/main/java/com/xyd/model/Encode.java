package com.xyd.model;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

public enum Encode implements BaseEnum{
	NONE(0, "未定义",""),
	MPEG1_AUDIO(1, "MPEG-1 LayerI/II音频文件",".mpg"),
	MP3(2, "MPEG-1 LayerIII 音频文件",".mp3"),
	AAC(3, "《IP 传输应急广播技术规范》定义",".aac"),
	MPEG2(21, "MPEG-2 编码音视频文件",".mpg"),
	H264(22, "H.264 编码音视频文件",".264"),
	AVS(23, "AVS+编码音视频文件",".avs"),
	PNG(41, "PNG 图片文件",".png"),
	JPEG(42, "JPEG 图片文件",".jpg"),
	GIF(43, "GIF 图片文件",".gif"),
	UNVARNISHED(44, "透传数据，类型由厂家扩展(《有线数字电视应急广播技术规范》)",""),
	RTSP(61, "实时流(本系统均采用RTSP/RTP)",""),
	UTF8(80, "UTF-8",""),
	GBK(81, "GBK",""),
	GB2312(82, "GB2312",""),
	UNICODE(83, "UNICODE",""),
	ASCII(84, "ASCII","");
	
    private Integer value;
    private String desc;
    private String suffix;
    
    Encode(Integer value, String desc, String suffix) {
        this.value = value;
        this.desc = desc;
        this.suffix = suffix;
    }

    @Override
    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
    
    public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
    
    public static Encode valueOf(Integer value) {
        switch (value) {
        case 1: return MPEG1_AUDIO;
        case 2: return MP3;
        case 3: return AAC;
        case 21: return MPEG2;
        case 22: return H264;
        case 23: return AVS;
        case 41: return PNG;
        case 42: return JPEG;
        case 43: return GIF;
        case 44: return UNVARNISHED;
        case 61: return RTSP;
        case 80: return UTF8;
        case 81: return GBK;
        case 82: return GB2312;
        case 83: return UNICODE;
        case 84: return ASCII;
        default: return NONE;  
        }  
    }

	public static class EncodeConvert extends BaseEnumConverter<Encode>{
		@Override
		protected Encode getObject(Integer value) {
			return valueOf(value);
		}
    }
	
	public static class JSONSerializer implements ObjectSerializer{
		@Override
		public void write(com.alibaba.fastjson.serializer.JSONSerializer serializer, Object object, Object fieldName,
				Type fieldType, int features) throws IOException {
			serializer.write(((Encode) object).getValue());
			
		}
	}
	
	public static class JSONDeserializer implements ObjectDeserializer {

		@SuppressWarnings("unchecked")
		@Override
		public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
	        JSONLexer lexer = parser.getLexer();
	        int value = lexer.intValue();
	        return (T) valueOf(value);
		}

		@Override
		public int getFastMatchToken() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
}
