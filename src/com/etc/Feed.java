package com.etc;

import com.var.FeedVar;
import com.util.DBConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fsaulo
 */
public class Feed extends User {

	Connection con = null;
	PreparedStatement ps = null;

	public Feed() {

	}

	/**
	 * this method is called at com.sys.User everytime a new user
	 * is inserted in database. a new connection to database is
	 * required, at the end it is closed with proper treatment .
	 */
	public void spawnFeed(int user_id) throws SQLException {

		String insertSQL = "insert into feed (user_id) values (?)";

		try {

			System.out.println("Setting new Feed for user...");
			con = DBConnector.getConnection();
			ps = con.prepareStatement(insertSQL);
			ps.setInt(1, user_id);
			ps.executeUpdate();

		}

		catch (SQLException ex) {
			Logger.getLogger(Feed.class.getName()).log(Level.SEVERE, null, ex);
		}

		finally {

			if (con != null) con.close();
			if (ps != null) ps.close();

		}
	}

	public int selectFeedId(int user_id) throws SQLException {

		String selectSQL = "select feed_id from feed where user_id = ?";
		int feed_id = 0;

		try {
			con = DBConnector.getConnection();
			ps = con.prepareStatement(selectSQL);
			ps.setInt(1, user_id);
			rs = ps.executeQuery();
			while (rs.next()){
				feed_id = rs.getInt("feed_id");
			}
			return feed_id;
		}

		catch (SQLException ex) {
			Logger.getLogger(Feed.class.getName()).log(Level.SEVERE, null, ex);
		}

		finally {

			if (con != null) con.close();
			if (ps != null) ps.close();
			if (rs != null) rs.close();
		}
		return -1;
	}
}
