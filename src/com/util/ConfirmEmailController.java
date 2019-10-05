package com.util;

import com.var.UserVar;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author fsaulo
 */
public class ConfirmEmailController {

	private UserVar registeredUser;
	private UserController userController = new UserController();

	@FXML private Button submitPinButton;
	@FXML private Button showLoginPageButton;

	@FXML private Hyperlink emailFieldLink;
	@FXML private Hyperlink sendPinLink;

	@FXML private Label successLabel;
	@FXML private Label notConfirmedLabel;
	@FXML private Label emailSentLabel;

	@FXML private TextField pinText;


	/**
	 * this method accepts a user to initialize the view
	 * @param UseVar
	 */
	public void initializeUserData(UserVar user) {

		registeredUser = user;
		emailFieldLink.setText(registeredUser.getEmail());

	}

	public void sendEmail(Event e) {
		// @TODO implement automatic sending email
		// to user with pin information.
		if (registeredUser.isStatusEmail()) {
			setLabelMessage(emailSentLabel, "no further procedures are necessary");
		} else {
			setLabelMessage(emailSentLabel, "Email instructions sent successfully");
		}
	}

	public void setLabelMessage(Label label, String message) {
		label.setText(message);
	}

	/**
	 * if the user wants to leave confirmation screen
	 * without going through the confirmation process
	 * he can.
	 */
	public void showLogin(ActionEvent e) throws IOException {

		Parent LoginScreen = FXMLLoader.load(getClass().getResource("../../src/com/util/LoginScreen.fxml"));
		Scene loginScene = new Scene(LoginScreen, 1100, 700);
		Stage loginPane = (Stage)((Node)e.getSource()).getScene().getWindow();
		loginPane.setScene(loginScene);
		loginPane.setResizable(false);
		loginPane.show();

	}

	public void confirmEmail() throws IOException, SQLException {

		boolean pinTextCheck = false;
		boolean emailCheck = false;

		if (pinText.getText().equals("")) {
			setLabelMessage(notConfirmedLabel, "You must provide a pin.");
		} else {
			pinTextCheck = true;
			setLabelMessage(notConfirmedLabel, "");
		}

		if (pinTextCheck) {
			registeredUser.setPin(pinText.getText());
			try {
				userController.confirmEmail(registeredUser);
				emailCheck = userController.emailStatus(registeredUser.getEmail());
			} catch (SQLException ex) {
				throw new IOException(ex);
			}
			if (emailCheck) {
				setLabelMessage(successLabel, "Email successfully validated");
			}
			else {
				setLabelMessage(notConfirmedLabel, "Pin doesn't match");
				pinTextCheck = false;
			}
		}
	}
}
