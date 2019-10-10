package com.etc;

import com.var.CommentVar;
import com.var.FeedVar;
import com.var.FollowerVar;
import com.var.InteractionVar;
import com.var.PostVar;
import com.var.ReactVar;
import com.var.UserVar;
import com.util.DBConnector;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fsaulo
 */
public class Portrait {

	Connection con = null;
	ResultSet rs = null;
	PreparedStatement ps = null;

	/**
	 * portrait is a stage where are all posts
	 * of user whose id is user_id.
	 * returns a list of type PostVar
	 * that holds all posts from user.
	 * @param user_id
	 */
	public List<PostVar> listPortraitPosts(int user_id) throws SQLException {

		String selectPostsSQL = "select * from posts where user_id = ?";

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(selectPostsSQL);
			ps.setInt(1, user_id);
			rs = ps.executeQuery();
			List<PostVar> posts = new ArrayList<>();

			while (rs.next()) {

				PostVar post = new PostVar();
				post.setPostId(rs.getInt("post_id"));
				post.setContent(rs.getString("post_content"));
				post.setImagePath(rs.getString("post_image"));
				post.setUserId(rs.getInt("user_id"));
				post.setFeedId(rs.getInt("feed_id"));
				post.setPlace(rs.getString("post_location"));
				post.setMood(rs.getString("post_mood"));
				post.setDate(rs.getDate("post_date_created"));
				posts.add(post);

			}
			return posts;
		}

		catch (SQLException ex) {
			Logger.getLogger(Portrait.class.getName()).log(Level.SEVERE, null, ex);
		}

		finally {

			if (con != null) con.close();
			if (ps != null) ps.close();
			if (rs != null) rs.close();

		}
		return null;
	}
}
