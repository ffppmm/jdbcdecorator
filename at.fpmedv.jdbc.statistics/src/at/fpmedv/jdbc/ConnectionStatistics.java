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
import java.sql.SQLException;

/**
 * 
 * Entrypoint for the our statistics system.
 * 
 * @author Franz Philipp Moser
 *
 */
public class ConnectionStatistics extends ConnectionDecorator {
	
	/**
	 * decorates a Statement object created by the underlying connection 
	 * with a StatementLogger.
	 * 
	 * @return Statement decorated with StatementLogger
	 */
	public java.sql.Statement createStatement() throws SQLException {
		return new StatementStatistics(connection.createStatement());
	}

	/**
	 * decorates a PreparedStatement object created by the underlying connection 
	 * with a PreparedStatementLogger.
	 * 
	 * @return PreparedStatement decorated with PreparedStatementLogger
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return new PreparedStatementStatistics(connection.prepareStatement(sql), sql);
	}

}