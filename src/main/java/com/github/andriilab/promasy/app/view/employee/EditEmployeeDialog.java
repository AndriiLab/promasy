package com.github.andriilab.promasy.app.view.employee;

import com.github.andriilab.promasy.data.commands.CreateCommand;
import com.github.andriilab.promasy.data.commands.DeleteCommand;
import com.github.andriilab.promasy.domain.EmptyModel;
import com.github.andriilab.promasy.domain.organization.entities.Employee;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Icons;
import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.app.components.dialogs.CEDButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EditEmployeeDialog extends JDialog {

    private EditEmployeeDialogListener listener;
    private final JButton editEmployeeButton;
    private final JButton deleteEmployeeButton;

    private final JTable table;
    private final EmployeeTableModel tableModel;
    private Employee selectedModel;

    public EditEmployeeDialog(MainFrame parent) {
        super(parent, Labels.getProperty("addEmployee"), true);
        setSize(600, 400);
        setResizable(false);
        setLocationRelativeTo(parent);

        listener = new EmptyEditEmployeeDialogListener();
        CEDButtons ced = new CEDButtons(Labels.getProperty("user_ced"));
        JButton createEmployeeButton = ced.getCreateButton();
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
                listener.persistModelEventOccurred(new DeleteCommand<>(selectedModel));
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                int row = table.rowAtPoint(ev.getPoint());
                table.getSelectionModel().setSelectionInterval(row, row);

                if (ev.getButton() == MouseEvent.BUTTON1) {
                    selectedModel = (Employee) table.getValueAt(row, 0);
                    editEmployeeButton.setEnabled(true);
                    deleteEmployeeButton.setEnabled(true);
                }
            }
        });

        closeButton.addActionListener(e -> setVisible(false));
    }

    public void setEmpTableData(List<Employee> db) {
        tableModel.setData(db);
        tableModel.fireTableDataChanged();

        table.setAutoCreateRowSorter(true);
    }

    public void setListener(EditEmployeeDialogListener empListener) {
        this.listener = empListener;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            listener.getAllEmployees();
        }
        super.setVisible(visible);
    }
}
