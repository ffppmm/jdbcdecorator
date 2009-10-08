package at.fpmedv.jdbc;


import java.sql.SQLException;

public class StatisticsDriver extends NonRegisteringDriver {
	
	//
	// Register ourselves with the DriverManager
	//
	static {
		String buildDate = "__builddate__";
		String version = "__version__";
		
		try {
			StatisticsDriver driver2Register = new StatisticsDriver();
			java.sql.DriverManager.registerDriver(driver2Register);
			driver2Register.initEnvironment();
			System.out.println(Statistics.JDBC_STATISTICS_LOGGING_PREFIX + "registered myself Version: " + version + " build on: " + buildDate);
		} catch (SQLException e) {
			throw new RuntimeException(Statistics.JDBC_STATISTICS_LOGGING_PREFIX + "Can't register myself!");
		}
		
	}
	
	@Override
	protected String getConnectionClassName() {
		return "at.fpmedv.jdbc.ConnectionStatistics";
	}

}
