package gui;

import gui.commons.Icons;
import gui.commons.Labels;

import javax.swing.*;
import java.awt.*;

public class Toolbar extends JToolBar {

	private ToolbarListener btnListener;

	public Toolbar() {
		setFloatable(false);
        JButton printBtn = new JButton(Icons.PRINT);
        printBtn.setToolTipText(Labels.getProperty("print"));

        JButton exportToTableFileButton = new JButton(Icons.EXCEL_FILE);
        exportToTableFileButton.setPreferredSize(new Dimension(25, 25));
        exportToTableFileButton.setToolTipText(Labels.withThreeDots("exportToTableFile"));

        JButton cpvBtn = new JButton("CPV");
        cpvBtn.setToolTipText(Labels.withThreeDots("cpvPanelTab"));

        JButton calcButton = new JButton(Icons.CALCULATOR);
        calcButton.setToolTipText(Labels.withThreeDots("calculator"));

        add(printBtn);
        add(exportToTableFileButton);
        addSeparator();
        add(cpvBtn);
        add(calcButton);


        printBtn.addActionListener(e -> {
            if (btnListener != null) {
                btnListener.printEventOccurred();
            }
        });

        cpvBtn.addActionListener(e -> {
            if (btnListener != null) {
                btnListener.showCpvSearchDialog();
            }
        });

        calcButton.addActionListener(e -> {
            if (btnListener != null) {
                btnListener.showCalculator();
            }
        });
    }

	public void setToolbarListener(ToolbarListener listener) {
		this.btnListener = listener;
	}
}
