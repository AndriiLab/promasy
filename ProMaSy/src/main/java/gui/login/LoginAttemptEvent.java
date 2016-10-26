package main.java.gui.login;

import java.util.EventObject;

public class LoginAttemptEvent extends EventObject{
	
	private String username;
	private String password;
	
	LoginAttemptEvent(Object source, String username, String password) {
		super(source);
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
