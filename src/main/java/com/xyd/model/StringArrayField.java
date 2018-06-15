package com.xyd.model;

import java.io.Serializable;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class StringArrayField implements UserType {
	protected static final int[] SQL_TYPES = { Types.ARRAY };

	@Override
	public int[] sqlTypes() {
	    return SQL_TYPES;
	}

	@Override
	public Class<?> returnedClass() {
	    return String[].class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
	    if (x == y) {
	        return true;
	    } else if (x == null || y == null) {
	        return false;
	    } else {
	        return x.equals(y);
	    }
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {

	    if (rs.wasNull()) {
	        return null;
	    }

	    String[] array = (String[]) rs.getArray(names[0]).getArray();
	    return array;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
	    if (value == null) {
	    	st.setNull(index, SQL_TYPES[0]);
	    } else {
	        String[] castObject = (String[]) value;
	        Array array = session.connection().createArrayOf("text", castObject);
	        st.setArray(index, array);
	    }
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return null;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return null;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

}
