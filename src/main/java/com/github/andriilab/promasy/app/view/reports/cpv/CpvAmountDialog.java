package com.github.andriilab.promasy.app.view.reports.cpv;

import com.github.andriilab.promasy.data.authorization.LoginData;
import com.github.andriilab.promasy.data.reports.ReportsGenerator;
import com.github.andriilab.promasy.data.reports.models.CpvAmountReportModel;
import com.github.andriilab.promasy.data.queries.employees.GetEmployeesQuery;
import com.github.andriilab.promasy.data.reports.models.CpvAmountsReportRequest;
import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.domain.bid.enums.ProcurementProcedure;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Colors;
import com.github.andriilab.promasy.app.commons.Icons;
import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.app.components.panes.ErrorOptionPane;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Panel for {@link CpvAmount}
 */
public class CpvAmountDialog extends JDialog {
    private final CpvAmountTableModel tableModel;
    private final JTable table;
    private final CpvAmountDetailDialog dialog;
    private CpvAmountDialogListener listener;
    private final MainFrame parent;
    private final JTextField resolutionField;
    private final DatePicker resolutionDatePicker;
    private final ReportsGenerator reportsGenerator;

    public CpvAmountDialog(MainFrame parent) {
        super(parent, Labels.getProperty("cpvAmounts"), true);
        this.parent = parent;
        reportsGenerator = new ReportsGenerator(parent);

        setSize(1000, 500);
        setResizable(false);
        setLocationRelativeTo(parent);

        listener = new EmptyCpvAmountDialogListener();
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
                if (e.getButton() == MouseEvent.BUTTON1 && selectedRows.length == 1 && table.getSelectedColumn() == 0) {
                    CpvAmountReportModel model = (CpvAmountReportModel) table.getValueAt(selectedRows[0], -1);
                    dialog.setData(model.getParentModel());
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
            ErrorOptionPane.emptyField(parent, Labels.getProperty("resolutionDate"));
            return;
        }

        String resolutionDate = resolutionDatePicker.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String resolutionNum = resolutionField.getText();
        if (resolutionNum.isEmpty()) {
            ErrorOptionPane.emptyField(parent, Labels.getProperty("resolutionNum"));
            return;
        }

        Institute institute = LoginData.getInstance().getSubdepartment().getDepartment().getInstitute();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("year", parent.getReportYear());
        parameters.put("organization", institute.getInstName());
        parameters.put("edrpou", institute.getEdrpou());
        parameters.put("headTender", listener.getEmployee(new GetEmployeesQuery(Role.HEAD_OF_TENDER_COMMITTEE)));
        parameters.put("secretaryTender", listener.getEmployee(new GetEmployeesQuery(Role.SECRETARY_OF_TENDER_COMMITTEE)));
        parameters.put("resolutionDate", resolutionDate);
        parameters.put("resolutionNum", resolutionNum);

        this.setVisible(false);
        reportsGenerator.showPrintDialog(new CpvAmountsReportRequest(parameters, Collections.unmodifiableList(list)));
    }

    private void setupEditableCells() {
        JComboBox<ProcurementProcedure> procProcCombo = new JComboBox<>(ProcurementProcedure.values());
        TableColumn targetColumn = table.getColumnModel().getColumn(3);

        targetColumn.setCellEditor(new DefaultCellEditor(procProcCombo));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText(Labels.getProperty("selectProcProc"));
        targetColumn.setCellRenderer(renderer);
    }

    public void setTableData(List<CpvAmount> db) {
        List<CpvAmountReportModel> list = db.stream().map(CpvAmountReportModel::new).collect(Collectors.toList());
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
            this.setTitle(Labels.getProperty("cpvAmounts") + " - " + parent.getReportYear());
            resolutionField.setText("");
            resolutionDatePicker.clear();
            listener.getData(parent.getReportYear());
        }
        super.setVisible(visible);
    }
}
