/*
 Copyright (C) fpmedv.at

 This program is free software; you can redistribute it and/or modify
 it under the terms of version 2 of the GNU General Public License as
 published by the Free Software Foundation.

 There are special exceptions to the terms and conditions of the GPL
 as it is applied to this software. View the full text of the
 exception in file EXCEPTIONS-CONNECTOR-J in the directory of this
 software distribution.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package at.fpmedv.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * PreparedStatementDecorator class wraps around an existing PreparedStatement
 * implementation and all calls are made against the underlying Object.
 * 
 * Following the <a href="http://en.wikipedia.org/wiki/Decorator_pattern">decorator pattern</a>
 * you can add extra functionality by extending this abstract class.
 * 
 * @see java.sql.PreparedStatement
 * @author Franz Philipp Moser
 *
 */
public abstract class PreparedStatementDecorator implements PreparedStatement {

	/**
	 * The underlying PreparedStatement object that is decorated
	 */
	protected PreparedStatement preparedstatement;
	
	/**
	 * Initialisation of the PreparedStatementDecorator
	 * 
	 * @param preparedstatement the underlying PreparedStatement Object that should
	 * be decorated.
	 */
	public PreparedStatementDecorator(java.sql.PreparedStatement preparedstatement) {
		this.preparedstatement = preparedstatement;
	}
	
	public void addBatch() throws SQLException {
		preparedstatement.addBatch();

	}

	public void clearParameters() throws SQLException {
		preparedstatement.clearParameters();

	}

	public boolean execute() throws SQLException {
		return preparedstatement.execute();
	}

	public ResultSet executeQuery() throws SQLException {
		return preparedstatement.executeQuery();
	}

	public int executeUpdate() throws SQLException {
		return preparedstatement.executeUpdate();
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return preparedstatement.getMetaData();
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		return preparedstatement.getParameterMetaData();
	}

	public void setArray(int arg0, Array arg1) throws SQLException {
		preparedstatement.setArray(arg0, arg1);

	}

	public void setAsciiStream(int arg0, InputStream arg1) throws SQLException {
		preparedstatement.setAsciiStream(arg0, arg1);
	}

	public void setAsciiStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		preparedstatement.setAsciiStream(arg0, arg1, arg2);

	}

	public void setAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		preparedstatement.setAsciiStream(arg0, arg1, arg2);

	}

	public void setBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		preparedstatement.setBigDecimal(arg0, arg1);

	}

	public void setBinaryStream(int arg0, InputStream arg1) throws SQLException {
		preparedstatement.setBinaryStream(arg0, arg1);

	}

	public void setBinaryStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		preparedstatement.setBinaryStream(arg0, arg1, arg2);

	}

	public void setBinaryStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		preparedstatement.setBinaryStream(arg0, arg1, arg2);

	}

	public void setBlob(int arg0, Blob arg1) throws SQLException {
		preparedstatement.setBlob(arg0, arg1);

	}

	public void setBlob(int arg0, InputStream arg1) throws SQLException {
		preparedstatement.setBlob(arg0, arg1);

	}

	public void setBlob(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		preparedstatement.setBlob(arg0, arg1, arg2);

	}

	public void setBoolean(int arg0, boolean arg1) throws SQLException {
		preparedstatement.setBoolean(arg0, arg1);

	}

	public void setByte(int arg0, byte arg1) throws SQLException {
		preparedstatement.setByte(arg0, arg1);

	}

	public void setBytes(int arg0, byte[] arg1) throws SQLException {
		preparedstatement.setBytes(arg0, arg1);

	}

	public void setCharacterStream(int arg0, Reader arg1) throws SQLException {
		preparedstatement.setCharacterStream(arg0, arg1);

	}

	public void setCharacterStream(int arg0, Reader arg1, int arg2)
			throws SQLException {
		preparedstatement.setCharacterStream(arg0, arg1, arg2);

	}

	public void setCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		preparedstatement.setCharacterStream(arg0, arg1, arg2);

	}

	public void setClob(int arg0, Clob arg1) throws SQLException {
		preparedstatement.setClob(arg0, arg1);

	}

	public void setClob(int arg0, Reader arg1) throws SQLException {
		preparedstatement.setClob(arg0, arg1);

	}

	public void setClob(int arg0, Reader arg1, long arg2) throws SQLException {
		preparedstatement.setClob(arg0, arg1, arg2);

	}

	public void setDate(int arg0, Date arg1) throws SQLException {
		preparedstatement.setDate(arg0, arg1);

	}

	public void setDate(int arg0, Date arg1, Calendar arg2) throws SQLException {
		preparedstatement.setDate(arg0, arg1, arg2);

	}

	public void setDouble(int arg0, double arg1) throws SQLException {
		preparedstatement.setDouble(arg0, arg1);

	}

	public void setFloat(int arg0, float arg1) throws SQLException {
		preparedstatement.setFloat(arg0, arg1);

	}

	public void setInt(int arg0, int arg1) throws SQLException {
		preparedstatement.setInt(arg0, arg1);

	}

	public void setLong(int arg0, long arg1) throws SQLException {
		preparedstatement.setLong(arg0, arg1);

	}

	public void setNCharacterStream(int arg0, Reader arg1) throws SQLException {
		preparedstatement.setNCharacterStream(arg0, arg1);

	}

	public void setNCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		preparedstatement.setNCharacterStream(arg0, arg1, arg2);

	}

	public void setNClob(int arg0, NClob arg1) throws SQLException {
		preparedstatement.setNClob(arg0, arg1);

	}

	public void setNClob(int arg0, Reader arg1) throws SQLException {
		preparedstatement.setNClob(arg0, arg1);

	}

	public void setNClob(int arg0, Reader arg1, long arg2) throws SQLException {
		preparedstatement.setNClob(arg0, arg1, arg2);

	}

	public void setNString(int arg0, String arg1) throws SQLException {
		preparedstatement.setNString(arg0, arg1);

	}

	public void setNull(int arg0, int arg1) throws SQLException {
		preparedstatement.setNull(arg0, arg1);

	}

	public void setNull(int arg0, int arg1, String arg2) throws SQLException {
		preparedstatement.setNull(arg0, arg1, arg2);

	}

	public void setObject(int arg0, Object arg1) throws SQLException {
		preparedstatement.setObject(arg0, arg1);

	}

	public void setObject(int arg0, Object arg1, int arg2) throws SQLException {
		preparedstatement.setObject(arg0, arg1, arg2);

	}

	public void setObject(int arg0, Object arg1, int arg2, int arg3)
			throws SQLException {
		preparedstatement.setObject(arg0, arg1, arg2, arg3);

	}

	public void setRef(int arg0, Ref arg1) throws SQLException {
		preparedstatement.setRef(arg0, arg1);

	}

	public void setRowId(int arg0, RowId arg1) throws SQLException {
		preparedstatement.setRowId(arg0, arg1);

	}

	public void setSQLXML(int arg0, SQLXML arg1) throws SQLException {
		preparedstatement.setSQLXML(arg0, arg1);

	}

	public void setShort(int arg0, short arg1) throws SQLException {
		preparedstatement.setShort(arg0, arg1);

	}

	public void setString(int arg0, String arg1) throws SQLException {
		preparedstatement.setString(arg0, arg1);

	}

	public void setTime(int arg0, Time arg1) throws SQLException {
		preparedstatement.setTime(arg0, arg1);

	}

	public void setTime(int arg0, Time arg1, Calendar arg2) throws SQLException {
		preparedstatement.setTime(arg0, arg1, arg2);

	}

	public void setTimestamp(int arg0, Timestamp arg1) throws SQLException {
		preparedstatement.setTimestamp(arg0, arg1);

	}

	public void setTimestamp(int arg0, Timestamp arg1, Calendar arg2)
			throws SQLException {
		preparedstatement.setTimestamp(arg0, arg1, arg2);

	}

	public void setURL(int arg0, URL arg1) throws SQLException {
		preparedstatement.setURL(arg0, arg1);

	}

	public void setUnicodeStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		preparedstatement.setUnicodeStream(arg0, arg1, arg2);
	}

	public void addBatch(String arg0) throws SQLException {
		preparedstatement.addBatch(arg0);

	}

	public void cancel() throws SQLException {
		preparedstatement.cancel();

	}

	public void clearBatch() throws SQLException {
		preparedstatement.clearBatch();

	}

	public void clearWarnings() throws SQLException {
		preparedstatement.clearWarnings();

	}

	public void close() throws SQLException {
		preparedstatement.close();

	}

	public boolean execute(String arg0) throws SQLException {
		return preparedstatement.execute(arg0);
	}

	public boolean execute(String arg0, int arg1) throws SQLException {
		return preparedstatement.execute(arg0, arg1);
	}

	public boolean execute(String arg0, int[] arg1) throws SQLException {
		return preparedstatement.execute(arg0, arg1);
	}

	public boolean execute(String arg0, String[] arg1) throws SQLException {
		return preparedstatement.execute(arg0, arg1);
	}

	public int[] executeBatch() throws SQLException {
		return preparedstatement.executeBatch();
	}

	public ResultSet executeQuery(String arg0) throws SQLException {
		return preparedstatement.executeQuery(arg0);
	}

	public int executeUpdate(String arg0) throws SQLException {
		return preparedstatement.executeUpdate(arg0);
	}

	public int executeUpdate(String arg0, int arg1) throws SQLException {
		return preparedstatement.executeUpdate(arg0, arg1);
	}

	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		return preparedstatement.executeUpdate(arg0, arg1);
	}

	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		return preparedstatement.executeUpdate(arg0, arg1);
	}

	public Connection getConnection() throws SQLException {
		return preparedstatement.getConnection();
	}

	public int getFetchDirection() throws SQLException {
		return preparedstatement.getFetchDirection();
	}

	public int getFetchSize() throws SQLException {
		return preparedstatement.getFetchSize();
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		return preparedstatement.getGeneratedKeys();
	}

	public int getMaxFieldSize() throws SQLException {
		return preparedstatement.getMaxFieldSize();
	}

	public int getMaxRows() throws SQLException {
		return preparedstatement.getMaxRows();
	}

	public boolean getMoreResults() throws SQLException {
		return preparedstatement.getMoreResults();
	}

	public boolean getMoreResults(int arg0) throws SQLException {
		return preparedstatement.getMoreResults(arg0);
	}

	public int getQueryTimeout() throws SQLException {
		return preparedstatement.getQueryTimeout();
	}

	public ResultSet getResultSet() throws SQLException {
		return preparedstatement.getResultSet();
	}

	public int getResultSetConcurrency() throws SQLException {
		return preparedstatement.getResultSetConcurrency();
	}

	public int getResultSetHoldability() throws SQLException {
		return preparedstatement.getResultSetHoldability();
	}

	public int getResultSetType() throws SQLException {
		return preparedstatement.getResultSetType();
	}

	public int getUpdateCount() throws SQLException {
		return preparedstatement.getUpdateCount();
	}

	public SQLWarning getWarnings() throws SQLException {
		return preparedstatement.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return preparedstatement.isClosed();
	}

	public boolean isPoolable() throws SQLException {
		return preparedstatement.isPoolable();
	}

	public void setCursorName(String arg0) throws SQLException {
		preparedstatement.setCursorName(arg0);
	}

	public void setEscapeProcessing(boolean arg0) throws SQLException {
		preparedstatement.setEscapeProcessing(arg0);
	}

	public void setFetchDirection(int arg0) throws SQLException {
		preparedstatement.setFetchDirection(arg0);
	}

	public void setFetchSize(int arg0) throws SQLException {
		preparedstatement.setFetchSize(arg0);
	}

	public void setMaxFieldSize(int arg0) throws SQLException {
		preparedstatement.setMaxFieldSize(arg0);
	}

	public void setMaxRows(int arg0) throws SQLException {
		preparedstatement.setMaxRows(arg0);
	}

	public void setPoolable(boolean arg0) throws SQLException {
		preparedstatement.setPoolable(arg0);
	}

	public void setQueryTimeout(int arg0) throws SQLException {
		preparedstatement.setQueryTimeout(arg0);
	}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return preparedstatement.isWrapperFor(arg0);
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return preparedstatement.unwrap(arg0);
	}

}
