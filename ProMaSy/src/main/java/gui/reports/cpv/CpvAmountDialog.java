package gui.reports.cpv;

import com.github.lgooddatepicker.components.DatePicker;
import controller.ReportsGenerator;
import gui.MainFrame;
import gui.commons.Colors;
import gui.commons.Icons;
import gui.commons.Labels;
import gui.components.PJOptionPane;
import model.dao.LoginData;
import model.enums.ProcurementProcedure;
import model.enums.Role;
import model.models.CpvAmountModel;
import model.models.EmptyModel;
import model.models.InstituteModel;
import model.models.report.CpvAmountReportModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * Panel for {@link model.models.CpvAmountModel}
 */
public class CpvAmountDialog extends JDialog {
    private CpvAmountTableModel tableModel;
    private JTable table;
    private CpvAmountDetailDialog dialog;
    private CpvAmountDialogListener listener;
    private MainFrame parent;
    private JTextField resolutionField;
    private DatePicker resolutionDatePicker;

    public CpvAmountDialog(MainFrame parent) {
        super(parent, Labels.getProperty("cpvAmounts"), true);
        this.parent = parent;
        setSize(1000, 500);
        setResizable(false);
        setLocationRelativeTo(parent);

        JButton printButton = new JButton(Icons.PRINT);
        printButton.setPreferredSize(new Dimension(25, 25));
        printButton.setToolTipText(Labels.getProperty("print"));

        JButton closeButton = new JButton(Labels.getProperty("closeBtn"));


        tableModel = new CpvAmountTableModel();
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                BigDecimal totalAmount = (BigDecimal) table.getModel().getValueAt(row, 2);
                if (totalAmount.compareTo(new BigDecimal(50000)) < 0) {
                    comp.setBackground(isRowSelected(row) ? Colors.GREEN_LIGHT_SELECTED : Colors.GREEN_LIGHT);
                } else {
                    comp.setBackground(isRowSelected(row) ? Colors.RED_LIGHT_SELECTED : Colors.RED_LIGHT);
                }
                return comp;
            }
        };
        table.getColumnModel().getColumn(0).setMinWidth(450);
        table.getColumnModel().getColumn(3).setMinWidth(130);
        setupEditableCells();

        dialog = new CpvAmountDetailDialog(parent);

        resolutionField = new JTextField(3);

        resolutionDatePicker = new DatePicker();
        resolutionDatePicker.setLocale(Locale.getDefault());

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        JPanel leftButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        leftButtonsPanel.add(printButton);
        leftButtonsPanel.add(new JLabel(Labels.withColon("resolutionFrom")));
        leftButtonsPanel.add(resolutionDatePicker);
        leftButtonsPanel.add(new JLabel("#"));
        leftButtonsPanel.add(resolutionField);

        rightButtonsPanel.add(closeButton);

        buttonsPanel.add(leftButtonsPanel, BorderLayout.WEST);
        buttonsPanel.add(rightButtonsPanel, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int[] selectedRows = table.getSelectedRows();
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (selectedRows.length == 1 && table.getSelectedColumn() == 0) {
                        CpvAmountReportModel model = (CpvAmountReportModel) table.getValueAt(selectedRows[0], -1);
                        dialog.setData(model.getParentModel());
                    }
                }
            }
        });

        printButton.addActionListener(e -> onPrintClick());
        closeButton.addActionListener(e -> setVisible(false));
    }

    private void onPrintClick() {
        List<CpvAmountReportModel> list = new LinkedList<>();
        for (int row = 0; row < table.getRowCount(); row++) {
            CpvAmountReportModel model = (CpvAmountReportModel) table.getValueAt(row, -1);
            list.add(model);
        }

        if (resolutionDatePicker.getDate() == null) {
            PJOptionPane.emptyField(parent, Labels.getProperty("resolutionDate"));
            return;
        }

        String resolutionDate = resolutionDatePicker.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String resolutionNum = resolutionField.getText();
        if (resolutionNum.isEmpty()) {
            PJOptionPane.emptyField(parent, Labels.getProperty("resolutionNum"));
            return;
        }

        InstituteModel institute = LoginData.getInstance().getSubdepartment().getDepartment().getInstitute();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("year", Year.now().getValue());
        parameters.put("organization", institute.getInstName());
        parameters.put("edrpou", institute.getEDRPOU());
        parameters.put("headTender", listener.getEmployeeName(Role.HEAD_OF_TENDER_COMMITTEE));
        parameters.put("secretaryTender", listener.getEmployeeName(Role.SECRETARY_OF_TENDER_COMMITTEE));
        parameters.put("resolutionDate", resolutionDate);
        parameters.put("resolutionNum", resolutionNum);

        this.setVisible(false);
        new ReportsGenerator(ReportsGenerator.CPV_AMOUNT_REPORT, parameters, Collections.unmodifiableList(list), parent);
    }

    private void setupEditableCells() {
        JComboBox<ProcurementProcedure> procProcCombo = new JComboBox<>(ProcurementProcedure.values());
        TableColumn targetColumn = table.getColumnModel().getColumn(3);

        targetColumn.setCellEditor(new DefaultCellEditor(procProcCombo));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText(Labels.getProperty("selectProcProc"));
        targetColumn.setCellRenderer(renderer);
    }

    public void setTableData(List<CpvAmountModel> db) {
        List<CpvAmountReportModel> list = new LinkedList<>();
        for (CpvAmountModel model : db) {
            list.add(model.generateCpvAmountReportModel());
        }
        tableModel.setData(list);
        table.setAutoCreateRowSorter(true);
        tableModel.fireTableDataChanged();
    }

    public void setListener(CpvAmountDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            resolutionField.setText(EmptyModel.STRING);
            resolutionDatePicker.clear();
            if (listener != null) {
                listener.getData();
            }
        }
        super.setVisible(visible);
    }
}
