package at.fpmedv.jdbc;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;



public class Driver extends NonRegisteringDriver implements java.sql.Driver {
	// ~ Static fields/initializers
	// ---------------------------------------------

	//
	// Register ourselves with the DriverManager
	//
	static {
		try {
			String connectionWrapperClassname = System.getProperty("jdbcdecorator.connectionWrapperClassname");
			if (connectionWrapperClassname != null) {
				try {
					NonRegisteringDriver.connectionWrapperClass = Class.forName(connectionWrapperClassname);
				} catch (ClassNotFoundException e) {
					System.out.println("[jdbcdecorator] not initialized: " + e);
					e.printStackTrace();
				}
			} else {
				System.out.println("[jdbcdecorator] system property jdbcdecorator.connectionWrapperClassname is null");
				System.out.println("[jdbcdecorator] not initialized");
			}
			java.sql.Driver driver2Register = new Driver();
			java.sql.DriverManager.registerDriver(driver2Register);
			System.out.println("[jdbcdecorator] registered myself");
			
			// The Set of drivers that the jdbcwrapper driver will try
			// preload at instantiation time.
			Set<String> subDrivers = new TreeSet<String>();
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

			// look for additional driver specified in system properties
			String moreDrivers = System.getProperty("jdbcdecorator.drivers");

			if (moreDrivers != null) {
				String[] moreDriversArr = moreDrivers.split(",");
				for (int i = 0; i < moreDriversArr.length; i++) {
					subDrivers.add(moreDriversArr[i]);
				}
			}
			String driverClass;
			for (Iterator i = subDrivers.iterator(); i.hasNext();) {
				driverClass = (String) i.next();
				try {
					Class.forName(driverClass).newInstance();
					System.out.println("[jdbcdecorator] found driver: " + driverClass);
				} catch (Throwable c) {}
			}			
		} catch (SQLException E) {
			throw new RuntimeException("[jdbcdecorator] Can't register myself!");
		}
	}

	// ~ Constructors
	// -----------------------------------------------------------

	/**
	 * Construct a new driver and register it with DriverManager
	 * @param name 
	 * 
	 * @throws SQLException
	 *             if a database error occurs.
	 */
	public Driver() {

	}

}
