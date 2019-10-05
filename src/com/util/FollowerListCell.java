package com.util;

import com.var.UserVar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public class FollowerListCell extends ListCell<UserVar> {

	@FXML private Label name;
	@FXML private GridPane grid;
	@FXML private Button followButton;
	@FXML private FXMLLoader loader;

	private FeedController controller;
	private UserController userController;
	private String verifyFollower = "select user_id, follower_id from follower where user_id = ?";
	private int idSession;
	protected UserVar listPerson;

	public FollowerListCell(int session, UserVar search, FeedController feed) {
		this.idSession = session;
		this.listPerson = search;
		this.controller = feed;
		userController = new UserController();
	}

	@Override
	protected void updateItem(UserVar person, boolean empty) {
		super.updateItem(person, empty);

		if (empty || person == null) {
			setText(null);
			setGraphic(null);
		}

		else {

			if (loader == null) {
				loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../../src/com/util/ListCell.fxml"));
				loader.setController(this);

				try {
					loader.load();
				} catch(IOException ex) {
					ex.printStackTrace();
				}
			}

			name.setText(person.getFullName());

			int follower_id = userController.getFollowerId(this.idSession);

			if (userController.interactionCheck(person.getUserId(), this.idSession)) {
				followButton.setText("Unfollow");
			} else {
				followButton.setText("Follow back");
			}
			followButton.setOnAction(event -> {
				try {
					userController.newInteraction(person.getUserId(), this.idSession);
					controller.updateStatus(event);
					if (userController.interactionCheck(person.getUserId(), this.idSession)) {
						followButton.setText("Unfollow");
					} else {
						followButton.setText("Follow back");
					}
					super.updateItem(person, empty);
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			});
			setText(null);
			setGraphic(grid);
		}
	}
}
