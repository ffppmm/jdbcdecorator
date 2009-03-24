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

import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Driver;

/**
 * This is the actual implementation of the java.sql.Driver
 * 
 * @see Driver
 */
public class NonRegisteringDriver implements java.sql.Driver {

	/**
	 * stores the prefix for the jdbc URL that identifies a jdbcdecorator url
	 */
	private static final String DECORATOR_URL_PREFIX = "jdbcdecorator:";
	
	/**
	 * the underlying driver that is decorated
	 */
	private Driver driver;

	/**
	 * the Class of the Connection returned by connect()
	 * 
	 * @see ConnectionDecorator
	 * @see #connect(String, Properties)
	 */
	protected static Class connectionClass;
	
	/**
	 * Construct a new driver. This method is only needed for Class.getInstance()
	 * 
	 * @throws SQLException
	 *             if a database error occurs.
	 */
	public NonRegisteringDriver() {

	}
	
	/**
	 * Transforms the given URL by removing the <code>WRAPPER_URL_PREFIX</code> prefix if needed
	 * 
	 * @param url the URL to transform
	 * 
	 * @see #DECORATOR_URL_PREFIX
	 */
	private String transformUrl(String url) {
		if (url.startsWith(DECORATOR_URL_PREFIX)) {
			url = url.substring(DECORATOR_URL_PREFIX.length());
		}
		return url;
	}

	/**
	 *  Tries to get the underlying Driver object
	 *  without initalising it.
	 *  
	 * @return the underlying Driver object that is decorated
	 */
	private Driver getUnderlyingDriver()  {
		try {
			return getUnderlyingDriver(null);
		} catch (SQLException sqle) {}
		return null;
	}
	
	/**
	 * Given a <code>WRAPPER_URL_PREFIX</code> type URL, find the underlying driver
	 * that accepts the URL.
	 *
	 * @param url JDBC connection URL.
	 *
	 * @return Underlying driver for the given URL. Null is returned if the URL is
	 *         not a <code>WRAPPER_URL_PREFIX</code> type URL or there is no underlying
	 *         driver that accepts the URL.
	 *
	 * @throws SQLException if a database access error occurs.
	 * 
	 * @see #DECORATOR_URL_PREFIX
	 */
	private Driver getUnderlyingDriver(String url) throws SQLException {
		if (driver != null)
			return driver;
		if (driver == null && url != null) {
			Driver d = DriverManager.getDriver(url);
			if (d.acceptsURL(url)) {
				driver = d;
				return driver;
			}
		}
		return null;
	}

	/**
	 * Typically, drivers will return true if they understand the subprotocol
	 * specified in the URL and false if they don't. This driver's protocols
	 * start with <code>WRAPPER_URL_PREFIX</code>
	 * 
	 * @param url
	 *            the URL of the driver
	 * 
	 * @return true if this driver accepts the given URL
	 * 
	 * @exception SQLException
	 *                if a database-access error occurs
	 * 
	 * @see java.sql.Driver#acceptsURL
	 * @see #DECORATOR_URL_PREFIX
	 */
	public boolean acceptsURL(String url) throws SQLException {
		// added check because we use in getUnderlyingDriver getDriver
		if (url.startsWith(DECORATOR_URL_PREFIX)) {
			return (getUnderlyingDriver(transformUrl(url)) != null);
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Tries to connect with a given URL to the database. 
	 * 
	 * @param url
	 *            the URL of the database to connect to
	 * @param info
	 *            a list of arbitrary tag/value pairs as connection arguments
	 * 
	 * @return a decorated connection to the URL or null if it isnt us
	 * 
	 * @exception SQLException
	 *                if a database access error occurs
	 * 
	 * @see java.sql.Driver#connect
	 */
	public java.sql.Connection connect(String url, Properties info)
			throws SQLException {
		if (getUnderlyingDriver(transformUrl(url)) != null) {
			java.sql.Connection con = driver.connect(transformUrl(url), info);
			if (connectionClass != null) {
				try {
//					 wrap the Connection
					ConnectionDecorator underlyingConnection = (ConnectionDecorator) connectionClass.newInstance();
					underlyingConnection.setUnderLyingConnection(con);
					return underlyingConnection;
				} catch (Exception e) {
					System.out.println("[jdbcdecorator] Could not instantiate class connectionClass: " + e);
					return con;
				}
			} else {
				return con;
			}
		}
		return null;
	}
	
	public int getMajorVersion() {
		if (getUnderlyingDriver() != null) {
			return driver.getMajorVersion();
		} else {
			return 1;
		}
	}

	public int getMinorVersion() {
		if (getUnderlyingDriver() != null) {
			return driver.getMinorVersion();
		} else {
			return 0;
		}
	}

	public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1) throws SQLException {
		if (getUnderlyingDriver() != null) {
			return driver.getPropertyInfo(arg0, arg1);
		} else {
			return null;
		}
	}

	public boolean jdbcCompliant() {
		if (getUnderlyingDriver() != null) {
			return driver.jdbcCompliant();
		} else {
			return false;
		}
		
	}
}
