package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener {

	private JButton printBtn;
	private ToolbarListener btnListener;

	public Toolbar() {
		setFloatable(false);
		printBtn = new JButton();
		printBtn.setToolTipText(Labels.getProperty("print"));
		printBtn.setIcon(Utils.createIcon("/images/Prnt16.gif"));
		add(printBtn);
        printBtn.addActionListener(this);
	}

	public void setToolbarListener(ToolbarListener listener) {
		this.btnListener = listener;
	}

	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();

		if (clicked == printBtn) {
			if (btnListener != null) {
				btnListener.printEventOccurred();
			}
		}

	}

}
