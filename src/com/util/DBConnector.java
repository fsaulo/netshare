package com.util;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnector {

	private static String driver;
	private static String url;
	private static String username;
	private static String password;

	/**
	 * resposible for opening connection to the database
	 * hosted by local machine at localhost, for convenience
	 * the destiny port was omitted, default is 3306.
	 */
	public static Connection getConnection() {

		driver = "com.mysql.cj.jdbc.Driver";
		url = "jdbc:mysql://localhost:3306/netshare?useSSL=false";
		username = "root";
		password = "";

		try {

			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, username, password);
			return con;

		}

		catch (ClassNotFoundException | SQLException ex) {
			Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}
}
