package com.xyd.model;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseEnumConverter<X extends BaseEnum> implements AttributeConverter<BaseEnum, Integer> {
	Logger logger = LoggerFactory.getLogger(BaseEnumConverter.class);

    @Override
    public Integer convertToDatabaseColumn(BaseEnum attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public X convertToEntityAttribute(Integer dbData) {
    	if(dbData != null) {
	        return getObject(dbData);
    	}
        
        return null;
    }
    
    protected abstract X getObject(Integer value);
}
