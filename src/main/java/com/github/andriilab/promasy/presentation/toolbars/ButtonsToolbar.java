package com.github.andriilab.promasy.presentation.toolbars;

import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;

import javax.swing.*;
import java.awt.*;

public class ButtonsToolbar extends JToolBar {

    private ButtonsToolbarListener buttonsToolbarListener;

    public ButtonsToolbar() {
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
        addSeparator();

        printBtn.addActionListener(e -> {
            if (buttonsToolbarListener != null) {
                buttonsToolbarListener.printEventOccurred();
            }
        });

        exportToTableFileButton.addActionListener(e -> {
            if (buttonsToolbarListener != null) {
                buttonsToolbarListener.exportToTableEventOccurred();
            }
        });

        refreshButton.addActionListener(e -> {
            if (buttonsToolbarListener != null) {
                buttonsToolbarListener.refreshTable();
            }
        });

        cpvBtn.addActionListener(e -> {
            if (buttonsToolbarListener != null) {
                buttonsToolbarListener.showCpvSearchDialog();
            }
        });

        calcButton.addActionListener(e -> {
            if (buttonsToolbarListener != null) {
                buttonsToolbarListener.showCalculator();
            }
        });
    }

    public void setButtonsToolbarListener(ButtonsToolbarListener listener) {
        this.buttonsToolbarListener = listener;
    }
}
