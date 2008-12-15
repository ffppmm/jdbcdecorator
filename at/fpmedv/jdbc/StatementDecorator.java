package at.fpmedv.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public abstract class StatementDecorator implements Statement {

	protected java.sql.Statement statement;
	
	public StatementDecorator(java.sql.Statement statement) {
		this.statement = statement;
	}
	
	public void addBatch(String arg0) throws SQLException {
		statement.addBatch(arg0);
	}

	public void cancel() throws SQLException {
		statement.cancel();
	}

	public void clearBatch() throws SQLException {
		statement.clearBatch();
	}

	public void clearWarnings() throws SQLException {
		statement.clearWarnings();
	}

	public void close() throws SQLException {
		statement.close();
	}

	public boolean execute(String arg0) throws SQLException {
		return statement.execute(arg0);
	}

	public boolean execute(String arg0, int arg1) throws SQLException {
		return statement.execute(arg0, arg1);
	}

	public boolean execute(String arg0, int[] arg1) throws SQLException {
		return statement.execute(arg0, arg1);
	}

	public boolean execute(String arg0, String[] arg1) throws SQLException {
		return statement.execute(arg0, arg1);
	}

	public int[] executeBatch() throws SQLException {
		return statement.executeBatch();
	}

	public ResultSet executeQuery(String arg0) throws SQLException {
		return statement.executeQuery(arg0);
	}

	public int executeUpdate(String arg0) throws SQLException {
		return statement.executeUpdate(arg0);
	}

	public int executeUpdate(String arg0, int arg1) throws SQLException {
		return statement.executeUpdate(arg0, arg1);
	}

	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		return statement.executeUpdate(arg0, arg1);
	}

	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		return statement.executeUpdate(arg0, arg1);
	}

	public Connection getConnection() throws SQLException {
		return statement.getConnection();
	}

	public int getFetchDirection() throws SQLException {
		return statement.getFetchDirection();
	}

	public int getFetchSize() throws SQLException {
		return statement.getFetchSize();
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		return statement.getGeneratedKeys();
	}

	public int getMaxFieldSize() throws SQLException {
		return statement.getMaxFieldSize();
	}

	public int getMaxRows() throws SQLException {
		return statement.getMaxRows();
	}

	public boolean getMoreResults() throws SQLException {
		return statement.getMoreResults();
	}

	public boolean getMoreResults(int arg0) throws SQLException {
		return statement.getMoreResults(arg0);
	}

	public int getQueryTimeout() throws SQLException {
		return statement.getQueryTimeout();
	}

	public ResultSet getResultSet() throws SQLException {
		return statement.getResultSet();
	}

	public int getResultSetConcurrency() throws SQLException {
		return statement.getResultSetConcurrency();
	}

	public int getResultSetHoldability() throws SQLException {
		return statement.getResultSetHoldability();
	}

	public int getResultSetType() throws SQLException {
		return statement.getResultSetType();
	}

	public int getUpdateCount() throws SQLException {
		return statement.getUpdateCount();
	}

	public SQLWarning getWarnings() throws SQLException {
		return statement.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return statement.isClosed();
	}

	public boolean isPoolable() throws SQLException {
		return statement.isPoolable();
	}

	public void setCursorName(String arg0) throws SQLException {
		statement.setCursorName(arg0);
	}

	public void setEscapeProcessing(boolean arg0) throws SQLException {
		statement.setEscapeProcessing(arg0);
	}

	public void setFetchDirection(int arg0) throws SQLException {
		statement.setFetchDirection(arg0);
	}

	public void setFetchSize(int arg0) throws SQLException {
		statement.setFetchSize(arg0);
	}

	public void setMaxFieldSize(int arg0) throws SQLException {
		statement.setMaxFieldSize(arg0);
	}

	public void setMaxRows(int arg0) throws SQLException {
		statement.setMaxRows(arg0);
	}

	public void setPoolable(boolean arg0) throws SQLException {
		statement.setPoolable(arg0);
	}

	public void setQueryTimeout(int arg0) throws SQLException {
		statement.setQueryTimeout(arg0);
	}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return statement.isWrapperFor(arg0);
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return statement.unwrap(arg0);
	}

}
