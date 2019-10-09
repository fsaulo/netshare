/**
 * Netshare - The open source social media experiment
 * Copyright (C) 2019  Saulo G. Felix
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sys;

import com.util.LoggerHandler;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger LOGGER =
        new LoggerHandler(Main.class.getName()).getGenericConsoleLogger();

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../src/com/util/LoginScreen.fxml"));
        primaryStage.setTitle("netshare");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.setResizable(false);
        primaryStage.show();
        LOGGER.info("Opened main window");
    }

    public static void main(String[] args) throws IOException, SQLException {
      launch(args);
    }
}
