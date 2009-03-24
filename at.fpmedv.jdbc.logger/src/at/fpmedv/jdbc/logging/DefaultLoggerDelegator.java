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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class DefaultLoggerDelegator implements LoggerDelegator {

	// the Singleton
	private volatile static LoggerDelegator uniqueInstance;
	
	private boolean enabled = true;
	
    static DateFormat dformat = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss] ");

    public final static int TRACE = 0;
    public final static int DEBUG = 1;
    public final static int INFO =  2;
    public final static int WARN =  3;
    public final static int ERROR = 4;
    public final static int FATAL = 5;

    public final static String[] loglevelStringRepresantation = {"TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL"};
    
	private DefaultLoggerDelegator(){
		
	}

	public static LoggerDelegator getLogger() {
		if (uniqueInstance == null) {
			synchronized (DefaultLoggerDelegator.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new DefaultLoggerDelegator();
				}
			}
		}
		return uniqueInstance;
	}
	
	public void trace(Object msg) {
		printLoggingMessage(TRACE, msg, null);
	}
	
	public void trace(Object msg, Throwable t) {
		printLoggingMessage(TRACE, msg, t);
	}

	public void debug(Object msg) {
		printLoggingMessage(DEBUG, msg, null);
	}
	
	public void debug(Object msg, Throwable t) {
		printLoggingMessage(DEBUG, msg, t);
	}

	public void info(Object msg) {
		printLoggingMessage(INFO, msg, null);
	}

	public void info(Object msg, Throwable t) {
		printLoggingMessage(INFO, msg, t);
	}

	public void warn(Object msg) {
		printLoggingMessage(WARN, msg, null);
	}

	public void warn(Object msg, Throwable t) {
		printLoggingMessage(WARN, msg, t);
	}

	public void error(Object msg) {
		printLoggingMessage(ERROR, msg, null);
	}
	
	public void error(Object msg, Throwable t) {
		printLoggingMessage(ERROR, msg, t);
	}

	public void fatal(Object msg, Throwable t) {
		printLoggingMessage(FATAL, msg, t);
	}

	public void fatal(Object msg) {
		printLoggingMessage(FATAL, msg, null);
	}

	public void log(Level l, Object message) {
	
	}
	void printLoggingMessage(int loglevel, Object msg, Throwable t) {
		if (enabled) {
	        Date date = new Date();
	        String dateCache = dformat.format(date);
	        System.out.println(dateCache + "[jdbclogger] [" + loglevelStringRepresantation[loglevel] + "] " + msg);
		}
	}

	public void disableLogging() {
		enabled = false;		
	}

	public void enableLogging() {
		enabled = true;		
		
	}

	public boolean getEnabled() {
		return enabled;
	}
}
