package com.util;

import com.var.UserVar;
import com.var.PostVar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

import java.sql.SQLException;
import java.net.MalformedURLException;

/**
* @author fsaulo
*/
public class NewPostViewController {

	private UserVar currentSession;
	private UserController userController;
	private int user_id;
	private String image_path;
	private PostVar post;

	@FXML private ImageView previewImage;

	@FXML private Label fileSelected;
	@FXML private Stage primaryStage;

	@FXML private Button cancelButton;
	@FXML private Button confirmPostButton;
	@FXML private Button selectImageButton;

	@FXML private TextArea contentPost;

	@FXML private void closeDialog(ActionEvent e){
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void initializeUserData(UserVar user) throws SQLException, IOException {
		this.currentSession = user;
		this.user_id = currentSession.getUserId();

		this.post = new PostVar();
		this.userController = new UserController();

		confirmPostButton.setOnAction(event -> {

			post.setContent(contentPost.getText());
			post.setImagePath(image_path);
			post.setUserId(user_id);

			try {
				userController.newPost(post);
				closeDialog(event);
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		});
	}

	public void pickImage(ActionEvent actionEvent) throws MalformedURLException {

		String imageFile;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Image File");
		fileChooser.getExtensionFilters().addAll(
		new FileChooser.ExtensionFilter("Image Files",
		"*.bmp", "*.png", "*.jpg", "*.gif"));

		File selectedFile = fileChooser.showOpenDialog(fileSelected.getScene().getWindow());

		if (selectedFile != null) {

			imageFile = selectedFile.toURI().toURL().toString();
			System.out.println("uploading image... " + imageFile);
			image_path = imageFile;
			Image image = new Image(imageFile);
			previewImage.setFitHeight(306);
			previewImage.setFitWidth(560);
			previewImage.setImage(image);

		} else {
			fileSelected.setText("Image file selection cancelled.");
		}
	}

	public void postConfirm(ActionEvent e) throws IOException, SQLException {

		PostVar post = new PostVar();

		post.setContent(contentPost.getText());
		post.setImagePath(image_path);
		post.setUserId(user_id);

		try {
			userController.newPost(post);
			closeDialog(e);
		} catch (SQLException ex) {
			throw new IOException(ex);
		}
	}
}
