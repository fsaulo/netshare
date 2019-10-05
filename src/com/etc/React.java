package com.etc;

import com.var.ReactVar;
import com.var.PostVar;
import com.var.CommentVar;
import com.util.DBConnector;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

/**
 *
 * @author fsaulo
 */
public class React {

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public void insertReactInPost(ReactVar react) throws SQLException {

		String insertSQL = "insert into reacts (react_hash, user_id) values (?, ?)";
		String insertPostSQL = "insert into reacts_in_post (react_id, post_id, user_id) values (?, ?, ?)";
		String selectSQL = "select react_id from reacts where react_hash = ?";
		String selectPostSQL = "select count(*) from reacts_in_post where user_id = ?";
		String deleteSQL = "delete from reacts where user_id = ?";

		int react_id = 0;
		int count = 0;

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(selectPostSQL);
			ps.setInt(1, react.getUserId());
			rs = ps.executeQuery();

			while (rs.next()) {
				count = rs.getInt("count(*)");
			}

			// if reaction of this user already
			// exists, then remove this reaction
			if (count > 0) {
				ps = con.prepareStatement(deleteSQL);
				ps.setInt(1, react.getUserId());
				System.out.println("user has ditched react");
				ps.executeUpdate();
			}

			// if not, create a new reaction
			// and link it to a post
			else {

				// insert reaction
				ps = con.prepareStatement(insertSQL);
				ps.setString(1, react.getHash());
				ps.setInt(2, react.getUserId());
				ps.executeUpdate();

				// link reaction to post
				ps = con.prepareStatement(selectSQL);
				ps.setString(1, react.getHash());
				rs = ps.executeQuery();

				while (rs.next()) {
					react_id = rs.getInt("react_id");
				}

				ps = con.prepareStatement(insertPostSQL);
				ps.setInt(1, react_id);
				ps.setInt(2, react.getPostId());
				ps.setInt(3, react.getUserId());
				ps.executeUpdate();

				PostVar post = new PostVar();
				post.setPostId(react.getPostId());
				System.out.println("new react in post " + countReactsInPost(post));

			}
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

	public void insertReactInComment(ReactVar react) throws SQLException {

		String insertSQL = "insert into reacts (react_hash, user_id) values (?, ?)";
		String insertPostSQL = "insert into reacts_in_comment (react_id, comment_id, user_id) values (?, ?, ?)";
		String selectSQL = "select react_id from reacts where react_hash = ?";
		String selectCommentSQL = "select count(*) from reacts_in_comment where user_id = ?";
		String deleteSQL = "delete from reacts where user_id = ?";

		int react_id = 0;
		int count = 0;

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(selectCommentSQL);
			ps.setInt(1, react.getUserId());
			rs = ps.executeQuery();

			while (rs.next()) {
				count = rs.getInt("count(*)");
			}

			// if reaction of this user already
			// exists, then remove this reaction
			if (count > 0) {
				ps = con.prepareStatement(deleteSQL);
				ps.setInt(1, react.getUserId());
				System.out.println("user has ditched react");
				ps.executeUpdate();
			}

			// if not, create a new reaction
			// and link it to a comment.
			else {

				// insert reaction
				ps = con.prepareStatement(insertSQL);
				ps.setString(1, react.getHash());
				ps.setInt(2, react.getUserId());
				ps.executeUpdate();

				// link reaction to comment
				ps = con.prepareStatement(selectSQL);
				ps.setString(1, react.getHash());
				rs = ps.executeQuery();

				while (rs.next()) {
					react_id = rs.getInt("react_id");
				}

				ps = con.prepareStatement(insertPostSQL);
				ps.setInt(1, react_id);
				ps.setInt(2, react.getCommentId());
				ps.setInt(3, react.getUserId());
				ps.executeUpdate();

				CommentVar comment = new CommentVar();
				comment.setCommentId(react.getCommentId());
				comment = countReactsInComment(comment);
				System.out.println("new react in comment " + comment.getCountReacts());

			}
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

	public int countReactsInPost(PostVar post) throws SQLException {

		String countSQL = "select count(*) from reacts_in_post where post_id = ?";

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(countSQL);
			ps.setInt(1, post.getPostId());
			rs = ps.executeQuery();

			while (rs.next()) {
				post.setCountReacts(rs.getInt("count(*)"));
			}
			return post.getCountReacts();
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}

		finally {

			if (ps != null) con.close();
			if (con != null) ps.close();
			if (rs != null) rs.close();
		}
	}

	public CommentVar countReactsInComment(CommentVar comment) throws SQLException {

		String countSQL = "select count(*) from reacts_in_comment where comment_id = ?";

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(countSQL);
			ps.setInt(1, comment.getCommentId());
			rs = ps.executeQuery();

			while (rs.next()) {
				comment.setCountReacts(rs.getInt("count(*)"));
			}
			return comment;
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}

		finally {

			if (ps != null) con.close();
			if (con != null) ps.close();
			if (rs != null) rs.close();
		}
	}
}
