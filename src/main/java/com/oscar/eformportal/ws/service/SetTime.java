/**
 * Copyright (c) 2001-2002. Department of Family Medicine, McMaster University. All Rights Reserved.
 * This software is published under the GPL GNU General Public License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * This software was written for the
 * Department of Family Medicine
 * McMaster University
 * Hamilton
 * Ontario, Canada
 */
package com.oscar.eformportal.ws.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.oscarehr.util.DbConnectionFilter;
import org.oscarehr.util.MiscUtils;


public class SetTime {
	public static void inquiryTime(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String sql1 = "update eform_inquiry set inquirytime = ?";
		PreparedStatement statement;
		try {
			statement = DbConnectionFilter.getThreadLocalDbConnection().prepareStatement(sql1);
			statement.setTimestamp(1, timestamp);
			statement.execute();
		} catch (SQLException e) {
			MiscUtils.getLogger().debug(e.toString());
		}
	
	}
}
