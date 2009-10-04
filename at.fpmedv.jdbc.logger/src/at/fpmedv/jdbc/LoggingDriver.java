package at.fpmedv.jdbc;

import java.sql.SQLException;

public class LoggingDriver extends NonRegisteringDriver {
	
	//
	// Register ourselves with the DriverManager
	//
	static {
		String buildDate = "__builddate__";
		String version = "__version__";
		
		try {
			LoggingDriver driver2Register = new LoggingDriver();
			java.sql.DriverManager.registerDriver(driver2Register);
			driver2Register.initEnvironment();
			System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "registered myself Version: " + version + " build on: " + buildDate);
		} catch (SQLException e) {
			throw new RuntimeException(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "Can't register myself!");
		}
		
	}
	
	@Override
	protected String getConnectionClassName() {
		System.out.println(NonRegisteringDriver.DECORATOR_LOGGING_PREFIX + "Returning Connection Class Name");
		return "at.fpmedv.jdbc.ConnectionLogger";
	}

}
