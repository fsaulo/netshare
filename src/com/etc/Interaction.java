package com.etc;

import com.var.InteractionVar;
import com.var.UserVar;
import com.var.FollowerVar;
import com.util.DBConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;

/**
 *
 * @author fsaulo
 */
public class Interaction {

	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;

	public void insertFollower(FollowerVar follower) throws SQLException {

		String insertSQL = "insert into interactions (user_id, follower_id) values (?, ?)";
		String selectFollowerSQL = "select follower_id, user_id from follower where user_id = ?";

		if (!checkLoopRequest(follower, selectFollowerSQL, false)) {
			removeInteraction(follower.getUserId(), follower.getFollowerId());
			return;
		}

		else {

			try {

				con = DBConnector.getConnection();
				ps = con.prepareStatement(insertSQL);
				ps.setInt(1, follower.getUserId());
				ps.setInt(2, follower.getFollowerId());
				ps.executeUpdate();

			}

			catch (SQLException ex) {
				throw new RuntimeException(ex.getMessage());
			}

			finally {

				if (con != null) con.close();
				if (ps != null) ps.close();

			}
		}
	}


	/**
	 * convoluted way to check if person
	 * requested to follow itself.
	 * @param SQLQuery selection
	 */
	public boolean checkLoopRequest(FollowerVar follower, String query, boolean request) throws SQLException {

		int userId = 0;
		int followerId = 0;

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, follower.getUserId());
			rs = ps.executeQuery();

			while (rs.next()) {

				followerId = rs.getInt("follower_id");
				userId = rs.getInt("user_id");

			}

			if (interactionExists(follower.getUserId(), follower.getFollowerId())){
				return false;
			} else if ((follower.getUserId() == userId) && (follower.getFollowerId() == followerId)) {
				// System.out.println("There was a problem processing your request");
				return false;
			}

			else if (!request) {

				String selectInteractionSQL = "select user_id, follower_id from interactions where user_id = ?";
				request = checkLoopRequest(follower, selectInteractionSQL, true);
				return request;

			}
			return true;
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}

		finally {

			if (con != null) con.close();
			if (rs != null) rs.close();
			if (ps != null) ps.close();

		}
	}

	/**
	 * if interaction exists, return true
	 * otherwise, return false. user of user_id
	 * is followed by person whose follower id is
	 * follow_id.
	 **/
	public boolean interactionExists(int user_id, int follower_id) throws SQLException {

		String selectSQL = "select count(*) from interactions where user_id = ? and follower_id = ?";
		int count = 0;

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(selectSQL);
			ps.setInt(1, user_id);
			ps.setInt(2, follower_id);
			rs = ps.executeQuery();

			while(rs.next()) {
				count = rs.getInt("count(*)");
			} if (count > 0) {
				return true;
			} else return false;

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		finally {

			if (con != null) con.close();
			if (ps != null) ps.close();
			if (rs != null) rs.close();

		}
		return false;
	}

	public void removeInteraction(int user_id, int follower_id) throws SQLException {

		String deleteSQL = "delete from interactions where user_id = ? and follower_id = ?";

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(deleteSQL);
			ps.setInt(1, user_id);
			ps.setInt(2, follower_id);
			ps.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		finally {

			if (con != null) con.close();
			if (ps != null) ps.close();
			if (rs != null) rs.close();

		}
	}

	public FollowerVar countFollower(FollowerVar follower) throws SQLException {

		String countSQL = "select count(*) from interactions where user_id = ?";

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(countSQL);
			ps.setInt(1, follower.getUserId());
			rs = ps.executeQuery();

			if (rs.next()) {
				follower.setCountFollower(rs.getInt("count(*)"));
			}
			return follower;
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}

		finally {

			if (con != null) con.close();
			if (ps != null) ps.close();
			if (rs != null) rs.close();

		}
	}

	public FollowerVar countFollowing(FollowerVar follower) throws SQLException {

		String selectSQL = "select follower_id from follower where user_id = ?";
		String countSQL = "select count(*) from interactions where follower_id = ?";
		int followerId = 0;

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(selectSQL);
			ps.setInt(1, follower.getUserId());
			rs = ps.executeQuery();

			while (rs.next()) {

				followerId = rs.getInt("follower_id");
				ps = con.prepareStatement(countSQL);
				ps.setInt(1, followerId);
				rs = ps.executeQuery();

				while (rs.next()) {
					follower.setCountFollowing(rs.getInt("count(*)"));
				}
			}
			return follower;
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}

		finally {

			if (con != null) con.close();
			if (ps != null) ps.close();
			if (rs != null) rs.close();

		}
	}

	/**
	 * bad database, dont look.
	 * TODO: improve management of database connections
	 * & improve database itself & improve code.
	 */
	public List<UserVar> listFollowers(int user_id, String criteria) throws SQLException {

		Follower controller = new Follower();
		User database = new User();

		String selectFollowerSQL = "select follower_id from interactions where user_id = ?";
		String selectFollowing = "select user_id from interactions where follower_id = ?";


		try {

			con = DBConnector.getConnection();

			if (criteria.equals("follower")) {
				ps = con.prepareStatement(selectFollowerSQL);
			} else if (criteria.equals("following")) {
				ps = con.prepareStatement(selectFollowing);
			}

			ps.setInt(1, user_id);
			rs = ps.executeQuery();

			List<UserVar> followers = new ArrayList<>();

			while (rs.next()) {

				UserVar follower = new UserVar();
				List<UserVar> details = new ArrayList<>();

				if (criteria.equals("follower")) {
					details = database.listUser("byId", null, rs.getInt("follower_id"));
				} else if (criteria.equals("following")) {
					details = database.listUser("byId", null, rs.getInt("user_id"));
				}

				for (UserVar unique : details) {

					follower.setFullName(unique.getFullName());
					follower.setUserId(unique.getUserId());
					follower.setFirstName(unique.getFirstName());
					follower.setLastName(unique.getLastName());
					followers.add(follower);

				}
			}
			return followers;

		} catch (SQLException ex) {
			Logger.getLogger(Interaction.class.getName()).log(Level.SEVERE, null, ex);
		}

		finally {

			if (con != null) con.close();
			if (ps != null) ps.close();
			if (rs != null) rs.close();

		}
		return null;
	}
}
