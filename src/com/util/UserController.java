package com.util;

import com.var.CommentVar;
import com.var.FeedVar;
import com.var.FollowerVar;
import com.var.InteractionVar;
import com.var.PostVar;
import com.var.ReactVar;
import com.var.UserVar;
import com.etc.Comment;
import com.etc.Feed;
import com.etc.Follower;
import com.etc.Interaction;
import com.etc.Login;
import com.etc.Logout;
import com.etc.Portrait;
import com.etc.Post;
import com.etc.React;
import com.etc.User;
import com.util.Hash;
import com.etc.Login;
import com.etc.Logout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.time.LocalDate;

public class UserController {
	private static final Logger LOGGER =
        new LoggerHandler(UserController.class.getName()).getGenericConsoleLogger();

	private User user = new User();
    private Hash hash = new Hash();
	private UserVar session = null;
	private Login login = null;
	private Logout end = null;

	public UserController() {

	}

	public void newUser(UserVar newUser) throws SQLException
	{
		try
		{
			newUser.setStatusSession(false);
			newUser.setStatusEmail(false);
			newUser.setStatusEmail(false);
			user.insertUser(newUser);
		}

		catch (SQLException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public void confirmEmail(UserVar session) throws SQLException
	{
		try
		{
			user.verifyEmail(session);
		}

		catch (SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

	public boolean emailStatus(String email) throws SQLException
	{
		boolean email_status;

		try
		{
			email_status = email_status = user.checkEmailStatus(email);
			return email_status;
		}

		catch(SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

	public UserVar startSession(UserVar session) throws SQLException
	{
		login = new Login();
		UserVar newSession = new UserVar();

		try
		{
			LOGGER.info("Attempt to login into the system.");
			newSession = login.userAuthentication(session);
			return newSession;
		}

		catch (SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

	public void endSession(int idSession) throws SQLException
	{
		session = new UserVar();
		end = new Logout();
		session.setUserId(idSession);

		try
		{
			LOGGER.info("Attempt to logoff.");
			end.exitSystem(session);
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	public void newPost(PostVar post) throws SQLException
	{
            Post postController = new Post();
            Feed feedController = new Feed();
            int id_feed = feedController.selectFeedId(post.getUserId());
            post.setFeedId(id_feed);

		try
		{
			postController.insertPost(post);
		}

		catch (SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

	public void newComment(int userId, int postId, String content) throws SQLException {

		Comment cmt = new Comment();
		CommentVar comment = new CommentVar();
		comment.setContent(content);
		comment.setUserId(userId);
		comment.setPostId(postId);

		try
		{
			cmt.insertComnent(comment);
		}

		catch (SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

	public void newReactInPost(int post_id, int user_id) throws SQLException
	{
		React rct = new React();
		ReactVar react = new ReactVar();

		react.setHash(hash.getHash());
		react.setPostId(post_id);
        react.setUserId(user_id);

		try
		{
			rct.insertReactInPost(react);
		}

		catch(SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

	public void newReactInComment(int comment_id, int user_id) throws SQLException
	{
		React rct = new React();
		ReactVar react = new ReactVar();

		react.setHash(hash.getHash());
		react.setCommentId(comment_id);
        react.setUserId(user_id);

		try
		{
			rct.insertReactInComment(react);
		}

		catch(SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

	public void newInteraction(int user_id, int follower_id) throws SQLException
	{
		FollowerVar follower = new FollowerVar();
		Interaction following = new Interaction();
		follower.setUserId(user_id);
		follower.setFollowerId(follower_id);

		try
		{
			following.insertFollower(follower);
		}

		catch (SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

	public int showReactsInPost(int post_id) throws SQLException
	{
		PostVar post = new PostVar();
		React react = new React();
		post.setPostId(post_id);
        int count = 0;

		try
		{
			count = react.countReactsInPost(post);
		}

		catch (SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}

		return count;
	}

	public void showReactsInComment(int comment_id) throws SQLException
	{
		CommentVar comment = new CommentVar();
		React react = new React();
		comment.setCommentId(comment_id);

		try
		{
			comment = react.countReactsInComment(comment);
		}

		catch (SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}


	public int howManyFollowers(int user_id) throws SQLException
	{
		FollowerVar follower = new FollowerVar();
		Interaction following = new Interaction();
		follower.setUserId(user_id);
		int totalFollower = 0;

		try
		{
			follower = following.countFollower(follower);
			return follower.getCountFollower();
		}

		catch (SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

	public int howManyFollowing(int user_id) throws SQLException
	{
		FollowerVar follower = new FollowerVar();
		Interaction following = new Interaction();
		follower.setUserId(user_id);

		try
		{
			follower = following.countFollowing(follower);
			return follower.getCountFollowing();
		}

		catch (SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

    public int howManyPosts(int user_id) throws SQLException
	{
        PostVar post = new PostVar();
        post.setUserId(user_id);
        Post postController = new Post();

        try
		{
            post = postController.countPosts(post);
            return post.getCountPosts();
        }

        catch (SQLException ex)
		{
            throw new RuntimeException(ex.getMessage());
        }
    }

	public int howManyComments(int post_id) throws SQLException
	{

		Post controller = new Post();
		int count = 0;

		try
		{
			count = controller.getCountComments(post_id);
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return count;
	}

	public List<UserVar> showPortrait(String searchMode, String criteria) throws SQLException
	{

		List<UserVar> profiles = null;

		try
		{
			profiles = user.listUser(searchMode, criteria, 0);
			return profiles;
		}

		catch (SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

    public List<String> listByName(String searchMode, String criteria) throws SQLException
	{
        List<UserVar> profiles = null;

        try
		{
            profiles = user.listUser(searchMode, "byName", 0);
            List<String> name = new ArrayList<>();

            for (UserVar u : profiles)
			{
                name.add(u.getFirstName() + " " + u.getLastName());
            }

            return name;
        }

        catch (SQLException ex)
		{
            throw new RuntimeException(ex.getMessage());
        }
    }

	public int getFollowerId(int user_id)
	{
		int follower_id = 0;
		Follower follower = new Follower();

		try
		{
			follower_id = follower.getFollowerId(user_id);
			return follower_id;

		}
		catch (SQLException ex)
		{
			LOGGER.severe(ex.getMessage());
		}
		return -1;
	}

	public boolean interactionCheck(int user_id, int follower_id)
	{
		Interaction controller = new Interaction();

		try
		{
			return controller.interactionExists(user_id, follower_id);
		}
		catch (SQLException ex)
		{
			LOGGER.severe(ex.getMessage());
		}
		return false;
	}

	public List<UserVar> getFollowers(int user_id) throws SQLException
	{
		Interaction controller = new Interaction();
		List<UserVar> user = new ArrayList<>();

		try
		{
			user = controller.listFollowers(user_id, "follower");
			return user;
		}

		catch (SQLException ex)
		{
			LOGGER.severe(ex.getMessage());
		}

		return null;
	}

	public List<UserVar> getFollowing(int user_id) throws SQLException
	{
		Interaction controller = new Interaction();
		List<UserVar> user = new ArrayList<>();
		try
		{
			user = controller.listFollowers(this.getFollowerId(user_id), "following");
			return user;

		} catch (SQLException ex)
		{
			LOGGER.severe(ex.getMessage());
		}
		return null;
	}

	public List<PostVar> getPortrait(int user_id) throws SQLException
	{
		Portrait controller = new Portrait();
		List<PostVar> posts = new ArrayList<>();

		try
		{
			posts = controller.listPortraitPosts(user_id);
		} catch (SQLException ex)
		{
			LOGGER.severe(ex.getMessage());
		}
		return posts;
	}
}
