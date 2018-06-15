package com.xyd.utils;

import java.util.ArrayList;
import java.util.List;

public final class RegionUtil {
	private static final String ZERO_MASK = "000000000000";

	/**
	 * 获取所有上级行政区域代码
	 * @param regionCode
	 * @return
	 */
    public static List<String> upperRegionCodes(String regionCode){
    	List<String> upperRegionCodes = new ArrayList<String>();
    	
    	switch (regionCode.length()) {
		case 12:
			upperRegionCodes.add(regionCode.substring(0, 9));
		case 9:
			upperRegionCodes.add(regionCode.substring(0, 6));
		case 6:
			upperRegionCodes.add(regionCode.substring(0, 4));
		case 4:
			upperRegionCodes.add(regionCode.substring(0, 2));
		}
    	return upperRegionCodes;
    }
    
    /**
     * 删除尾部补足的0
     * @param regionCode
     * @return
     */
    public static String removeZero(String regionCode) {
    	if(regionCode != null && regionCode.length() == 12) {
    		if(ZERO_MASK.equals(regionCode))
    			return "";
    		
    		String newCode = regionCode;
    		for(int i = 4; i >= 0; i--) {
    			if(i > 2) {
    				if(newCode.endsWith("000")) {
    					newCode = newCode.substring(0, i*3 -3);
    				} else {
    					return newCode;
    				}
    			} else {
    				if(newCode.endsWith("00")) {
    					newCode = newCode.substring(0, i*2);
    				} else {
    					return newCode;
    				}
    			}
    		}
    	}
    	
		return regionCode;
    }

    /**
     * 不满12位的尾部用0补足
     * @param regionCode
     * @return
     */
    public static String supplyZero(String regionCode) {
    	if(regionCode != null && regionCode.length() < 12) {
    		return regionCode + ZERO_MASK.substring(0, 12 - regionCode.length());
    	}
    	
		return regionCode;
    }
}
