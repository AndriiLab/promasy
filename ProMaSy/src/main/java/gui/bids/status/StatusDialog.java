package main.java.gui.bids.status;

import main.java.gui.Icons;
import main.java.gui.Labels;
import main.java.model.StatusModel;
import main.java.model.enums.Status;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

/**
 * Dialog displays bid status change history and controls for setting new status for the bid
 */
public class StatusDialog extends JDialog {

    private JButton setStatusButton;
    private JButton closeButton;
    private JComboBox<Status> statusJComboBox;
    private StatusTableModel statusTableModel;
    private JTable statusTable;
    private StatusDialogListener listener;
    private long selectedBidId;

    public StatusDialog(JFrame parent) {
        super(parent, Labels.getProperty("bidHistory"), false);
        setSize(450, 300);
        setLocationRelativeTo(parent);

        Dimension buttonDim = new Dimension(25, 25);
        Dimension comboBoxDim = new Dimension(150, 25);

        statusTableModel = new StatusTableModel();
        java.util.List<StatusModel> dummyList = new ArrayList<>();
        dummyList.add(new StatusModel());
        statusTableModel.setData(dummyList);
        statusTable = new JTable(statusTableModel);

        statusJComboBox = new JComboBox<>(Status.values());
        statusJComboBox.setPreferredSize(comboBoxDim);
        statusJComboBox.setEditable(false);

        setStatusButton = new JButton(Icons.SET_STATUS);
        setStatusButton.setToolTipText(Labels.getProperty("setStatus"));
        setStatusButton.setPreferredSize(buttonDim);
        setStatusButton.setEnabled(true);

        closeButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        setStatusButton.addActionListener(e -> {
            if (listener != null) {
                int statusId = ((Status) statusJComboBox.getSelectedItem()).getStatusId();
                String statusDesc = ((Status) statusJComboBox.getSelectedItem()).getStatusDesc();
                StatusModel model = new StatusModel(selectedBidId, statusId, statusDesc);
                listener.statusChangeEventOccured(model);
            }
        });


        closeButton.addActionListener(e -> setVisible(false));

    }

    public void setTableData(java.util.List<StatusModel> tableData) {
        statusTableModel.setData(tableData);
        statusTableModel.fireTableDataChanged();
    }

    public void setVisible(boolean b, long selectedBidId) {
        this.selectedBidId = selectedBidId;
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

        setStatusPanel.setLayout(new FlowLayout());
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
