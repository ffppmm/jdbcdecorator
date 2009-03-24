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

public interface LoggerDelegator {
	
	public void trace(Object msg);
	
	public void trace(Object msg, Throwable t);

	public void debug(Object msg);
	
	public void debug(Object msg, Throwable t);

	public void info(Object msg);

	public void info(Object msg, Throwable t);

	public void warn(Object msg);

	public void warn(Object msg, Throwable t);

	public void error(Object msg);
	
	public void error(Object msg, Throwable t);

	public void fatal(Object msg);

	public void fatal(Object msg, Throwable t);
	
}

