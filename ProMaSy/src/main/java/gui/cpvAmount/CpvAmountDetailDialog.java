package gui.cpvAmount;

import gui.MainFrame;
import gui.commons.Labels;
import model.models.CpvAmountModel;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for detailed view of bids from {@link model.models.CpvAmountModel}
 */
public class CpvAmountDetailDialog extends JDialog {
    private CpvAmountDetailTableModel tableModel;
    private JTable table;

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

    public void setData(CpvAmountModel cpvAmountModel) {
        tableModel.setData(cpvAmountModel);
        setTitle(cpvAmountModel.getType().getBidTypeName() + " - " + cpvAmountModel.getCpv().getCpvId() + " - " + cpvAmountModel.getCpv().getCpvUkr());
        table.setAutoCreateRowSorter(true);
        tableModel.fireTableDataChanged();
        setVisible(true);
    }
}
