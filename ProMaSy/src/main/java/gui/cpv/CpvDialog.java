package gui.cpv;

import gui.MainFrame;
import gui.commons.Icons;
import gui.commons.Labels;
import model.models.CPVModel;
import model.models.EmptyModel;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CpvDialog extends JDialog {

    private final JTextField searchField;
    private final JButton upButton;
    private final JButton selectButton;
    private final CpvTableModel cpvTableModel;
    private final JTable table;
    private CpvSearchListener cpvListener;
    private CPVModel selectedCpvModel;
    private final MainFrame parent;

    public CpvDialog(MainFrame parent) {
        super(parent, Labels.getProperty("cpvPanelTab"), true);
        this.parent = parent;
        setSize(800, 400);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        selectedCpvModel = EmptyModel.CPV;
        cpvTableModel = new CpvTableModel();
        table = new JTable(cpvTableModel);
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(43);

        Dimension buttonDim = new Dimension(40, 26);

        JButton homeButton = new JButton(Icons.HOME);
        homeButton.setToolTipText(Labels.getProperty("goToTopCategory"));
        homeButton.setPreferredSize(buttonDim);

        upButton = new JButton(Icons.LEVEL_UP);
        upButton.setToolTipText(Labels.getProperty("upCategory"));
        upButton.setEnabled(false);
        upButton.setPreferredSize(buttonDim);

        JButton searchButton = new JButton(Icons.SEARCH);
        searchButton.setToolTipText(Labels.getProperty("search"));
        searchButton.setPreferredSize(buttonDim);

        selectButton = new JButton(Labels.getProperty("selectCode"));
        selectButton.setEnabled(false);
//        selectButton.setPreferredSize(buttonDim);

        //set format for table
        table.getColumnModel().getColumn(0).setMaxWidth(150);

        PromptSupport.setPrompt(Labels.getProperty("searchFieldHint"), searchField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIGHLIGHT_PROMPT, searchField);
        searchField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                selectButton.setEnabled(false);
            }
        });

        homeButton.addActionListener(e -> {
            upButton.setEnabled(false);
            makeCpvQuery(EmptyModel.STRING, 0);
            searchField.setText(null);
            selectButton.setEnabled(false);
        });

        upButton.addActionListener(e -> {
            selectButton.setEnabled(false);
            String cpvRequest = searchField.getText();
            if (cpvRequest.length() > 8) {
                int zeroIndex = cpvRequest.indexOf("0", 2);
                if (zeroIndex != -1) {
                    cpvRequest = cpvRequest.substring(0, zeroIndex > 2 ? zeroIndex - 1 : zeroIndex);
                }
            }

            makeCpvQuery(cpvRequest, -1);

            cpvRequest = cpvRequest.substring(0, cpvRequest.length() - 1);
            if (cpvRequest.length() < 2) {
                upButton.setEnabled(false);
                cpvRequest = EmptyModel.STRING;
            }
            searchField.setText(cpvRequest);
        });

        searchButton.addActionListener(e -> {
            upButton.setEnabled(false);
            String cpvRequest = searchField.getText();
            makeCpvQuery(cpvRequest, 0);
        });

        selectButton.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(parent,
                    Labels.withColon("selectCpvCode") + selectedCpvModel.getCpvId() + "\n" +
                            selectedCpvModel.getCpvUkr() + "?",
                    Labels.getProperty("confirmAction"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    Icons.QUESTION);
            if (answer == JOptionPane.YES_OPTION) {
                parent.setCpvModel(selectedCpvModel);
                setVisible(false);
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ev) {
                int row = table.rowAtPoint(ev.getPoint());
                table.getSelectionModel().setSelectionInterval(row, row);

                if (ev.getButton() == MouseEvent.BUTTON1) {
                    upButton.setEnabled(true);
                    selectedCpvModel = (CPVModel) cpvTableModel.getValueAt(row, 1);
                    String cpvRequest = selectedCpvModel.getCpvId();
                    boolean isTerminal = selectedCpvModel.isCpvTerminal();
                    searchField.setText(cpvRequest + " " + selectedCpvModel.getCpvUkr());
                    if (!isTerminal) {
                        makeCpvQuery(cpvRequest, 1);
                    }
                    selectButton.setEnabled(isTerminal || selectedCpvModel.getCpvLevel() > 2);
                }
            }

        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });

        //seting search button as default button, for search by hitting enter
        this.getRootPane().setDefaultButton(searchButton);

        searchPanel.setLayout(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createEtchedBorder());
        searchPanel.add(homeButton);
        searchPanel.add(upButton);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(selectButton);

        setLayout(new BorderLayout());

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setData(List<CPVModel> db) {
        cpvTableModel.setData(db);
    }

    public void setCpvListener(CpvSearchListener cpvListener) {
        this.cpvListener = cpvListener;

    }

    private void makeCpvQuery(String cpvRequest, int depth) {

        CpvRequestContainer ev = new CpvRequestContainer(cpvRequest, depth);

        if (cpvListener != null) {
            cpvListener.cpvSelectionEventOccurred(ev);
            cpvTableModel.fireTableDataChanged();
        }
    }

    public void showWithCode(String code) {
        searchField.setText(code);
        makeCpvQuery(code, 0);
        super.setVisible(true);
    }

    @Override
    public void setVisible(boolean visible) {
        selectButton.setVisible(parent.getCreateBidPanel().isVisible());
        selectedCpvModel = EmptyModel.CPV;
        if (visible) {
            makeCpvQuery(EmptyModel.STRING, 0);
        } else if (!visible) {
            searchField.setText(EmptyModel.STRING);
            selectButton.setVisible(true);
        }
        super.setVisible(visible);
    }
}