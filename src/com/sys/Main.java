package com.sys;

/**
* import javafx library to which path must be
* linked at the compiled modules.
*/
import com.util.UserController;
import com.var.PostVar;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


/**
* @author fsaulo
*/
public class Main extends Application {

    @Override
	public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../src/com/util/LoginScreen.fxml"));
        primaryStage.setTitle("netshare");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.setResizable(false);
        primaryStage.show();
	}

    public static void main(String[] args) throws IOException, SQLException {
        launch(args);
    }
}
