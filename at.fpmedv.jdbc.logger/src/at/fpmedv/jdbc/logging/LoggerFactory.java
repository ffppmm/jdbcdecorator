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
package at.fpmedv.jdbc.logging;

import java.util.HashMap;

import org.apache.log4j.PropertyConfigurator;

import at.fpmedv.jdbc.Logger;

public class LoggerFactory {
	private static String filename = Logger.CONFIGURATION_FILE_NAME;

	private static HashMap<String, LoggerDelegator> logger = new HashMap<String, LoggerDelegator>();

	public static LoggerDelegator getLogger(String name) {
		if (logger.containsKey(name)) {
			return (LoggerDelegator) logger.get(name);
		}
		try {
			// check for log4j
			Class.forName("org.apache.log4j.Logger");
			// System.out.println("[jdbclogger] found org.apache.log4j");
			LoggerDelegator tmpLogger = new Log4JLoggerDelegator(name);
			logger.put(name, tmpLogger);
			if (logger.size() == 1)
				PropertyConfigurator.configureAndWatch(filename);
		} catch (Throwable t) {
			t.printStackTrace();
			logger.put(name, DefaultLoggerDelegator.getLogger());
		}
		return (LoggerDelegator) logger.get(name);
	}
}
