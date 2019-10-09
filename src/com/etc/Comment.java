package com.etc;

import com.var.CommentVar;
import com.var.UserVar;
import com.var.PostVar;
import com.util.DBConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fsaulo
 */
public class Comment {

	PreparedStatement ps = null;
	Connection con = null;
	ResultSet rs = null;

	public void insertComnent(CommentVar comment) throws SQLException {

		String insertSQL = "insert into comments (post_id, user_id, comment_content) values (?, ?, ?)";

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(insertSQL);
			ps.setInt(1, comment.getPostId());
			ps.setInt(2, comment.getUserId());
			ps.setString(3, comment.getContent());
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

	public List<CommentVar> listCommentsInPost(int post_id) throws SQLException {

		String selectCommentSQL = "select * from comments where post_id = ?";

		try {

			con = DBConnector.getConnection();
			ps = con.prepareStatement(selectCommentSQL);
			ps.setInt(1, post_id);
			rs = ps.executeQuery();
			List<CommentVar> comments = new ArrayList<>();

			while (rs.next()) {

				CommentVar comment = new CommentVar();
				comment.setContent(rs.getString("comment_content"));
				comment.setCommentId(rs.getInt("comment_id"));
				comment.setPostId(rs.getInt("post_id"));
				comment.setUserId(rs.getInt("user_id"));
				comments.add(comment);

			}
			return comments;
		}

		catch (SQLException ex) {
			Logger.getLogger(Comment.class.getName()).log(Level.SEVERE, null, ex);
		}

		finally {

			if (con != null) con.close();
			if (ps != null) ps.close();
			if (rs != null) rs.close();

		}
		return null;
	}
}
