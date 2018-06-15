package com.xyd.model;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class IDGenerator implements IdentifierGenerator {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
    	Broadcast broadcast = (Broadcast)object;
    	if(broadcast.getId() != null && broadcast.getId().length() == 30) {
    		return broadcast.getId();
    	}
    	
    	try {
    		String prefix = broadcast.getSource() + DATE_FORMAT.format(new Date());
    		PreparedStatement maxseqStat = session.connection().prepareStatement(
    				"select max(right(id,4)) from eb_broadcast where left(id, 26)='" + prefix + "'");
    		ResultSet resultSet = maxseqStat.executeQuery();
    		int maxSequence = 1;
    		if(resultSet.next()){
    			String seqData = resultSet.getString(1);
    			if(seqData != null){
    				maxSequence = Integer.parseInt(seqData) + 1;
    			}
    		}
    		
	        return prefix + String.format("%04d", maxSequence);
		} catch (SQLException e) {
			return null;
		}
	}
}
