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
