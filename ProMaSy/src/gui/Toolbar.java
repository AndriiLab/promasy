package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener {

	private JButton testConBtn;
	private ToolbarListener btnListener;

	public Toolbar() {
		setFloatable(false);
		testConBtn = new JButton();
		testConBtn.setToolTipText("Тест з'єднання");
		testConBtn.setIcon(Utils.createIcon("/images/TipOfTheDay16.gif"));

		testConBtn.addActionListener(this);

		add(testConBtn);
	}

	public void setToolbarListener(ToolbarListener listener) {
		this.btnListener = listener;
	}

	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();

		if (clicked == testConBtn) {
			if (btnListener != null) {
				btnListener.testConEventOccured();
			}
		}

	}

}
