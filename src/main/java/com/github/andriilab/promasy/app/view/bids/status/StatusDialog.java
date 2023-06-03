package com.github.andriilab.promasy.app.view.bids.status;

import com.github.andriilab.promasy.data.commands.CreateCommand;
import com.github.andriilab.promasy.data.commands.UpdateCommand;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.BidStatus;
import com.github.andriilab.promasy.domain.bid.enums.Status;
import com.github.andriilab.promasy.app.commons.Labels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Dialog displays bid status change history and controls for setting new status for the bid
 */
public class StatusDialog extends JDialog {
    private final JButton setStatusButton;
    private final JButton closeButton;
    private final JComboBox<Status> statusJComboBox;
    private final DefaultComboBoxModel<Status> defaultComboBoxModel;
    private final StatusTableModel statusTableModel;
    private final JTable statusTable;
    private StatusDialogListener listener;
    private Bid selectedBidModel;

    public StatusDialog(JFrame parent) {
        super(parent, Labels.getProperty("bidHistory"), true);
        setSize(450, 300);
        setLocationRelativeTo(parent);

        Dimension comboBoxDim = new Dimension(150, 25);

        listener = new EmptyStatusDialogListener();
        statusTableModel = new StatusTableModel();
        statusTable = new JTable(statusTableModel);

        defaultComboBoxModel = new DefaultComboBoxModel<>(Status.values());

        statusJComboBox = new JComboBox<>(defaultComboBoxModel);
        statusJComboBox.setPreferredSize(comboBoxDim);
        statusJComboBox.setEditable(false);

        setStatusButton = new JButton(Labels.getProperty("setStatus"));
        setStatusButton.setEnabled(true);

        closeButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        setStatusButton.addActionListener(e -> {
            Status selectedStatus = (Status) statusJComboBox.getSelectedItem();
            BidStatus statusModel = new BidStatus(selectedStatus, selectedBidModel);
            selectedBidModel.addStatus(statusModel);
            listener.persistModelEventOccurred(new CreateCommand<>(statusModel));
            listener.persistModelEventOccurred(new UpdateCommand<>(selectedBidModel));
            this.setTableData(selectedBidModel);
        });

        closeButton.addActionListener(e -> setVisible(false));
    }

    private void setTableData(Bid model) {
        statusTableModel.setBidModel(model);
        statusTable.setAutoCreateRowSorter(true);
        statusTableModel.fireTableDataChanged();
    }

    public void setVisible(boolean b, Bid model) {
        this.selectedBidModel = model;
        setTableData(model);
        int lastStatusNumber = defaultComboBoxModel.getIndexOf(model.getLastBidStatusModel().getStatus());
        if (lastStatusNumber != -1 && lastStatusNumber < (defaultComboBoxModel.getSize() - 1)) {
            statusJComboBox.setSelectedIndex(lastStatusNumber + 1);
        }
        super.setVisible(b);
    }

    public void setStatusDialogListener(StatusDialogListener listener) {
        this.listener = listener;
    }

    private void layoutControls() {
        JPanel statusPanel = new JPanel();
        JPanel setStatusPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border statusPanelBorder = BorderFactory.createEtchedBorder();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        statusPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, statusPanelBorder));

        setStatusPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        setStatusPanel.add(statusJComboBox);
        setStatusPanel.add(setStatusButton);

        statusPanel.setLayout(new BorderLayout());
        statusPanel.add(new JScrollPane(statusTable), BorderLayout.CENTER);
        statusPanel.add(setStatusPanel, BorderLayout.SOUTH);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        buttonsPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(statusPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
