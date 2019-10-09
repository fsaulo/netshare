package com.etc;

import com.util.Hash;
import com.util.DBConnector;
import com.var.PostVar;
import com.var.UserVar;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;

/**
 *
 * @author fsaulo
 */
public class Post {

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public void insertPost(PostVar post) throws SQLException {

		String insertSQL = "insert into posts (post_content, post_mood, post_image, post_location, " +
		"user_id, feed_id) values (?, ?, ?, ?, ?, ?)";

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(insertSQL);
			ps.setString(1, post.getContent());
			ps.setString(2, post.getMood());
			ps.setString(3, post.getImagePath());
			ps.setString(4, post.getPlace());
			ps.setInt(5, post.getUserId());
			ps.setInt(6, post.getFeedId());
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

	public PostVar listPostDetails(int post_id) throws SQLException {

		String selectSQL = "select * from posts where post_id = ?";

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(selectSQL);
			ps.setInt(1, post_id);
			rs = ps.executeQuery();
			PostVar post = null;

			while (rs.next()) {

				post = new PostVar();
				post.setContent(rs.getString("post_content"));
				post.setImagePath(rs.getString("post_image"));
				post.setMood(rs.getString("post_mood"));
				post.setPlace(rs.getString("post_location"));
				post.setUserId(rs.getInt("user_id"));
				post.setFeedId(rs.getInt("feed_id"));
				post.setPostId(rs.getInt("post_id"));
				post.setDate(rs.getDate("post_date_created"));

			}
			return post;
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

	public PostVar countPosts(PostVar post) throws SQLException {
		String countSQL = "select count(*) from posts where user_id = ?";

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(countSQL);
			ps.setInt(1, post.getUserId());
			rs = ps.executeQuery();

			if (rs.next()) {
				post.setCountPosts(rs.getInt("count(*)"));
			}
			return post;
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

	public int getCountComments(int post_id) throws SQLException {

		String countSQL = "select count(*) from comments where post_id = ?";
		int count = 0;

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(countSQL);
			ps.setInt(1, post_id);
			rs = ps.executeQuery();

			if (rs.next()) {
				count = (rs.getInt("count(*)"));
			}
			return count;
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
}
