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

import org.apache.log4j.Logger;

public class Log4JLoggerDelegator implements LoggerDelegator {
	
	private Logger logger;
			
	public Log4JLoggerDelegator(String name) {
		logger = Logger.getLogger(name);
	}

	public void debug(Object msg) {
		logger.debug(msg);
	}

	public void debug(Object msg, Throwable t) {
		logger.debug(msg, t);
	}

	public void error(Object msg) {
		logger.error(msg);
	}

	public void error(Object msg, Throwable t) {
		logger.error(msg, t);
	}

	public void fatal(Object msg) {
		logger.fatal(msg);
	}

	public void fatal(Object msg, Throwable t) {
		logger.fatal(msg, t);
	}

	public void info(Object msg) {
		logger.info(msg);
	}

	public void info(Object msg, Throwable t) {
		logger.info(msg, t);
	}

	public void trace(Object msg) {
		logger.trace(msg);
	}

	public void trace(Object msg, Throwable t) {
		logger.trace(msg, t);
	}

	public void warn(Object msg) {
		logger.warn(msg);
	}

	public void warn(Object msg, Throwable t) {
		logger.warn(msg, t);
	}
}
