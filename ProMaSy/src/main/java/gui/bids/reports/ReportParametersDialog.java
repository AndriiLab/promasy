package main.java.gui.bids.reports;

import main.java.gui.Labels;
import main.java.gui.MainFrame;
import main.java.gui.Utils;
import main.java.model.EmployeeModel;
import main.java.model.RoleModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

/**
 * Created by laban on 22.06.2016.
 */
public class ReportParametersDialog extends JDialog {

	private JComboBox<RoleModel> roleBox;
	private JComboBox<EmployeeModel> headBox;
	private JComboBox<EmployeeModel> departmentHeadBox;
	private JComboBox<EmployeeModel> personallyLiableEmpBox;
	private JComboBox<EmployeeModel> accountantBox;
	private JComboBox<EmployeeModel> economistBox;
	private final RoleModel emptyRoleModel = new RoleModel();
	private final EmployeeModel emptyEmployeeModel = new EmployeeModel();
	private JButton okButton;
	private JButton cancelButton;
	private ReportParametersDialogListener listener;
	private String roleName;
	private String headName;
	private String departmentHeadName;
	private String personallyLiableEmpName;
	private String accountantName;
	private String economistName;
	private MainFrame parent;

	public ReportParametersDialog(MainFrame parent) {
		super(parent, Labels.getProperty("reportParameters"), true);
		this.parent = parent;
		setSize(330, 340);
		setResizable(false);
		setLocationRelativeTo(this.parent);

		Dimension preferredFieldDim = new Dimension(235, 15);

		roleBox = new JComboBox<>();
		roleBox.setSize(preferredFieldDim);
		roleBox.addItem(emptyRoleModel);

		headBox = new JComboBox<>();
		headBox.setSize(preferredFieldDim);
		headBox.addItem(emptyEmployeeModel);

		departmentHeadBox = new JComboBox<>();
		departmentHeadBox.setSize(preferredFieldDim);
		departmentHeadBox.addItem(emptyEmployeeModel);

		personallyLiableEmpBox = new JComboBox<>();
		personallyLiableEmpBox.setSize(preferredFieldDim);
		personallyLiableEmpBox.addItem(emptyEmployeeModel);

		accountantBox = new JComboBox<>();
		accountantBox.setSize(preferredFieldDim);
		accountantBox.addItem(emptyEmployeeModel);

		economistBox = new JComboBox<>();
		economistBox.setSize(preferredFieldDim);
		economistBox.addItem(emptyEmployeeModel);

		okButton = new JButton(Labels.getProperty("print"));

		cancelButton = new JButton(Labels.getProperty("cancel"));

		createLayout();

		roleBox.addActionListener(e -> {
			if (listener != null) {
				int roleId = ((RoleModel) roleBox.getSelectedItem()).getRoleId();
				if (roleId != 0) {
					listener.roleSelectionOccurred(roleId);
				} else {
					headBox.removeAllItems();
					headBox.addItem(emptyEmployeeModel);
				}
			}
		});

		okButton.addActionListener(e -> {
			if (checkBoxes() && listener != null) {
				listener.ReportParametersSelectionOccurred(new ReportParametersEvent(roleName, headName,
						departmentHeadName, personallyLiableEmpName, accountantName, economistName));
				setVisible(false);
				clear();
			}
		});

		cancelButton.addActionListener(e -> {

			setVisible(false);
			clear();
		});
	}

	private boolean checkBoxes() {
		roleName = roleBox.getSelectedItem().toString();
		if (roleName == null || roleName.equals("")) {
			Utils.emptyFieldError(parent, Labels.getProperty("headPostion"));
			return false;
		}

		headName = headBox.getSelectedItem().toString();
		if (headName == null || headName.equals("")) {
			Utils.emptyFieldError(parent, Labels.getProperty("headOrganization"));
			return false;
		}
		departmentHeadName = departmentHeadBox.getSelectedItem().toString();

		personallyLiableEmpName = personallyLiableEmpBox.getSelectedItem().toString();

		accountantName = accountantBox.getSelectedItem().toString();
		if (accountantName == null || accountantName.equals("")) {
			Utils.emptyFieldError(parent, Labels.getProperty("chiefAccountant"));
			return false;
		}

		economistName = economistBox.getSelectedItem().toString();
		if (economistName == null || economistName.equals("")) {
			Utils.emptyFieldError(parent, Labels.getProperty("chiefEconomist"));
			return false;
		}

		return true;
	}

	private void clear() {
		roleName = "";
		headName = "";
		departmentHeadName = "";
		personallyLiableEmpName = "";
		accountantName = "";
		economistName = "";
	}

	public void setRoleBoxData(List<RoleModel> db) {
		for (RoleModel model : db) {
			// only if role is director(def id 1000) or deputy director (def id
			// 2000) add to box
			if (model.getRoleId() == 1000 || model.getRoleId() == 2000) {
				roleBox.addItem(model);
			}
		}
	}

	public void setHeadBoxData(List<EmployeeModel> db) {
		headBox.removeAllItems();
		if (db.isEmpty()) {
			headBox.addItem(emptyEmployeeModel);
		} else {
			for (EmployeeModel model : db) {
				headBox.addItem(model);
			}
		}
	}

	public void setDepartmentHeadBoxData(List<EmployeeModel> db) {
		departmentHeadBox.removeAllItems();
		if (db.isEmpty()) {
			departmentHeadBox.addItem(emptyEmployeeModel);
		} else {
			for (EmployeeModel model : db) {
				departmentHeadBox.addItem(model);
			}
		}
	}

	public void setPersonallyLiableEmpBoxData(List<EmployeeModel> db) {
		personallyLiableEmpBox.removeAllItems();
		if (db.isEmpty()) {
			personallyLiableEmpBox.addItem(emptyEmployeeModel);
		} else {
			for (EmployeeModel model : db) {
				personallyLiableEmpBox.addItem(model);
			}
		}
	}

	public void setAccountantBoxData(List<EmployeeModel> db) {
		accountantBox.removeAllItems();
		if (db.isEmpty()) {
			accountantBox.addItem(emptyEmployeeModel);
		} else {
			for (EmployeeModel model : db) {
				accountantBox.addItem(model);
			}
		}
	}

	public void setEconomistBoxData(List<EmployeeModel> db) {
		economistBox.removeAllItems();
		if (db.isEmpty()) {
			economistBox.addItem(emptyEmployeeModel);
		} else {
			for (EmployeeModel model : db) {
				economistBox.addItem(model);
			}
		}
	}

	public void setListener(ReportParametersDialogListener listener) {
		this.listener = listener;
	}

	private void createLayout() {

		int space = 5;
		Border emptyBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border etchedBorder = BorderFactory.createEtchedBorder();
		Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, etchedBorder);

		JPanel selectionPanel = new JPanel();
		selectionPanel.setBorder(compoundBorder);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));

		selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
		selectionPanel.add(new JLabel(Labels.getProperty("headPostion")));
		selectionPanel.add(roleBox);
		selectionPanel.add(new JLabel(Labels.getProperty("headOrganization")));
		selectionPanel.add(headBox);
		selectionPanel.add(new JLabel(Labels.getProperty("departmentHead")));
		selectionPanel.add(departmentHeadBox);
		selectionPanel.add(new JLabel(Labels.getProperty("personallyLiableEmployee")));
		selectionPanel.add(personallyLiableEmpBox);
		selectionPanel.add(new JLabel(Labels.getProperty("chiefAccountant")));
		selectionPanel.add(accountantBox);
		selectionPanel.add(new JLabel(Labels.getProperty("chiefEconomist")));
		selectionPanel.add(economistBox);

		okButton.setPreferredSize(cancelButton.getPreferredSize());
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);

		setLayout(new BorderLayout());
		add(selectionPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}
}
