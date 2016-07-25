package main.java.model;

public class ConnectionSettingsModel {
	private String server;
	private String database;
	private String schema;
	private int portNumber;
	private String user;
	private String password;

    public ConnectionSettingsModel(String server, String database, String schema, int portNumber, String user,
								   String password) {
		this.server = server;
		this.schema = schema;
		this.database = database;
		this.portNumber = portNumber;
		this.user = user;
		this.password = password;
	}
	public String getSchema() {
		return schema;
	}
	public String getServer() {
		return server;
	}
	public String getDatabase() {
		return database;
	}
	public int getPortNumber() {
		return portNumber;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
}
