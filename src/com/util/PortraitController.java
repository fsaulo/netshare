package com.util;

import com.var.PostVar;
import com.var.UserVar;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.application.Application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 * @author fsaulo
 */
public class PortraitController {

	private UserVar currentSession;
	private UserVar currentPortrait;
	private PostVar post;
	private UserController userController = new UserController();
	private List<PostVar> posts = new ArrayList<>();

	@FXML private Stage primaryStage;
	@FXML private Label nameLabel;
	@FXML private Label followersCount;
	@FXML private Label followingCount;
	@FXML private Label postsCount;
	@FXML private ListView<PostVar> listPosts;
	@FXML private ObservableList<PostVar> postsList = FXCollections.observableArrayList();

	/**
	 * this method accepts a user to initialize the view
	 * @param UseVar
	 */
	public void initializeUserData(UserVar user, UserVar portrait) throws SQLException {

		currentSession = user;
		currentPortrait = portrait;

		int userId = currentPortrait.getUserId();
		int count = 0;

		try {

			posts = userController.getPortrait(portrait.getUserId());

			nameLabel.setText(currentPortrait.getFirstName());
			postsCount.setText("0"+Integer.toString(userController.howManyPosts(userId)));
			followersCount.setText("0"+Integer.toString(userController.howManyFollowers(userId)));
			followingCount.setText("0"+Integer.toString(userController.howManyFollowing(userId)));

			for (PostVar p : posts) {
				p.setCountReacts(userController.showReactsInPost(p.getPostId()));
				p.setCountComments(userController.howManyComments(p.getPostId()));
				postsList.addAll(p);
			}

			listPosts.setItems(postsList);
			listPosts.setCellFactory(event -> new PortraitListCell(currentPortrait.getUserId(), this.post));

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * jump to feed screen
	 */
	public void showFeed(ActionEvent e) throws SQLException, IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../src/com/util/FeedScreen.fxml"));

		Parent root = loader.load();
		Scene window = new Scene(root, 1300, 700);

		// access the controller and give to it an instance
		// of the user that just logged into the system.
		FeedController controller = loader.getController();
		controller.initializeUserData(currentSession);

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
				userController.endSession(currentSession.getUserId());
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		});
	}

	public void showSearchList(ActionEvent e) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../src/com/util/SearchListView.fxml"));
		BorderPane searchList = loader.load();

		Stage dialogStage = new Stage();
		dialogStage.setTitle(currentSession.getFirstName() + " Followers");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);

		Scene window = new Scene(searchList);
		dialogStage.setScene(window);
		dialogStage.showAndWait();

	}
}
