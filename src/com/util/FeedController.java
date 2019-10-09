package com.util;

import com.var.UserVar;
import com.var.PostVar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.net.MalformedURLException;

/**
 * @author fsaulo
 */
public class FeedController {

	private UserVar currentSession;
	private UserController userController;
	private PostVar post;
	private int user_id;
	private String image_path;

	@FXML private Parent root;
	@FXML private BorderPane postPane;
	@FXML private Scene window;

	@FXML private Stage primaryStage;
	@FXML private Stage dialogStage;

	@FXML private Button logoutButton;
	@FXML private Button settingsButton;
	@FXML private Button followerButton;
	@FXML private Button selectImageButton;
	@FXML private Button newPostButton;
	@FXML private Button confirmPostButton;
	@FXML private Button cancelButton;

	@FXML private Label nameLabel;
	@FXML private Label followersCount;
	@FXML private Label followingCount;
	@FXML private Label postsCount;
	@FXML private Label fileSelected;

	@FXML private ScrollPane feedScrollPane;

	@FXML private TextField searchText;

	@FXML FXMLLoader loader;

	@FXML public void closeDialog(ActionEvent e){
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML public void closeStage(ActionEvent e) {
		((Node)(e.getSource())).getScene().getWindow().hide();
	}

	/**
	 * this method accepts a user to initialize the view
	 * @param UseVar
	 */
	public void initializeUserData(UserVar user) throws SQLException {

		currentSession = user;
		userController = new UserController();
		user_id = currentSession.getUserId();
		nameLabel.setText(currentSession.getFirstName());
		followersCount.setText("0"+Integer.toString(userController.howManyFollowers(user_id)));
		followingCount.setText("0"+Integer.toString(userController.howManyFollowing(user_id)));
		postsCount.setText("0"+Integer.toString(userController.howManyPosts(user_id)));

	}

	public void updateStatus(ActionEvent e) throws SQLException {
		nameLabel.setText(currentSession.getFirstName());
		followersCount.setText("0"+Integer.toString(userController.howManyFollowers(user_id)));
		followingCount.setText("0"+Integer.toString(userController.howManyFollowing(user_id)));
		postsCount.setText("0"+Integer.toString(userController.howManyPosts(user_id)));
	}

	public void logout(ActionEvent e) {

		try {
			userController.endSession(currentSession.getUserId());
		} catch(SQLException ex) {
			System.out.println(ex);
		}

		try {
			showLogin(e);
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	/**
	 * when user logs out, goes to login page
	 */
	public void showLogin(ActionEvent e) throws IOException {

		Parent LoginScreen = FXMLLoader.load(getClass().getResource("../../src/com/util/LoginScreen.fxml"));
		Scene loginScene = new Scene(LoginScreen, 1100, 700);
		Stage loginPane = (Stage)((Node)e.getSource()).getScene().getWindow();
		loginPane.setScene(loginScene);
		loginPane.setResizable(false);
		loginPane.show();

	}

	public void showSelfPortrait(ActionEvent e) throws IOException, SQLException {

		try {
			showPortrait(e, currentSession);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void showPortrait(ActionEvent e, UserVar profileUser) throws IOException, SQLException {

		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../src/com/util/PortraitScreen.fxml"));
		Parent root = loader.load();
		Scene window = new Scene(root, 1300, 700);

		// access the controller and give to it an instance
		// of the user that just logged into the system.
		PortraitController controller = loader.getController();
		controller.initializeUserData(currentSession, profileUser);

		// this line gets the stage information.
		primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		primaryStage.setScene(window);
		primaryStage.setResizable(false);
		primaryStage.show();

		// this lambda expression handles the
		// logout method in case user ends
		// the program through default window event
		primaryStage.setOnHiding(event -> {
			try {
				System.out.println("Logging out...");
				userController.endSession(profileUser.getUserId());
				System.out.println("program closed");
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		});
	}

	public void showFeed(ActionEvent e) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("../../src/com/util/FeedScreen.fxml"));
		Scene window = new Scene(root, 1300, 700);
		primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		primaryStage.setScene(window);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public void showPostDialog(ActionEvent e) throws IOException, SQLException, MalformedURLException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../src/com/util/NewPostView.fxml"));
		postPane = loader.load();

		NewPostViewController controller = loader.getController();
		controller.initializeUserData(currentSession);

		dialogStage = new Stage();

		dialogStage.setTitle("netshare");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);

		window = new Scene(postPane);

		dialogStage.setScene(window);
		dialogStage.setResizable(false);
		dialogStage.showAndWait();

		postsCount.setText("0"+Integer.toString(userController.howManyPosts(user_id)));

	}

	public void showFollowersList(ActionEvent e) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../src/com/util/FollowersListView.fxml"));
		BorderPane followerList = loader.load();

		SearchListController controller = loader.getController();
		controller.getFollowersList(user_id, this);

		Stage dialogStage = new Stage();
		dialogStage.setTitle(currentSession.getFirstName() + " Followers");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene window = new Scene(followerList);
		dialogStage.setScene(window);
		dialogStage.showAndWait();

	}

	public void showFollowingList(ActionEvent e) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../src/com/util/FollowingListView.fxml"));
		BorderPane followingList = loader.load();

		SearchListController controller = loader.getController();
		controller.getFollowingList(user_id, this);

		Stage dialogStage = new Stage();
		dialogStage.setTitle(currentSession.getFirstName() + " Followers");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene window = new Scene(followingList);
		dialogStage.setScene(window);
		dialogStage.showAndWait();

	}

	public void searchList(ActionEvent e) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../src/com/util/SearchListView.fxml"));
		BorderPane searchList = loader.load();

		SearchListController controller = loader.getController();
		controller.getList("byName", searchText.getText(), user_id, this);

		Stage dialogStage = new Stage();
		dialogStage.setTitle("Search");
		dialogStage.initModality(Modality.NONE);
		dialogStage.initOwner(primaryStage);

		Scene window = new Scene(searchList);
		dialogStage.setScene(window);
		dialogStage.setAlwaysOnTop(true);
		dialogStage.show();

		dialogStage.setOnHidden(event -> {
			// closeStage(e);
		});

	}
}
