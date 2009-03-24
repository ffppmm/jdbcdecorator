/*
 Copyright (C) 2009 fpmedv.at

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import at.fpmedv.util.Stopwatch;

/**
 * 
 * Adds logging functionality to the underlying PreparedStatement
 * 
 * @author Franz Philipp Moser
 *
 */
public class PreparedStatementLogger extends PreparedStatementDecorator {

	/**
	 * constructor storing the sql to execute
	 * 
	 * @param preparedstatement the prepared statement that is decorated
	 * @param sql The sql String to execute
	 */
	public PreparedStatementLogger(PreparedStatement preparedstatement, String sql) {
		super(preparedstatement, sql);
	}

	/**
	 * Calls executeUpdate() on underlying PreparedStatement object, messures execution time, 
	 * records and logs the statistics.
	 */
	public int executeUpdate() throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		int returnValue = 0;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.executeUpdate();
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}
	
	@Override
	public boolean execute() throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		boolean returnValue = false;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.execute();
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}

	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		boolean returnValue = false;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.execute(arg0, arg1);
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		boolean returnValue = false;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.execute(arg0, arg1);
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		boolean returnValue = false;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.execute(arg0, arg1);
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}

	@Override
	public boolean execute(String arg0) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		boolean returnValue = false;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.execute(arg0);
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		ResultSet returnValue = null;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.executeQuery();
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}

	@Override
	public ResultSet executeQuery(String arg0) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		ResultSet returnValue = null;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.executeQuery(arg0);
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		int returnValue = 0;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.executeUpdate(arg0, arg1);
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}

	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		int returnValue = 0;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.executeUpdate(arg0, arg1);
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		int returnValue = 0;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.executeUpdate(arg0, arg1);
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}

	@Override
	public int executeUpdate(String arg0) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		int returnValue = 0;
		SQLException mayBe = null;
		try {
			returnValue = preparedstatement.executeUpdate(arg0);
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}

}
