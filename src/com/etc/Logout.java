package com.etc;

import com.var.UserVar;
import com.util.DBConnector;
import com.util.LoggerHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logout {
	private static final Logger LOGGER =
		new LoggerHandler(Logout.class.getName()).getGenericConsoleLogger();

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public void exitSystem(UserVar session) throws SQLException
	{
		String selectSQL = "SELECT user_status_session FROM users WHERE user_id = ?";
		String updateSQL = "UPDATE users SET user_status_session = ? WHERE user_id = ?";

		try
		{
			con = DBConnector.getConnection();
			ps = con.prepareStatement(selectSQL);
			ps.setInt(1, session.getUserId());
			rs = ps.executeQuery();

			if (rs.next())
			{
				LOGGER.info("Attempt to logout.");
				session.setStatusSession(rs.getBoolean("user_status_session"));
				ps = con.prepareStatement(updateSQL);

				if (session.isStatusSession())
				{
					session.setStatusSession(false);
					ps.setBoolean(1, session.isStatusSession());
					ps.setInt(2, session.getUserId());
				}

				else
				{
					ps.setBoolean(1, session.isStatusSession());
					ps.setInt(2, session.getUserId());

					LOGGER.info("Exiting...");
					System.exit(0);
				}

				ps.executeUpdate();
			}
		}

		catch (SQLException ex)
		{
			Logger.getLogger(Logout.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
