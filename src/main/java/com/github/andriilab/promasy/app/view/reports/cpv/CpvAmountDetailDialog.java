package com.github.andriilab.promasy.app.view.reports.cpv;

import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Labels;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for detailed view of bids from {@link CpvAmount}
 */
class CpvAmountDetailDialog extends JDialog {
    private final CpvAmountDetailTableModel tableModel;
    private final JTable table;

    public CpvAmountDetailDialog(MainFrame parent) {
        super(parent, Labels.getProperty("cpvAmounts"), true);
        setSize(750, 400);
        setLocationRelativeTo(parent);
        setResizable(false);

        tableModel = new CpvAmountDetailTableModel();
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setMinWidth(180);
        table.getColumnModel().getColumn(1).setMinWidth(150);
        table.getColumnModel().getColumn(2).setMinWidth(150);


        JButton closeButton = new JButton(Labels.getProperty("closeBtn"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(e -> setVisible(false));
    }

    public void setData(CpvAmount cpvAmount) {
        tableModel.setData(cpvAmount);
        setTitle(cpvAmount.getType().getBidTypeName() + " - " + cpvAmount.getCpv().getCpvId() + " - " + cpvAmount.getCpv().getCpvUkr());
        table.setAutoCreateRowSorter(true);
        tableModel.fireTableDataChanged();
        setVisible(true);
    }
}
