package gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class StatusPanel extends JPanel{
	
	private JLabel currentUser;
	
	public StatusPanel(JFrame parent) {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		setPreferredSize(new Dimension(parent.getWidth(), 20));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		currentUser = new JLabel("");
		currentUser.setHorizontalAlignment(SwingConstants.LEFT);
		add(currentUser);
	}
		
		public void setCurrentUser(String user){
			currentUser.setText("Користувач: " + user);
		}
}
