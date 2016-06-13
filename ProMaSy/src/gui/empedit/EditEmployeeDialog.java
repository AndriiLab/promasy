package gui.empedit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;

import gui.Labels;
import gui.Utils;
import model.EmployeeModel;

public class EditEmployeeDialog extends JDialog {

    private EditEmployeeDialogListener listener;
    private CreateEmployeeDialog createEmployeeDialog;
    private JTable table;
    private EmployeeTableModel tableModel;
    private EmployeeModel selectedModel;
    private final EmployeeModel emptyEmployeeModel = new EmployeeModel();

    public EditEmployeeDialog(JFrame parent) {
        super(parent, Labels.getProperty("addEmployee"), false);
        setSize(600, 400);
        setResizable(false);
        setLocationRelativeTo(parent);
        createEmployeeDialog = new CreateEmployeeDialog(parent);

        Dimension buttonDim = new Dimension(25, 25);

        JButton createEmployeeButton = new JButton();
        createEmployeeButton.setToolTipText(Labels.getProperty("createNewEmployee"));
        createEmployeeButton.setIcon(Utils.createIcon("/images/Add16.gif"));
        createEmployeeButton.setPreferredSize(buttonDim);
        createEmployeeButton.setEnabled(true);

        JButton editEmployeeButton = new JButton();
        editEmployeeButton.setToolTipText(Labels.getProperty("editEmployee"));
        editEmployeeButton.setIcon(Utils.createIcon("/images/Edit16.gif"));
        editEmployeeButton.setPreferredSize(buttonDim);
        editEmployeeButton.setEnabled(true);

        JButton deleteEmployeeButton = new JButton();
        deleteEmployeeButton.setToolTipText(Labels.getProperty("deleteEmployee"));
        deleteEmployeeButton.setIcon(Utils.createIcon("/images/Delete16.gif"));
        deleteEmployeeButton.setPreferredSize(buttonDim);
        deleteEmployeeButton.setEnabled(true);

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

        createEmployeeButton.addActionListener(e -> createEmployeeDialog.setVisible(true));
        editEmployeeButton.addActionListener(e -> {
            if (!selectedModel.equals(emptyEmployeeModel)){
                createEmployeeDialog.setEmployeeModel(selectedModel);
            }
        });

        deleteEmployeeButton.addActionListener(e -> {
            if (!selectedModel.equals(emptyEmployeeModel)) {
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
                }
            }
        });

        closeButton.addActionListener(e -> setVisible(false));
    }

    public void setEmpTableData(List<EmployeeModel> db){
        tableModel.setData(db);
    }

    public void setEmployeeDialogListener(EditEmployeeDialogListener empListener){
        this.listener = empListener;
    }

    public void refresh(){
        tableModel.fireTableDataChanged();
    }

    public CreateEmployeeDialog getCreateEmployeeDialog(){
        return createEmployeeDialog;
    }

}
