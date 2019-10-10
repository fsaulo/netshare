package com.util;

import com.var.UserVar;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.application.Application;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class SignUpController {

	private UserVar userData = new UserVar();
	private UserController userController = new UserController();

	@FXML private Stage primaryStage;
	@FXML private TextField firstNameText;
	@FXML private TextField lastNameText;
	@FXML private TextField emailText;
	@FXML private TextField passwordText;

	@FXML private Label firstNameRequiredLabel;
	@FXML private Label lastNameRequiredLabel;
	@FXML private Label emailRequiredLabel;
	@FXML private Label passwordRequiredLabel;
	@FXML private Label fieldsRequiredLabel;
	@FXML private Label acceptTermsLabel;

	@FXML private CheckBox acceptTermsCheckBox;
	@FXML private Button confirmButton;

	@FXML private ToggleGroup genderToggleGroup;
	@FXML private RadioButton radioButtonFemale;
	@FXML private RadioButton radioButtonMale;

	@FXML private DatePicker birthdayDate;

	/**
	 * catches all text fields data and regiter
	 * new user and provides a method non orthodox to
	 * verify that all fields are filled before
	 * submitting any request to database.
	 */
	public void confirmRegister(ActionEvent event) throws SQLException, IOException {

		String message = "* make sure all fields are filled";
		boolean confirmStatus = false;
		int itensVerified = 0;

		if (firstNameText.getText().equals("")) {
			setLabelMessage(firstNameRequiredLabel, "*");
			setLabelMessage(fieldsRequiredLabel, message);
		} else {
			itensVerified++;
			setLabelMessage(firstNameRequiredLabel, "");
		}

		if (lastNameText.getText().equals("")) {
			setLabelMessage(lastNameRequiredLabel, "*");
			setLabelMessage(fieldsRequiredLabel, message);
		} else {
			itensVerified++;
			setLabelMessage(lastNameRequiredLabel, "");
		}

		if (emailText.getText().equals("")) {
			setLabelMessage(emailRequiredLabel, "*");
			setLabelMessage(fieldsRequiredLabel, message);
		} else {
			itensVerified++;
			setLabelMessage(emailRequiredLabel, "");
		}

		if (passwordText.getText().equals("")) {
			setLabelMessage(passwordRequiredLabel, "*");
			setLabelMessage(fieldsRequiredLabel, message);
		} else {
			itensVerified++;
			setLabelMessage(passwordRequiredLabel, "");
		}

		if (!(acceptTermsCheckBox.isSelected())) {
			setLabelMessage(acceptTermsLabel, "You must accept the Terms and Agreements");
		} else {
			itensVerified++;
			setLabelMessage(acceptTermsLabel, "");
		}

		if (itensVerified == 5) {
			confirmStatus = true;
			setLabelMessage(fieldsRequiredLabel, "");
		} else itensVerified = 0;

		if (confirmStatus) {

			Hash hashEmail = new Hash();
			userData.setFirstName(firstNameText.getText());
			userData.setLastName(lastNameText.getText());
			userData.setEmail(emailText.getText());
			userData.setPassword(passwordText.getText());
			userData.setBirthday(birthdayDate.getValue());
			userData.setHashEmail(hashEmail.getHash());
			userData.setPin(hashEmail.getPin());
			userData.setAge();
			userData.setUserMode(1);
			userData.setPhoneNumber("55 5555-5555");

			try {
				userController.newUser(userData);
			} catch (SQLException ex) {
				throw new IOException(ex);
			}

			try {
				showConfirmScreen(event);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public void setLabelMessage(Label label, String message) {
		label.setText(message);
	}

	/**
	 * set default date to datepicker making
	 * it easier to select birthday.
	 */
	public void defaultDate() {
		birthdayDate.setValue(LocalDate.of(2000, 01, 01));
	}

	/**
	 * handles the radio button, it updates
	 * at every new change, so the user can't
	 * choose two options for gender.
	 */
	public void pickGender() {

		genderToggleGroup = new ToggleGroup();
		this.radioButtonFemale.setToggleGroup(genderToggleGroup);
		this.radioButtonMale.setToggleGroup(genderToggleGroup);

		if (this.genderToggleGroup.getSelectedToggle().equals(this.radioButtonFemale))
			userData.setGender("F");
		if (this.genderToggleGroup.getSelectedToggle().equals(this.radioButtonMale))
			userData.setGender("M");

	}

	/**
	 * this event was designed to switch
	 * between the login pane and sign up page
	 * through the hyperlink registerLink, loginLink
	 * respectively.
	 */
	public void showLogin(ActionEvent e) throws IOException {

		Parent LoginScreen = FXMLLoader.load(getClass().getResource("../../src/com/util/LoginScreen.fxml"));
		Scene loginScene = new Scene(LoginScreen, 1100, 700);
		Stage loginPane = (Stage)((Node)e.getSource()).getScene().getWindow();
		loginPane.setScene(loginScene);
		loginPane.setResizable(false);
		loginPane.show();

	}

	/**
	 * jump to confirm screen after registration
	 */
	public void showConfirmScreen(ActionEvent e) throws SQLException, IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../src/com/util/ConfirmEmailScreen.fxml"));

		Parent root = loader.load();
		Scene window = new Scene(root, 575, 350);

		// access the controller and give to it an instance
		// of the user we just created.
		ConfirmEmailController controller = loader.getController();
		controller.initializeUserData(userData);

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
				userController.endSession(userData.getUserId());
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		});
	}
}
