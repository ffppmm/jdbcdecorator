package at.fpmedv.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

public abstract class ConnectionDecorator implements java.sql.Connection {

	public java.sql.Connection connection;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setUnderLyingConnection(java.sql.Connection con) {
		connection = con;
	}
	
	public void clearWarnings() throws SQLException {
		connection.clearWarnings();
	}

	public void close() throws SQLException {
		connection.close();
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		return connection.createArrayOf(arg0, arg1);
	}

	public Blob createBlob() throws SQLException {
		return connection.createBlob();
	}

	public Clob createClob() throws SQLException {
		return connection.createClob();
	}

	public NClob createNClob() throws SQLException {
		return connection.createNClob();
	}

	public SQLXML createSQLXML() throws SQLException {
		return connection.createSQLXML();
	}

	public Statement createStatement() throws SQLException {
		return connection.createStatement();
	}

	public Statement createStatement(int arg0, int arg1) throws SQLException {
		return connection.createStatement(arg0, arg1);
	}

	public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
		return connection.createStatement(arg0, arg1, arg2);
	}

	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		return connection.createStruct(arg0, arg1);
	}

	public boolean getAutoCommit() throws SQLException {
		return connection.getAutoCommit();
	}

	public String getCatalog() throws SQLException {
		return connection.getCatalog();
	}

	public Properties getClientInfo() throws SQLException {
		return connection.getClientInfo();
	}

	public String getClientInfo(String arg0) throws SQLException {
		return connection.getClientInfo(arg0);
	}

	public int getHoldability() throws SQLException {
		return connection.getHoldability();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return connection.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException {
		return connection.getTransactionIsolation();
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return connection.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException {
		return connection.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return connection.isClosed();
	}

	public boolean isReadOnly() throws SQLException {
		return connection.isReadOnly();
	}

	public boolean isValid(int arg0) throws SQLException {
		return connection.isValid(arg0);
	}

	public String nativeSQL(String arg0) throws SQLException {
		return connection.nativeSQL(arg0);
	}

	public CallableStatement prepareCall(String arg0) throws SQLException {
		return connection.prepareCall(arg0);
	}

	public CallableStatement prepareCall(String arg0, int arg1, int arg2) throws SQLException {
		return connection.prepareCall(arg0, arg1, arg2);
	}

	public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
		return connection.prepareCall(arg0, arg1, arg2, arg3);
	}

	public PreparedStatement prepareStatement(String arg0) throws SQLException {
		return connection.prepareStatement(arg0);
	}

	public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
		return connection.prepareStatement(arg0, arg1);
	}

	public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
		return connection.prepareStatement(arg0, arg1);
	}

	public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
		return connection.prepareStatement(arg0, arg1);
	}

	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
		return connection.prepareStatement(arg0, arg1, arg2);
	}

	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
		return connection.prepareStatement(arg0, arg1, arg2, arg3);
	}

	public void releaseSavepoint(Savepoint arg0) throws SQLException {
		connection.releaseSavepoint(arg0);
	}

	public void rollback() throws SQLException {
		connection.rollback();
	}

	public void rollback(Savepoint arg0) throws SQLException {
		connection.rollback(arg0);
	}

	public void setAutoCommit(boolean arg0) throws SQLException {
		connection.setAutoCommit(arg0);
	}

	public void setCatalog(String arg0) throws SQLException {
		connection.setCatalog(arg0);
	}

	public void setClientInfo(Properties arg0) throws SQLClientInfoException {
		connection.setClientInfo(arg0);
	}

	public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {
		connection.setClientInfo(arg0, arg1);
	}

	public void setHoldability(int arg0) throws SQLException {
		connection.setHoldability(arg0);
	}

	public void setReadOnly(boolean arg0) throws SQLException {
		connection.setReadOnly(arg0);
	}

	public Savepoint setSavepoint() throws SQLException {
		return connection.setSavepoint();
	}

	public Savepoint setSavepoint(String arg0) throws SQLException {
		return connection.setSavepoint(arg0);
	}

	public void setTransactionIsolation(int arg0) throws SQLException {
		connection.setTransactionIsolation(arg0);
	}

	public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
		connection.setTypeMap(arg0);
	}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return connection.isWrapperFor(arg0);
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return connection.unwrap(arg0);
	}



}
