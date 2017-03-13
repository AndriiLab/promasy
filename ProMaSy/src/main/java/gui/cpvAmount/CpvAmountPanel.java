package gui.cpvAmount;

import gui.MainFrame;
import gui.commons.Colors;
import model.models.CpvAmountModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

/**
 * Panel for {@link model.models.CpvAmountModel}
 */
public class CpvAmountPanel extends JPanel {
    private CpvAmountTableModel tableModel;
    private JTable table;
    private CpvAmountDetailDialog dialog;

    public CpvAmountPanel(MainFrame parent) {
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
        table.getColumnModel().getColumn(0).setMinWidth(650);
        dialog = new CpvAmountDetailDialog(parent);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int[] selectedRows = table.getSelectedRows();
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (selectedRows.length == 1) {
                        CpvAmountModel model = (CpvAmountModel) table.getValueAt(selectedRows[0], -1);
                        dialog.setData(model);
                    }
                }
            }
        });
    }

    public void setTableData(List<CpvAmountModel> db) {
        tableModel.setData(db);
        table.setAutoCreateRowSorter(true);
        tableModel.fireTableDataChanged();
    }
}
