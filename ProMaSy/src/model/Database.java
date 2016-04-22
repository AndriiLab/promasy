package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum Database {
	INSTANCE;
	
	private Connection con;
	
	public Connection getConnection() {
		return con;
	}
	
	public void connect(Properties conSet) throws Exception {
		if (con != null)
			return;

		String url = "jdbc:postgresql://"+conSet.getProperty("host")+
				":"+conSet.getProperty("port")+"/"+conSet.getProperty("database");

		con = DriverManager.getConnection(url, conSet);
		System.out.println("Connected successfully");
	}
	
	public void disconnect() {
		if (con != null) {
			try {
				con.close();
				System.out.println("Disconnected successfully");
			} catch (SQLException e) {
				System.out.println("Can't close connection");
			}
		}
		
		con = null;
	}
	
}
