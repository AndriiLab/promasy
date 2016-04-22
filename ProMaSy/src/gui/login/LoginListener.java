package gui.login;

import java.util.EventObject;

public interface LoginListener {
	
	public void loginAttemptOccured(LoginAttemptEvent ev);
	
	public void loginCancelled(EventObject ev);

}
