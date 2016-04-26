package gui.login;

import java.util.EventObject;

public interface LoginListener {
	
	void loginAttemptOccurred(LoginAttemptEvent ev);
	
	void loginCancelled(EventObject ev);

}
