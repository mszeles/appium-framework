package com.mszeles.appium.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

public class MySqlExample {

	@Test
	public void connectToDB() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qadbt", "root", "password");
		Statement s = connection.createStatement();
		ResultSet r = s.executeQuery("select * from credentials where scenario='zerobalancecard'");
		r.next();
		System.out.println(r.getString("username"));
		System.out.println(r.getString("password"));
	}
}
