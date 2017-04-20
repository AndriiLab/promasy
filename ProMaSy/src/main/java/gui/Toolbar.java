package gui;

import gui.commons.Icons;
import gui.commons.Labels;

import javax.swing.*;
import java.awt.*;

public class Toolbar extends JToolBar {

    private ToolbarListener btnListener;

    public Toolbar() {
        setFloatable(false);
        Dimension dim = new Dimension(20, 20);
        JButton printBtn = new JButton(Icons.PRINT);
        printBtn.setPreferredSize(dim);
        printBtn.setToolTipText(Labels.getProperty("print"));

        JButton exportToTableFileButton = new JButton(Icons.EXCEL_FILE);
        exportToTableFileButton.setPreferredSize(dim);
        exportToTableFileButton.setPreferredSize(new Dimension(25, 25));
        exportToTableFileButton.setToolTipText(Labels.withThreeDots("exportToTableFile"));

        JButton refreshButton = new JButton(Icons.REFRESH);
        refreshButton.setPreferredSize(dim);
        refreshButton.setPreferredSize(new Dimension(25, 25));
        refreshButton.setToolTipText(Labels.getProperty("refreshTable"));

        JButton cpvBtn = new JButton("CPV");
        cpvBtn.setPreferredSize(dim);
        cpvBtn.setToolTipText(Labels.withThreeDots("cpvPanelTab"));

        JButton calcButton = new JButton(Icons.CALCULATOR);
        calcButton.setPreferredSize(dim);
        calcButton.setToolTipText(Labels.withThreeDots("calculator"));

        add(printBtn);
        add(exportToTableFileButton);
        add(refreshButton);
        addSeparator();
        add(cpvBtn);
        add(calcButton);


        printBtn.addActionListener(e -> {
            if (btnListener != null) {
                btnListener.printEventOccurred();
            }
        });

        exportToTableFileButton.addActionListener(e -> {
            if (btnListener != null) {
                btnListener.exportToTableEventOccurred();
            }
        });

        refreshButton.addActionListener(e -> {
            if (btnListener != null) {
                btnListener.refreshTable();
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
