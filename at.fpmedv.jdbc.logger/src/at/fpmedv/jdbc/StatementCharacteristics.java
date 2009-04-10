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

import java.util.Date;

/**
 * 
 * Class for holding the statistics of a SQL statement.
 * 
 * @author Franz Philipp Moser
 *
 */
public class StatementCharacteristics {
	
	/**
	 * How long it took to execute the SQL statement (comulative)
	 */
	private long millis = 0L;
	
	/**
	 * How often the SQL statement has been executed
	 */
	private int count = 1;
	
	private Date lastCommit = new Date();
	
	public StatementCharacteristics(long millis) {
		this.millis = millis;
	}

	public void increaseMillis(long millis) {
		this.millis += millis;
	}
	
	public void increaseCount() {
		this.count++;
	}
	
	public long getMillis() {
		return millis;
	}
	
	public int getCount() {
		return count;
	}

	public Date getLastCommit() {
		return lastCommit;
	}

	public void setLastCommit(Date lastCommit) {
		this.lastCommit = lastCommit;
	}
}
