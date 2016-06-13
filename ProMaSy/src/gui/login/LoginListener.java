package gui.login;

public interface LoginListener {

	void usernameEntered(String username);
	
	void loginAttemptOccurred(LoginAttemptEvent ev);
	
	void loginCancelled();

}
