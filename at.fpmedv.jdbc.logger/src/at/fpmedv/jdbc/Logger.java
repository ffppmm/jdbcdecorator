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

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.PatternSyntaxException;

import at.fpmedv.jdbc.logging.LoggerDelegator;
import at.fpmedv.jdbc.logging.LoggerFactory;
import at.fpmedv.util.Stopwatch;
import at.fpmedv.util.regexp.RegExpReplacementContainer;

/**
 * 
 * Does all the logging stuff and stores individual statistics about SQL statements
 * 
 * @author Franz Philipp Moser
 *
 */
public class Logger {

	/**
	 * The day when this file was compiled
	 */
	public static final String BUILD_DATE = "__builddate__";
	
	/**
	 * the current version build
	 */
	public static final String VERSION = "__version__";
	
	/**
	 * the prefix (namespace) for config and system properties
	 */
	public static final String JDBC_LOGGER_PREFIX = "jdbclogger.";
	
	/**
	 * the Singelton of the Statistics Object
	 */
	private volatile static Logger uniqueInstance;

	/**
	 * if logging is enabled or not
	 */
	private boolean loggingEnabled = true;

	/**
	 * how many sql statements to store internaly
	 */
	private int maxStatements = 400;

	/**
	 * the filepath to the Statistic properties file, read from a java system property
	 */
	public static final String CONFIGURATION_FILE_NAME = System.getProperty(Logger.JDBC_LOGGER_PREFIX + "configuration");

	/**
	 * the properties of the Statistic object
	 */
	private Properties properties = new Properties();

	/**
	 * counter for update, select, insert statements
	 */
	private volatile long[] count = {0L,0L,0L,0L,0L};

	/**
	 * counter for execution time for update, select, insert statements
	 */
	private volatile long[] millis = {0L,0L,0L,0L,0L};

	/**
	 * counter for errors for update, select, insert statements
	 */
	private volatile long errorCount = 0L;
	
	/**
	 * if the full stacktrace should be logged or not
	 */
	private boolean displayFullStackTrace = false;

	/**
	 * contains StatementStatistics objects for each statement passing through
	 */
	private volatile HashMap<String, StatementCharacteristics> statementStatistics = new HashMap<String, StatementCharacteristics>();

	/**
	 * represents the number of Statements that are not stored in {@link #statementStatistics}
	 */
	private volatile long statementStatisticsOverflow = 0L;
	
	/**
	 * logger for all statement categories
	 */
	private LoggerDelegator[] logger = {LoggerFactory.getLogger(Logger.class.getName() + ".default"),
			LoggerFactory.getLogger(Logger.class.getName() + ".select"),
			LoggerFactory.getLogger(Logger.class.getName() + ".update"),
			LoggerFactory.getLogger(Logger.class.getName() + ".insert"),
			LoggerFactory.getLogger(Logger.class.getName() + ".delete"),};

	/**
	 * index for default
	 * 
	 * @see #count
	 * @see #millis
	 * @see #logger
	 * 
	 */
	public static final int DEFAULT = 0;

	/**
	 * index for select statements
	 * 
	 * @see #count
	 * @see #millis
	 * @see #logger
	 * 
	 */
	public static final int SELECT = 1;

	/**
	 * index for update statements
	 * 
	 * @see #count
	 * @see #millis
	 * @see #logger
	 * 
	 */
	public static final int UPDATE = 2;

	/**
	 * index for insert statements
	 * 
	 * @see #count
	 * @see #millis
	 * @see #logger
	 * 
	 */
	public static final int INSERT = 3;

	/**
	 * index for delete statements
	 * 
	 * @see #count
	 * @see #millis
	 * @see #logger
	 * 
	 */
	public static final int DELETE = 4;

	/**
	 * logging has started by this date
	 */
	private volatile Date loggingStarted = new Date();
	
	/**
	 * Holds the patterns and replacement Strings for sql statment normalization
	 * 
	 * @see #recordStatistics(String, long, int)
	 */
	private ArrayList<RegExpReplacementContainer> replacePatterns = new ArrayList<RegExpReplacementContainer>();

	private Date dateLastRendered;

	private long dateLastRenderedTime = 0L;

	/**
	 * opens properties file and reads configuration values
	 */
	private Logger() {
		init();
	}

	/**
	 * Singelton method for getting Statistics object
	 * 
	 * @return the Statistics object
	 */
	public static Logger getInstance() {
		if (uniqueInstance == null) {
			synchronized (Logger.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new Logger();
				}
			}
		}
		return uniqueInstance;
	}

	/**
	 * wrapper for recordStatistics and log
	 * 
	 * @param sql the sql that was executed
	 * @param millis execution time
	 * @param exception an exception object if something went wrong
	 */
	public void recordStatisticsAndLog(String sql, long millis, Throwable exception) {
		int type = getType(sql);
		recordStatistics(sql, millis, type);
		log(sql, millis, exception, type);
	}

	/**
	 * try to find out if UPDATE, INSERT or SELECT statement and returns the equvivalent
	 * internal type representation
	 * 
	 * @param sql the sql to operate on
	 * 
	 * @return the internal type
	 */
	private int getType(String sql) {
		int type = Logger.DEFAULT;
		String sqlQuery = sql.toLowerCase().trim();
		if (sqlQuery.startsWith("select")) {
			type = Logger.SELECT;
		} else if (sqlQuery.startsWith("update")) {
			type = Logger.UPDATE;
		} else if (sqlQuery.startsWith("insert")) {
			type = Logger.INSERT;
		} else if (sqlQuery.startsWith("delete")) {
			type = Logger.DELETE;
		}
		return type;
	}

	/**
	 * wrapper for private log so nothing can go wrong with the indexing
	 * of the internal arrays
	 * 
	 * @param sql the sql that was executed
	 * @param millis execution time
	 * @param exception an exception object if something went wrong
	 */
	public void log(String sql, long millis, Throwable exception) {
		int type = getType(sql);
		log(sql, millis, exception, type);
	}

	/**
	 * log the given arguments to the logger specified by type 
	 * 
	 * @param sql the sql that was executed
	 * @param millis execution time
	 * @param exception an exception object if something went wrong
	 * @param type internal type representation
	 */
	private void log(String sql, long millis, Throwable exception, int type) {
		if (isLoggingEnabled())
			logger[type].info(millis + " - " + sql, exception);		
	}

	/**
	 * wrapper for private recordStatistics so nothing can go wrong with the indexing
	 * of the internal arrays
	 *
	 * @param sql the sql that was executed
	 * @param millis execution time
	 */
	public void recordStatistics(String sql, long millis) {
		int type = getType(sql);

		recordStatistics(sql, millis, type);
	}

	/**
	 * stores the given arguments, applies the {@link #replacePatterns} to the sql statement
	 * 
	 * @param sql the sql that was executed
	 * @param millis execution time
	 * @param type internal type representation
	 */
	private void recordStatistics(String sql, long millis, int type) {
		Stopwatch sw = new Stopwatch();
		sw.start();
		this.millis[type] += millis;
		this.count[type]++;
		String normalizedSql = new String(sql);		
		Iterator<RegExpReplacementContainer> replacePatternsIterator = replacePatterns.iterator();
		Throwable errorThrown = null;
		while (replacePatternsIterator.hasNext()) {
			RegExpReplacementContainer patternReplacement = replacePatternsIterator.next();
			if (patternReplacement.isEnabled()) {
				try {
					normalizedSql = normalizedSql.replaceAll(patternReplacement.getRegexp(), patternReplacement.getReplacement());
				} catch (Throwable t) {
					// something went terrible wrong
					// log message
					String errorMessage = "Error applying pattern: " + patternReplacement.getRegexp() + " on " + normalizedSql;
					if (displayFullStackTrace) {
						logger[DEFAULT].error(errorMessage, t);
					} else {
						logger[DEFAULT].error("(" + t + ") " + errorMessage);
					}
					// disable the pattern so it is not applied
					patternReplacement.disable();
					errorThrown = t;
					increaseErrorCount();
				}
			}
		}
		sw.stop();
		this.logger[DEFAULT].trace("Normalized SQL(" + sw.getElapsedTimeMillis() + "): " + normalizedSql);
		// only add statement statistics if no error occurred
		if (errorThrown == null) {
			if (statementStatistics.containsKey(normalizedSql)) {
				StatementCharacteristics currentStatementStatistics = (StatementCharacteristics) statementStatistics.get(normalizedSql);
				currentStatementStatistics.increaseCount();
				currentStatementStatistics.increaseMillis(millis);
				currentStatementStatistics.setLastCommit(getDate());
			} else {
				if (statementStatistics.size() < maxStatements){
					StatementCharacteristics newStatementStatistics = new StatementCharacteristics(millis);
					statementStatistics.put(normalizedSql, newStatementStatistics);
				} else
					statementStatisticsOverflow++;
			}
		}
	}

	private Date getDate() {
        if ((System.currentTimeMillis() - 1000) > dateLastRenderedTime) {
	        dateLastRendered = new Date();
	        dateLastRenderedTime = dateLastRendered.getTime();
        }
        return dateLastRendered;
	}

	/**
	 * just a getter
	 * 
	 * @return if the Logger should log
	 */
	public boolean isLoggingEnabled() {
		return loggingEnabled;
	}

	/**
	 * just a setter
	 * 
	 * @param loggingEnabled sets logging to enabled or disables it
	 */
	public void setLoggingEnabled(boolean loggingEnabled) {
		this.loggingEnabled = loggingEnabled;
	}

	/**
	 * checks if type is valid gets it
	 * 
	 * @param type one of {@link #SELECT}, {@link #UPDATE}, {@link #INSERT} or {@link #DELETE}
	 * @return the millis of the given type
	 */
	public long getMillis(int type) {
		if (type >= millis.length || type < 0)
			return 0;
		return millis[type];
	}

	/**
	 * checks if type is valid and returns global count of type
	 * 
	 * @param type  one of {@link #SELECT}, {@link #UPDATE}, {@link #INSERT} or {@link #DELETE}
	 * @return the count of the given type
	 */
	public long getCount(int type) {
		if (type >= count.length || type < 0)
			return 0;
		return count[type];
	}

	/**
	 * just a getter
	 * 
	 * @return the normalized HashMap of the Statements
	 */
	public HashMap<String, StatementCharacteristics> getNormalizedStatements() {
		return statementStatistics;
	}
	
	/**
	 * just a getter
	 * 
	 * @return number of errors occurred when applying {@link #replacePatterns}
	 */
	public long getErrorCount() {
		return errorCount;
	}

	/**
	 * just a setter
	 * 
	 * @param errorCount the new {@link #errorCount}
	 */
	public void setErrorCount(long errorCount) {
		this.errorCount = errorCount;
	}
	
	/**
	 * just a setter. increases {@link #errorCount} by 1
	 */
	public void increaseErrorCount() {
		this.errorCount++;
	}

	/**
	 * just a getter {@link #maxStatements}
	 * 
	 * @return max allowed statements to be recorded
	 */
	public int getMaxStatements() {
		return maxStatements;
	}

	/**
	 * just a getter {@link #statementStatisticsOverflow}
	 * 
	 * @return number of not stored Statements in {@link #statementStatisticsOverflow}
	 */
	public long getStatementStatisticsOverflow() {
		return statementStatisticsOverflow;
	}

	/**
	 * a getter
	 * 
	 * @return returns when logging has started
	 */
	public Date getLoggingStarted() {
		return loggingStarted;
	}

	/**
	 * initializes the Logger
	 */
	public void init() {
		try {
			properties.load(new FileInputStream(CONFIGURATION_FILE_NAME));
			maxStatements = Integer.parseInt(properties.getProperty(Logger.JDBC_LOGGER_PREFIX + "maxStatementStatistics"));
			loggingEnabled = Boolean.parseBoolean(properties.getProperty(Logger.JDBC_LOGGER_PREFIX + "loggingEnabled"));
			displayFullStackTrace = Boolean.parseBoolean(properties.getProperty(Logger.JDBC_LOGGER_PREFIX + "displayFullStackTrace"));
			String baseNormalizeProperty = Logger.JDBC_LOGGER_PREFIX + "normalize.";
			int y = 0;
			while (properties.getProperty(baseNormalizeProperty + y + ".pattern") != null && properties.getProperty(baseNormalizeProperty + y + ".replacement") != null) {
				try {
					boolean mayBeDisabled = properties.getProperty(baseNormalizeProperty + y + ".doNotDisable") == "true" ? true : false;
					RegExpReplacementContainer patternReplacement = new RegExpReplacementContainer(properties.getProperty(baseNormalizeProperty + y + ".pattern"), properties.getProperty(baseNormalizeProperty + y + ".replacement"), true, mayBeDisabled);

					logger[DEFAULT].trace("Adding " + patternReplacement.getRegexp() + " => " + patternReplacement.getReplacement());
					replacePatterns.add(patternReplacement);
				} catch(PatternSyntaxException e) {
					logger[DEFAULT].error("Could not compile pattern: " + properties.getProperty(baseNormalizeProperty + y), e);
				}
				y++;
			}
			logger[DEFAULT].info(JDBC_LOGGER_PREFIX + VERSION + " build on " + BUILD_DATE + " init.");
		} catch (Exception e) {
			logger[DEFAULT].error("Error reading properties file: " + CONFIGURATION_FILE_NAME + ". Using defaults.", e);
		}		
	}
	
	/**
	 * sets defaults for all Statistics
	 */
	public void resetStatistics() {
		for (int i = 0; i < 5; i++) {
			count[i] = 0L;
			millis[i] = 0L;
		}
		errorCount = 0L;
		statementStatistics = new HashMap<String, StatementCharacteristics>();
		statementStatisticsOverflow = 0L;
		loggingStarted = new Date();
	}
}
