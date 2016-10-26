package main.java.gui.login;

public interface LoginListener {

	long usernameEntered(String username);
	
	void loginAttemptOccurred(LoginAttemptEvent ev);
	
	void loginCancelled();

}
