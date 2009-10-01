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
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;


/**
 * The Java SQL at.fpmedv.Driver allows to add more behavior to any existing
 * jdbc driver following the
 * <a href="http://en.wikipedia.org/wiki/Decorator_pattern">decorator pattern</a>.
 * 
 * <p>
 * When this Driver class is loaded with Class.forName("at.fpmedv.Driver"), it tries
 * to register well known Drivers:
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
 * If your Driver isn't listed here you can specify it as an commandline argument:
 * <p>
 * f.e.: <code>-Djdbcdecorator.drivers=your.Driver1,your.Driver2,your.Driver3,...</code>
 * <p>
 * It also tries to get the decorator Class for the Connection returned by Driver.connect(). The 
 * Class name must be specified by a System property:
 * <code>jdbcdecorator.connectionClassname</code>
 * 
 * @see NonRegisteringDriver
 * @see NonRegisteringDriver#connect(String, java.util.Properties)
 * @author Franz Philipp Moser
 */
public class Driver extends NonRegisteringDriver implements java.sql.Driver {
	
	//
	// Register ourselves with the DriverManager
	//
	static {
		String buildDate = "__builddate__";
		String version = "__version__";
		String configurationFileName = System.getProperty("jdbcdecorator.configuration");
		boolean preloadKnownDrivers = true;
		String connectionClassname = null;
		String moreDrivers = null;
		
		// try to load properties file and overwrite defaults
		try {
		// get Properties Filename
			Properties properties = new Properties();
			properties.load(new FileInputStream(configurationFileName));
			connectionClassname = properties.getProperty("jdbcdecorator.connectionClassname");
			preloadKnownDrivers = Boolean.parseBoolean(properties.getProperty("jdbcdecorator.preloadKnownDrivers"));
			moreDrivers = properties.getProperty("jdbcdecorator.drivers");
			if (connectionClassname != null) {
				// initialize ConnectionDecorator implementation
				try {
					NonRegisteringDriver.connectionClass = Class.forName(connectionClassname);
				} catch (ClassNotFoundException e) {
					System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "not initialized: " + e);
					e.printStackTrace();
				}
			} else {
				System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "system property jdbcdecorator.connectionClassname is null");
				System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "not initialized");
			}
		} catch (IOException e) {
			System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "error loading config file (" + configurationFileName + ": " + e);
		}
		try {
			java.sql.Driver driver2Register = new Driver();
			java.sql.DriverManager.registerDriver(driver2Register);
			System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "registered myself Version: " + version + " build on: " + buildDate);
			
			Set<String> subDrivers = new TreeSet<String>();

			// The Set of drivers that the jdbcwrapper driver will try
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

			// add drivers specified in configuration
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
		} catch (SQLException e) {
			throw new RuntimeException(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "Can't register myself!");
		}
	}

	// ~ Constructors
	// -----------------------------------------------------------

	/**
	 * Construct a new driver. This method is only needed for Class.getInstance()
	 * 
	 * @throws SQLException
	 *             if a database error occurs.
	 */
	public Driver() {

	}

}
