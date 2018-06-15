package com.xyd.transfer.ip.datapack;

public enum Parameter {
	NONE, VOLUME, LOCAL_ADDRESS, ECHO_ADDRESS, RESOURCE_CODE, PHYSICALADDRESS, STATUS, FAULT_CODE;

    public static Parameter valueOf(int value) { 
        switch (value) {  
        case 1:  
            return VOLUME;  
        case 2:  
            return LOCAL_ADDRESS;  
        case 3:  
            return ECHO_ADDRESS;  
        case 4:  
            return RESOURCE_CODE; 
        case 5:  
            return PHYSICALADDRESS;
        case 6:  
            return STATUS; 
        case 7:  
            return FAULT_CODE; 
        default:  
            return NONE;  
        }  
    }
}
