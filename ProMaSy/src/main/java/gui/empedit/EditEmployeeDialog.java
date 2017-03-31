package gui.empedit;

import gui.MainFrame;
import gui.commons.Icons;
import gui.commons.Labels;
import gui.components.CEDButtons;
import model.models.EmployeeModel;
import model.models.EmptyModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EditEmployeeDialog extends JDialog {

    private EditEmployeeDialogListener listener;
    private JButton createEmployeeButton;
    private JButton editEmployeeButton;
    private JButton deleteEmployeeButton;

    private JTable table;
    private EmployeeTableModel tableModel;
    private EmployeeModel selectedModel;

    public EditEmployeeDialog(MainFrame parent) {
        super(parent, Labels.getProperty("addEmployee"), true);
        setSize(600, 400);
        setResizable(false);
        setLocationRelativeTo(parent);

        CEDButtons ced = new CEDButtons(Labels.getProperty("user_ced"));
        createEmployeeButton = ced.getCreateButton();
        createEmployeeButton.setIcon(Icons.NEW_USER_GREEN);
        editEmployeeButton = ced.getEditButton();
        deleteEmployeeButton = ced.getDeleteButton();
        deleteEmployeeButton.setIcon(Icons.DELETE_USER_RED);
        editEmployeeButton.setEnabled(false);
        deleteEmployeeButton.setEnabled(false);

        selectedModel = EmptyModel.EMPLOYEE;

        tableModel = new EmployeeTableModel();
        table = new JTable(tableModel);

        JButton closeButton = new JButton(Labels.getProperty("closeBtn"));

        JPanel topButtonsPanel = new JPanel();
        JPanel downButtonsPanel = new JPanel();

        topButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topButtonsPanel.setBorder(BorderFactory.createEtchedBorder());
        topButtonsPanel.add(createEmployeeButton);
        topButtonsPanel.add(editEmployeeButton);
        topButtonsPanel.add(deleteEmployeeButton);

        downButtonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        downButtonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        downButtonsPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(topButtonsPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(downButtonsPanel, BorderLayout.SOUTH);

        createEmployeeButton.addActionListener(e -> parent.getCreateEmployeeDialog().setVisible(true));
        editEmployeeButton.addActionListener(e -> {
            if (!selectedModel.equals(EmptyModel.EMPLOYEE)) {
                parent.getCreateEmployeeDialog().setEmployeeModel(selectedModel);
            }
        });

        deleteEmployeeButton.addActionListener(e -> {
            if (!selectedModel.equals(EmptyModel.EMPLOYEE) && ced.deleteEntry(parent, selectedModel.toString())) {
                selectedModel.setDeleted();
                listener.persistModelEventOccurred(selectedModel);
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                int row = table.rowAtPoint(ev.getPoint());
                table.getSelectionModel().setSelectionInterval(row, row);

                if (ev.getButton() == MouseEvent.BUTTON1) {
                   selectedModel = (EmployeeModel) table.getValueAt(row, 0);
                    editEmployeeButton.setEnabled(true);
                    deleteEmployeeButton.setEnabled(true);
                }
            }
        });

        closeButton.addActionListener(e -> setVisible(false));
    }

    public void setEmpTableData(List<EmployeeModel> db){
        tableModel.setData(db);
        tableModel.fireTableDataChanged();

        table.setAutoCreateRowSorter(true);
    }

    public void setEmployeeDialogListener(EditEmployeeDialogListener empListener){
        this.listener = empListener;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible && listener != null) {
            listener.getAllEmployees();
        }
        super.setVisible(visible);
    }
}
