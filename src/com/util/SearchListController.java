package com.util;

import com.var.UserVar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;


public class SearchListController {

	private UserController userController = new UserController();

	private UserVar session = new UserVar();
	private UserVar listSearch = new UserVar();
	private FeedController controller;

	private int idSession;
	private String list_type;

	@FXML private ObservableList<UserVar> searchList = FXCollections.observableArrayList();

	@FXML private ListView<UserVar> listUsers;

	public void getList(String type, String criteria, int user_id, FeedController feed) {

		list_type = "search";
		idSession = user_id;
		this.controller = feed;

		try {
			List<UserVar> listPerson = userController.showPortrait(type, criteria);
			System.out.printf("searching for %s...\n", criteria);

			for (UserVar u : listPerson) {
				searchList.addAll(u);
			}

			listUsers.setItems(searchList);
			listUsers.setCellFactory(event -> new SearchListViewCell(
			this.idSession, this.listSearch, this.controller));

			listUsers.setOnMouseClicked(e -> {
				// System.out.println(listUsers.getItems().getUserId());
			});

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void getFollowersList(int user_id, FeedController feed) {

		list_type = "follower";
		idSession = user_id;
		this.controller = feed;
		try {

			List<UserVar> listFollowers = userController.getFollowers(idSession);
			System.out.println("searching followers...");

			for (UserVar u : listFollowers) {
				searchList.addAll(u);
			}

			listUsers.setItems(searchList);
			listUsers.setCellFactory(event -> new FollowerListCell (
			this.idSession, this.listSearch, this.controller));

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void getFollowingList(int user_id, FeedController feed) {

		list_type = "follower";
		idSession = user_id;
		this.controller = feed;

		try {

			List<UserVar> listFollowers = userController.getFollowing(idSession);
			System.out.println("searching following...");

			for (UserVar u : listFollowers) {
				searchList.addAll(u);
			}

			listUsers.setItems(searchList);
			listUsers.setCellFactory(event -> new SearchListViewCell (
			this.idSession, this.listSearch, this.controller));

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void showID() {
		// searchList = listUsers.getItems();
		// System.out.println("oi");
	}
}
