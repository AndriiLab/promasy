package gui.cpv;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import gui.LabelsLocale;
import org.jdesktop.swingx.prompt.PromptSupport;

import gui.Utils;
import model.CPVModel;

public class CpvPanel extends JPanel {

	private JTextField searchField;
	private JButton upButton;
	private JButton selectButton;
	private CpvTableModel cpvTableModel;
	private JTable table;
	private CpvSearchListener cpvListener;
	

	public CpvPanel() {
		cpvTableModel = new CpvTableModel();
		table = new JTable(cpvTableModel);
		JPanel searchPanel = new JPanel();
		searchField = new JTextField(30);
		JButton homeButton = new JButton();
		upButton = new JButton();
		JButton searchButton = new JButton();
		selectButton = new JButton(LabelsLocale.getProperty("selectButtonLabel"));
		
		//set format for table		
		table.getColumnModel().getColumn(0).setMaxWidth(150);
		
		homeButton.setToolTipText(LabelsLocale.getProperty("homeButtonToolTipText"));
		homeButton.setIcon(Utils.createIcon("/images/Home16.gif"));
		upButton.setToolTipText(LabelsLocale.getProperty("upButtonToolTipText"));
		upButton.setIcon(Utils.createIcon("/images/Up16.gif"));
		upButton.setEnabled(false);
		searchButton.setToolTipText(LabelsLocale.getProperty("searchButtonToolTipText"));
		searchButton.setIcon(Utils.createIcon("/images/Find16.gif"));
		selectButton.setEnabled(false);

		PromptSupport.setPrompt(LabelsLocale.getProperty("searchFieldHint"), searchField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, searchField);
        searchField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				selectButton.setEnabled(false);
			}
		});
        
        homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upButton.setEnabled(false);
				makeCpvQuery("", true);
				searchField.setText(null);
			}
		});
        
        upButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
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
			}
		});
        
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upButton.setEnabled(false);
				String cpvRequest = searchField.getText();
				makeCpvQuery(cpvRequest, true);
			}
		});
		
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (searchField.getText().length()==10){
					System.out.println(searchField.getText());
				}
			}
		});

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				int row = table.rowAtPoint(ev.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);

				if (ev.getButton() == MouseEvent.BUTTON1) {
					upButton.setEnabled(true);
					CPVModel curentCpvModel = (CPVModel) cpvTableModel.getValueAt(row, 1);
					String cpvRequest = curentCpvModel.getCpvId();
					boolean isTerminal = curentCpvModel.isCpvTerminal();
					searchField.setText(cpvRequest);
					if(!isTerminal){
						makeCpvQuery(cpvRequest, false);
					}
					selectButton.setEnabled(isTerminal);
				}
			}

		});

		searchPanel.setLayout(new FlowLayout());
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
	
	public void makeCpvQuery(String cpvRequest, boolean sameLvlShow){
		
		CpvReqEvent ev = new CpvReqEvent(this, cpvRequest, sameLvlShow);

		if (cpvListener != null) {
			cpvListener.cpvEventOccured(ev);
		}
	}
	

}
