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
		password = "b7T2J!@mm9";
		// url = "jdbc:mysql://179.105.185.227/netshare?useSSL=false";
		// username = "netshare.admin";
		// password = "7H3!CJfXJ%PkTWV2";

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
