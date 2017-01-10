package gui.cpv;

import gui.Icons;
import gui.Labels;
import gui.MainFrame;
import model.models.CPVModel;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CpvDialog extends JDialog {

	private JTextField searchField;
	private JButton upButton;
	private JButton selectButton;
	private CpvTableModel cpvTableModel;
	private JTable table;
	private CpvSearchListener cpvListener;
	

	public CpvDialog (MainFrame parent) {
        super(parent, Labels.getProperty("cpvPanelTab"), true);
        setSize(800, 400);
        setResizable(false);
        setLocationRelativeTo(parent);

		cpvTableModel = new CpvTableModel();
		table = new JTable(cpvTableModel);
		JPanel searchPanel = new JPanel();
		searchField = new JTextField(43);

        JButton homeButton = new JButton(Icons.HOME);
        homeButton.setToolTipText(Labels.getProperty("goToTopCategory"));

        upButton = new JButton(Icons.LEVEL_UP);
        upButton.setToolTipText(Labels.getProperty("upCategory"));
		upButton.setEnabled(false);

        JButton searchButton = new JButton(Icons.SEARCH);
        searchButton.setToolTipText(Labels.getProperty("search"));

        selectButton = new JButton(Labels.getProperty("selectCode"));
        selectButton.setEnabled(false);

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
            makeCpvQuery("", true);
            searchField.setText(null);
            selectButton.setEnabled(false);
        });
        
        upButton.addActionListener(e -> {
            selectButton.setEnabled(false);
            String cpvRequest = searchField.getText();
            if (cpvRequest.length() > 8) {
                cpvRequest = cpvRequest.substring(0, 6);
                while (cpvRequest.length()>2 && cpvRequest.endsWith("0")) {
                    cpvRequest = cpvRequest.substring(0, cpvRequest.length() - 1);
                }
            }
            cpvRequest = cpvRequest.substring(0, cpvRequest.length() - 1);
            if (cpvRequest.length() <= 1){
                cpvRequest = "";
                searchField.setText(null);
                upButton.setEnabled(false);
            }
            searchField.setText(cpvRequest);
            makeCpvQuery(cpvRequest, false);
        });
        
		searchButton.addActionListener(e -> {
            upButton.setEnabled(false);
            String cpvRequest = searchField.getText();
            makeCpvQuery(cpvRequest, true);
        });
		
		selectButton.addActionListener(e -> {
            String selectedCPV = searchField.getText();
            if (selectedCPV.length() > 10){
                selectedCPV =  selectedCPV.substring(0,10);
            }
            parent.setCpvCode(selectedCPV);
            setVisible(false);
        });

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				int row = table.rowAtPoint(ev.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);

				if (ev.getButton() == MouseEvent.BUTTON1) {
					upButton.setEnabled(true);
					CPVModel selectedCpvModel = (CPVModel) cpvTableModel.getValueAt(row, 1);
					String cpvRequest = selectedCpvModel.getCpvId();
					boolean isTerminal = selectedCpvModel.isCpvTerminal();
					searchField.setText(cpvRequest+" "+selectedCpvModel.getCpvUkr());
					if(!isTerminal){
						makeCpvQuery(cpvRequest, false);
					}
					selectButton.setEnabled(isTerminal || selectedCpvModel.getCpvLevel()>3);
				}
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

	public void refresh() {
		cpvTableModel.fireTableDataChanged();
	}

	public void setCpvListener(CpvSearchListener cpvListener) {
		this.cpvListener = cpvListener;

	}

    public void makeCpvQuery(String cpvRequest, boolean sameLvlShow) {

        CpvReqEvent ev = new CpvReqEvent(this, cpvRequest, sameLvlShow);

		if (cpvListener != null) {
            cpvListener.cpvSelectionEventOccurred(ev);
            refresh();
		}
	}

    @Override
    public void setVisible(boolean visible) {
        if (visible && cpvListener != null) {
            cpvListener.getTopCodes();
        }
        super.setVisible(visible);
    }
}