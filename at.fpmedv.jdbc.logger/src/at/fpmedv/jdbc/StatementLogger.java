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

import java.sql.ResultSet;
import java.sql.SQLException;

import at.fpmedv.util.Stopwatch;

/**
 * 
 * Adds logging functionality to the underlying Statement
 * 
 * @author Franz Philipp Moser
 *
 */
public class StatementLogger extends StatementDecorator {
	
	public StatementLogger(java.sql.Statement statement) {
		super(statement);
	}
	
	/**
	 * Calls executeQuery() on underlying Statement object, messures execution time, 
	 * records and logs the statistics.
	 * 
	 * @param sql the sql to execute
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		ResultSet returnValue = null;
		SQLException mayBe = null;
		try {
			returnValue = statement.executeQuery(sql);
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}
	
	/**
	 * Calls executeUpdate() on underlying Statement object, messures execution time, 
	 * records and logs the statistics.
	 * 
	 * @param sql the sql to execute
	 */
	public int executeUpdate(String sql) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		int returnValue = 0;
		SQLException mayBe = null;
		try {
			returnValue = statement.executeUpdate(sql);
		} catch (SQLException error) {
			mayBe = error;
		}
		sw.stop();
		Logger.getInstance().recordStatisticsAndLog(sql, sw.getElapsedTimeMillis(), mayBe);
		if (mayBe != null)
			throw mayBe;
		return returnValue;
	}
	
	/**
	 * Calls execute() on underlying Statement object, messures execution time, 
	 * records and logs the statistics.
	 * 
	 * @param sql the sql to execute
	 */
	public boolean execute(String sql) throws SQLException {
		Stopwatch sw = new Stopwatch();
		sw.start();
		boolean returnValue = false;
		SQLException mayBe = null;
		try {
			returnValue = statement.execute(sql);
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
