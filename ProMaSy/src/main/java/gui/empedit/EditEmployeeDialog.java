package main.java.gui.empedit;

import main.java.gui.CrEdDelButtons;
import main.java.gui.Labels;
import main.java.gui.MainFrame;
import main.java.model.EmployeeModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EditEmployeeDialog extends JDialog {

    private final EmployeeModel emptyEmployeeModel = new EmployeeModel();
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

        CrEdDelButtons ced = new CrEdDelButtons(Labels.getProperty("user_ced"));
        createEmployeeButton = ced.getCreateButton();
        editEmployeeButton = ced.getEditButton();
        deleteEmployeeButton = ced.getDeleteButton();
        editEmployeeButton.setEnabled(false);
        deleteEmployeeButton.setEnabled(false);

        selectedModel =  emptyEmployeeModel;

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
            if (!selectedModel.equals(emptyEmployeeModel)){
                parent.getCreateEmployeeDialog().setEmployeeModel(selectedModel);
            }
        });

        deleteEmployeeButton.addActionListener(e -> {
            if (!selectedModel.equals(emptyEmployeeModel) && ced.deleteEntry(parent, selectedModel.toString())) {
                listener.deleteEmployeeEventOccurred(selectedModel);
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
        refresh();
    }

    public void setEmployeeDialogListener(EditEmployeeDialogListener empListener){
        this.listener = empListener;
    }

    public void refresh(){
        tableModel.fireTableDataChanged();
    }

}
