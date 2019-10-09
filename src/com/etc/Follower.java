package com.etc;

import com.var.FollowerVar;
import com.util.DBConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fsaulo
 */
public class Follower extends User {

	Connection con = null;
	PreparedStatement ps = null;

	public Follower() {

	}

	/**
	 * this method is called at com.sys.User everytime a new user
	 * is inserted in database. a new connection to database is
	 * required, at the end it is closed with proper treatment
	 */
	public void setFollower(int user_id, String fName) throws SQLException {

		String insertSQL = "insert into follower (user_id, follower_name) values (?, ?)";

		try {

			System.out.println("Setting follower attributes...");

			con = DBConnector.getConnection();
			ps = con.prepareStatement(insertSQL);

			ps.setInt(1, user_id);
			ps.setString(2, fName);
			ps.executeUpdate();

		}

		catch (SQLException ex) {
			Logger.getLogger(Follower.class.getName()).log(Level.SEVERE, null, ex);
		}

		finally {

			if (con != null) con.close();
			if (ps != null) ps.close();

		}
	}

	/**
	 * returns follower id associated with user id
	 * or user id associated with follower id.
	 */
	public int getFollowerId(int user_id) throws SQLException {

		String selectSQL = "select follower_id from follower where user_id = ?";
		int follower_id = 0;

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(selectSQL);
			ps.setInt(1, user_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				follower_id = rs.getInt("follower_id");
			}

			return follower_id;

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		finally {

			if (con != null) con.close();
			if (ps != null) ps.close();
			if (rs != null) rs.close();

		}
		return -1;
	}
}
