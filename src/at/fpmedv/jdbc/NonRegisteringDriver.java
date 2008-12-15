package at.fpmedv.jdbc;

import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Driver;

public class NonRegisteringDriver implements java.sql.Driver {

	private static final String WRAPPER_URL_PREFIX = "jdbcdecorator:";
	
	private Driver driver;

	protected static Class connectionWrapperClass;
	
	public NonRegisteringDriver() {

	}
	
	private String transformUrl(String url) {
		if (url.startsWith(WRAPPER_URL_PREFIX)) {
			url = url.substring(WRAPPER_URL_PREFIX.length());
		}
		return url;
	}

	private Driver getUnderlyingDriver()  {
		try {
			return getUnderlyingDriver(null);
		} catch (SQLException sqle) {}
		return null;
	}
	/**
	 * Given a <code>jdbc:log4</code> type URL, find the underlying real driver
	 * that accepts the URL.
	 *
	 * @param url JDBC connection URL.
	 *
	 * @return Underlying driver for the given URL. Null is returned if the URL is
	 *         not a <code>jdbc:log4</code> type URL or there is no underlying
	 *         driver that accepts the URL.
	 *
	 * @throws SQLException if a database access error occurs.
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
	 * start with jdbc:mysql:
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
	 */
	public boolean acceptsURL(String url) throws SQLException {
		// added check because we use in getUnderlyingDriver getDriver
		if (url.startsWith(WRAPPER_URL_PREFIX)) {
			return (getUnderlyingDriver(transformUrl(url)) != null);
		} else {
			return false;
		}
	}

	/**
	 * Try to make a database connection to the given URL. The driver should
	 * return "null" if it realizes it is the wrong kind of driver to connect to
	 * the given URL. This will be common, as when the JDBC driverManager is
	 * asked to connect to a given URL, it passes the URL to each loaded driver
	 * in turn.
	 * 
	 * <p>
	 * The driver should raise an SQLException if it is the right driver to
	 * connect to the given URL, but has trouble connecting to the database.
	 * </p>
	 * 
	 * <p>
	 * The java.util.Properties argument can be used to pass arbitrary string
	 * tag/value pairs as connection arguments.
	 * </p>
	 * 
	 * <p>
	 * My protocol takes the form:
	 * 
	 * <PRE>
	 * 
	 * jdbc:mysql://host:port/database
	 * 
	 * </PRE>
	 * 
	 * </p>
	 * 
	 * @param url
	 *            the URL of the database to connect to
	 * @param info
	 *            a list of arbitrary tag/value pairs as connection arguments
	 * 
	 * @return a connection to the URL or null if it isnt us
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
			if (connectionWrapperClass != null) {
				try {
//					 wrap the Connection
					ConnectionDecorator tmp = (ConnectionDecorator) connectionWrapperClass.newInstance();
					tmp.setUnderLyingConnection(con);
					return tmp;
				} catch (Exception e) {
					System.out.println("[jdbcdecorator] Could not instantiate class connectionWrapperClass: " + e);
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
