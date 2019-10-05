package com.util;

import com.var.*;
import com.etc.*;
import com.util.Hash;
import com.etc.Login;
import com.etc.Logout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/**
 * @author fsaulo
 */
public class UserController {

	private User user = new User();
    private Hash hash = new Hash();
	private UserVar session = null;
	private Login login = null;
	private Logout end = null;

	public UserController() {

	}

	/**
	 * method supposed to call the insert
	 * function at com.sys.User intending
	 * to create new user and register it in database.
	 * @param UserVar all attributes.
	 */
	public void newUser(UserVar newUser) throws SQLException {

		try {

			newUser.setStatusSession(false);
			newUser.setStatusEmail(false);
			newUser.setStatusEmail(false);
			user.insertUser(newUser);

		}

		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * calls the database instance and tries to
	 * confirm user email.
	 * @param UserVar holds user email
	 */
	public void confirmEmail(UserVar session) throws SQLException {

		try {
			user.verifyEmail(session);
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * goes into database and check if given email
	 * is confirmed, returns false if not.
	 * @param email
	 */
	public boolean emailStatus(String email) throws SQLException {

		boolean email_status;

		try {

			email_status = email_status = user.checkEmailStatus(email);
			return email_status;

		}
		catch(SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * starts a session if user email password
	 * matches database.
	 * @param UserVar holds email and password
	 */
	public UserVar startSession(UserVar session) throws SQLException {

		login = new Login();
		UserVar newSession = new UserVar();

		try {

			newSession = login.userAuthentication(session);

			if (session != null) {
				System.out.println("Name: " + newSession.getFirstName() + " Email: " + newSession.getEmail());
			}
			return newSession;
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

    /**
     * loggoff user
     */
	public void endSession(int idSession) throws SQLException {
		session = new UserVar();
		end = new Logout();
		session.setUserId(idSession);

		try {
			end.exitSystem(session);
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

    /**
     * insert new post
     */
	public void newPost(PostVar post)
		throws SQLException {

            Post postController = new Post();
            Feed feedController = new Feed();
            int id_feed = feedController.selectFeedId(post.getUserId());
            post.setFeedId(id_feed);

		try {
			postController.insertPost(post);
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

    /**
     * calls insertion of a new comment in
     * method that handles database.
     */
	public void newComment(int userId, int postId, String content) throws SQLException {

		Comment cmt = new Comment();
		CommentVar comment = new CommentVar();
		comment.setContent(content);
		comment.setUserId(userId);
		comment.setPostId(postId);

		try {
			cmt.insertComnent(comment);
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

    /**
     * inserts new reaction into database
     * and link it with a post
     */
	public void newReactInPost(int post_id, int user_id) throws SQLException {

		React rct = new React();
		ReactVar react = new ReactVar();

		react.setHash(hash.getHash());
		react.setPostId(post_id);
        react.setUserId(user_id);

		try {
			rct.insertReactInPost(react);
		}

		catch(SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

    /**
     * inserts new reaction into database
     * and link it with a comment
     */
	public void newReactInComment(int comment_id, int user_id) throws SQLException {

		React rct = new React();
		ReactVar react = new ReactVar();

		react.setHash(hash.getHash());
		react.setCommentId(comment_id);
        react.setUserId(user_id);

		try {
			rct.insertReactInComment(react);
		}

		catch(SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

    /**
     * returns a list with all posts of user
     */
	public void showAllPosts(int user_id) throws SQLException {

		List<PostVar> posts = null;
		Portrait profile = new Portrait();

		try {

			posts = profile.listPortraitPosts(user_id);

			for (PostVar post : posts) {
				System.out.println("Post: " + post.getContent());
			}

		}
		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}


    /**
     * returns a list with all comments
     * associated with post.
     * @param post_id
     */
	public void showComments(int post_id) throws SQLException {

		List<CommentVar> comments = null;
		Comment comment = new Comment();
		React react = new React();
		UserVar nameUser = new UserVar();

		try {

			comments = comment.listCommentsInPost(post_id);

			for (CommentVar com : comments) {

				com = react.countReactsInComment(com);
				nameUser = user.getNameById(com.getUserId());
				System.out.printf("%s %80s %10d reacts\n", nameUser.getFirstName(), com.getContent(),
				com.getCountReacts());

			}
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	public void showPost(int post_id) throws SQLException {

		PostVar postDetails = null;
		Post post = new Post();

		try {

			postDetails = post.listPostDetails(post_id);
			System.out.printf("content: %80s\nimage: %82s\nplace: %82s\n\n", postDetails.getContent(),
			postDetails.getImagePath(), postDetails.getPlace());

		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	public void newInteraction(int user_id, int follower_id) throws SQLException {

		FollowerVar follower = new FollowerVar();
		Interaction following = new Interaction();
		follower.setUserId(user_id);
		follower.setFollowerId(follower_id);

		try {
			following.insertFollower(follower);
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	public int showReactsInPost(int post_id) throws SQLException {

		PostVar post = new PostVar();
		React react = new React();
		post.setPostId(post_id);
        int count = 0;

		try {
			count = react.countReactsInPost(post);
			// System.out.printf("Post has %d reacts\n", count);
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
		return count;
	}

	public void showReactsInComment(int comment_id) throws SQLException {

		CommentVar comment = new CommentVar();
		React react = new React();
		comment.setCommentId(comment_id);

		try {
			comment = react.countReactsInComment(comment);
			System.out.println(comment.getCountReacts() +" reacts");
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}


	public int howManyFollowers(int user_id) throws SQLException {

		FollowerVar follower = new FollowerVar();
		Interaction following = new Interaction();
		follower.setUserId(user_id);
		int totalFollower = 0;

		try {

			follower = following.countFollower(follower);
			return follower.getCountFollower();
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	public int howManyFollowing(int user_id) throws SQLException {

		FollowerVar follower = new FollowerVar();
		Interaction following = new Interaction();
		follower.setUserId(user_id);

		try {

			follower = following.countFollowing(follower);
			return follower.getCountFollowing();
		}

		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

    public int howManyPosts(int user_id) throws SQLException {

        PostVar post = new PostVar();
        post.setUserId(user_id);
        Post postController = new Post();

        try {

            post = postController.countPosts(post);
            return post.getCountPosts();

        }

        catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

	public int howManyComments(int post_id) throws SQLException {

		Post controller = new Post();
		int count = 0;

		try {
			count = controller.getCountComments(post_id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return count;
	}

	public List<UserVar> showPortrait(String searchMode, String criteria) throws SQLException {

		List<UserVar> profiles = null;

		try {

			profiles = user.listUser(searchMode, criteria, 0);
			for (UserVar u : profiles) {
				System.out.println(u.getFirstName() + " " + u.getLastName());
			}
			return profiles;
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

    public List<String> listByName(String searchMode, String criteria) throws SQLException {

        List<UserVar> profiles = null;

        try {

            profiles = user.listUser(searchMode, "byName", 0);
            List<String> name = new ArrayList<>();

            for (UserVar u : profiles) {
                name.add(u.getFirstName() + " " + u.getLastName());
            }
            return name;
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

	public int getFollowerId(int user_id) {

		int follower_id = 0;
		Follower follower = new Follower();

		try {

			follower_id = follower.getFollowerId(user_id);
			return follower_id;

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return -1;
	}

	public boolean interactionCheck(int user_id, int follower_id) {

		Interaction controller = new Interaction();

		try {
			return controller.interactionExists(user_id, follower_id);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}

	public List<UserVar> getFollowers(int user_id) throws SQLException {

		Interaction controller = new Interaction();
		List<UserVar> user = new ArrayList<>();

		try {
			user = controller.listFollowers(user_id, "follower");
			return user;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	public List<UserVar> getFollowing(int user_id) throws SQLException {

		Interaction controller = new Interaction();
		List<UserVar> user = new ArrayList<>();

		try {

			user = controller.listFollowers(this.getFollowerId(user_id), "following");
			return user;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	/**
	 * returns all posts of user
	 */
	public List<PostVar> getPortrait(int user_id) throws SQLException {

		Portrait controller = new Portrait();
		List<PostVar> posts = new ArrayList<>();

		try {

			posts = controller.listPortraitPosts(user_id);

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return posts;
	}
}
