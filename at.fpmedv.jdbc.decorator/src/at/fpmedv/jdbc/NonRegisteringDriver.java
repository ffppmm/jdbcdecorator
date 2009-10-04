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

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.sql.Driver;

/**
 * This is the actual implementation of the java.sql.Driver
 * 
 * @see Driver
 */
public abstract class NonRegisteringDriver implements java.sql.Driver {

	/**
	 * stores the prefix for the jdbc URL that identifies a jdbcdecorator url
	 */
	protected static final String DECORATOR_URL_PREFIX = "jdbcdecorator:";
	
	/**
	 * stores the prefix for the jdbcdecorator output logging
	 */
	protected static final String DECORATOR_LOGGING_PREFIX = "[jdbcdecorator] ";

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
	 * the Name of the properties file with the configuration
	 */
	private String configurationFileName = System.getProperty("jdbcdecorator.configuration");
	
	/**
	 * if standard jdbc drivers should be loaded, like mysql ...
	 */
	private boolean preloadKnownDrivers = true;
	
	/**
	 * The name of the connection class
	 */
	private String connectionClassname = null;

	/**
	 * storage for the configuration properties
	 */
	private Properties properties = new Properties();

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
	 * <p>
	 * This function tries to read the config properties, if specified as system 
	 * property -Djdbcdecorator.configuration=/path/2/properties
	 * <p>
	 * It will try to register well known Drivers:
	 * <ul>
	 *    <li>com.mysql.jdbc.Driver</li>
	 *    <li>org.postgresql.Driver</li>
	 *    <li>oracle.jdbc.driver.OracleDriver</li>
	 *    <li>com.sybase.jdbc2.jdbc.SybDriver</li>
	 *    <li>net.sourceforge.jtds.jdbc.Driver</li>
	 *    <li>com.microsoft.jdbc.sqlserver.SQLServerDriver</li>
	 *    <li>com.microsoft.sqlserver.jdbc.SQLServerDriver</li>
	 *    <li>weblogic.jdbc.sqlserver.SQLServerDriver</li>
	 *    <li>com.informix.jdbc.IfxDriver</li>
	 *    <li>org.apache.derby.jdbc.ClientDrive</li>
	 *    <li>org.apache.derby.jdbc.EmbeddedDriver</li>
	 *    <li>org.hsqldb.jdbcDrive</li>
	 *    <li>org.h2.Driver</li>
	 * </ul>
	 * by calling Class.forName(DRIVERNAME).
	 * <p>
	 * It will also preload Drivers specified in the properties file
	 */
	
	protected void initEnvironment() {
		// try to load properties file and overwrite defaults
		String moreDrivers = null;
		connectionClassname = getConnectionClassName(); 

		if (configurationFileName != null) {
			try {
				// get Properties Filename
				properties.load(new FileInputStream(configurationFileName));
				preloadKnownDrivers = Boolean.parseBoolean(properties.getProperty("jdbcdecorator.preloadKnownDrivers"));
				moreDrivers = properties.getProperty("jdbcdecorator.drivers");
			} catch (IOException e) {
				System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "error loading config file (" + configurationFileName + ": " + e);
			}
		}
		
		if (connectionClassname != null) {
			// initialize ConnectionDecorator implementation
			try {
				NonRegisteringDriver.connectionClass = Class.forName(connectionClassname);
			} catch (ClassNotFoundException e) {
				System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "not initialized: " + e);
				e.printStackTrace();
			}
		} else {
			System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "system / config file property jdbcdecorator.connectionClassname is null");
		}
		
		Set<String> subDrivers = new TreeSet<String>();

		// The Set of drivers that the jdbcwrapper driver will try to
		// preload at instantiation time if preloadKnownDrivers is enabled
		if (preloadKnownDrivers) {
			subDrivers.add("com.mysql.jdbc.Driver");
			subDrivers.add("org.postgresql.Driver");

			subDrivers.add("oracle.jdbc.driver.OracleDriver");
			subDrivers.add("com.sybase.jdbc2.jdbc.SybDriver");
			subDrivers.add("net.sourceforge.jtds.jdbc.Driver");

			// MS driver for Sql Server 2000
			subDrivers.add("com.microsoft.jdbc.sqlserver.SQLServerDriver");

			// MS driver for Sql Server 2005
			subDrivers.add("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			subDrivers.add("weblogic.jdbc.sqlserver.SQLServerDriver");
			subDrivers.add("com.informix.jdbc.IfxDriver");
			subDrivers.add("org.apache.derby.jdbc.ClientDriver");
			subDrivers.add("org.apache.derby.jdbc.EmbeddedDriver");
			subDrivers.add("org.hsqldb.jdbcDriver");
			subDrivers.add("org.h2.Driver");
		}

		// collect drivers specified in configuration
		if (moreDrivers != null) {
			String[] moreDriversArr = moreDrivers.split(",");
			for (int i = 0; i < moreDriversArr.length; i++) {
				subDrivers.add(moreDriversArr[i]);
			}
		}
		
		// register subdrivers
		String driverClassName;
		for (Iterator<String> i = subDrivers.iterator(); i.hasNext();) {
			driverClassName = i.next();
			try {
				Class.forName(driverClassName).newInstance();
				System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "found driver: " + driverClassName);
			} catch (ClassNotFoundException e) {
				// do nothing because we just try to find the correct driver					
			} catch (InstantiationException e) {
				// may be a problem
				System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "InstantiationException of " + driverClassName);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// may be a problem
				System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "IllegalAccessException of " + driverClassName);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Every Decorator must implement this function for providing the Connection
	 * Class implementation of the {@link ConnectionDecorator}
	 * 
	 * 
	 * @return name of Connection Class
	 */
	protected abstract String getConnectionClassName();
	
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
				} catch (InstantiationException e) {
					System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "InstantiationException while instantiating class: " + connectionClass);
					e.printStackTrace();
					return con;
				} catch (IllegalAccessException e) {
					System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "IllegalAccessException while instantiating class: " + connectionClass);
					e.printStackTrace();
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
