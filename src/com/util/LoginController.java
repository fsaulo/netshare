package com.util;

import com.var.UserVar;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author fsaulo
 */
public class LoginController {

	private UserVar session = new UserVar();
	private UserController userController = new UserController();

	@FXML private Stage primaryStage;

	@FXML private Button loginButton;

	@FXML private TextField emailTextField;
	@FXML private TextField passwordTextField;

	@FXML private Label emailCheckLabel;
	@FXML private Label passwordCheckLabel;
	@FXML private Label checkFieldsLabel;
	@FXML private Label successMessageLabel;

	/**
	 * submit request on enter typed
	 */
	public void onEnter(ActionEvent event) throws SQLException, IOException {
		try {
			initSession(event);
		} catch (SQLException ex) {
			throw new IOException(ex);
		}
	}

	/**
	 * function frequently called at most
	 * methods condensed for convenience.
	 * puts a text in label.
	 */
	public void setLabelMessage(Label label, String message) {
		label.setText(message);
	}

	/**
	 * changes status of user_status_session
	 * to true if email and password matches.
	 */
	public void initSession(ActionEvent event) throws SQLException, IOException {

		// provides a method non orthodox to
	    // verify that all fields are filled before
	    // submitting any request to database.
		String message = "* password and email are required";
		String logged = "user logged with success";
		String error = "password or email are incorrect";
		boolean confirmStatus = false;
		int itensVerified = 0;

		if (emailTextField.getText().equals("")) {
			setLabelMessage(emailCheckLabel, "*");
			setLabelMessage(checkFieldsLabel, message);
		} else {
			setLabelMessage(emailCheckLabel, "");
			itensVerified++;
		}

		if (passwordTextField.getText().equals("")) {
			setLabelMessage(passwordCheckLabel, "*");
			setLabelMessage(checkFieldsLabel, message);
		} else {
			setLabelMessage(passwordCheckLabel, "");
			itensVerified++;
		}

		if (itensVerified == 2) {
			confirmStatus = true;
			setLabelMessage(checkFieldsLabel, "");
		} else itensVerified = 0;

		// checks if all required fields
		// were provided.
		if (confirmStatus) {

			session.setEmail(emailTextField.getText());
			session.setPassword(passwordTextField.getText());

			try {

				// holds null point exception if session
				// was not validated.
				session = userController.startSession(session);

				if (session.isStatusSession()) {

					// if email and password matches database,
					// but user didn't provide email confirmation,
					// it redirects to confirm stage.
					if (session.isStatusEmail() == false) {
						try {
							showConfirmScreen(event);
						} catch (IOException ex) {
							throw new SQLException(ex);
						}
					}
					else {

						//TODO redirect user to feed stage
						setLabelMessage(successMessageLabel, logged);

						try {
							showFeed(event);
						} catch (IOException ex) {
							throw new SQLException(ex);
						}
					}
				}

				else {
					setLabelMessage(checkFieldsLabel, error);
				}
			}

			catch(SQLException ex) {
				throw new IOException(ex);
			}
		}
	}

	/**
	 * jump to confirm screen if, after login,
	 * user email isn't already verified.
	 */
	public void showConfirmScreen(ActionEvent e) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../src/com/util/ConfirmEmailScreen.fxml"));

		Parent confirmEmailScreen = loader.load();
		Scene confirmEmailScene = new Scene(confirmEmailScreen, 575, 350);

		// access the controller and give to it an instance
		// of the user we just created.
		ConfirmEmailController controller = loader.getController();
		controller.initializeUserData(session);

		// this line gets the stage information.
		Stage confirmEmailPane = (Stage)((Node)e.getSource()).getScene().getWindow();
		confirmEmailPane.setScene(confirmEmailScene);
		confirmEmailPane.setResizable(false);
		confirmEmailPane.show();

	}

	/**
	 * this event was designed to switch
	 * between the login pane and sign up page
	 * throught the hyperlink
	 */
	public void showSignUp(ActionEvent e) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("../../src/com/util/SignUpScreen.fxml"));
		Scene window = new Scene(root, 400, 700);
		primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		primaryStage.setScene(window);
		primaryStage.setResizable(false);
		primaryStage.show();

	}


	/**
	 * jump to feed screen after login
	 */
	public void showFeed(ActionEvent e) throws SQLException, IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../src/com/util/FeedScreen.fxml"));

		Parent root = loader.load();
		Scene window = new Scene(root, 1300, 700);

		// access the controller and give to it an instance
		// of the user that just logged into the system.
		FeedController controller = loader.getController();
		controller.initializeUserData(session);

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
				userController.endSession(session.getUserId());
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		});
	}
}
