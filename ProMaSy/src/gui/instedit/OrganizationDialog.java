package gui.instedit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import gui.Labels;
import gui.Utils;
import model.DepartmentModel;
import model.InstituteModel;
import model.SubdepartmentModel;

public class OrganizationDialog extends JDialog implements ActionListener {

    private JButton closeButton;
    private JButton createInstButton;
    private JButton editInstButton;
    private JButton deleteInstButton;
    private JButton createDepButton;
    private JButton editDepButton;
    private JButton deleteDepButton;
    private JButton createSubdepButton;
    private JButton editSubdepButton;
    private JButton deleteSubdepButton;
    private JComboBox<InstituteModel> instituteBox;
    private JComboBox<DepartmentModel> departmentBox;
    private JComboBox<SubdepartmentModel> subdepartmentBox;
    private OrganizationDialogListener orgListener;
    private String newInstName;
    private String newDepName;
    private String newSubdepName;
    private InstituteModel privateInstModel;
    private DepartmentModel privateDepModel;
    private SubdepartmentModel privateSybdepModel;
    private final InstituteModel emptyInstituteModel = new InstituteModel();
    private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
    private final SubdepartmentModel emptySubdepartmentModel = new SubdepartmentModel();


    public OrganizationDialog(JFrame parent) {
        super(parent, Labels.getProperty("addEditOrganizationAndDepartments"), false);
        setSize(610, 200);
        setResizable(false);
        setLocationRelativeTo(parent);

        Dimension buttonDim = new Dimension(25, 25);
        Dimension comboBoxDim = new Dimension(400, 25);

        privateInstModel = emptyInstituteModel;
        privateDepModel = emptyDepartmentModel;
        privateSybdepModel = emptySubdepartmentModel;

        //Set up institute combo box and edit buttons
        DefaultComboBoxModel<InstituteModel> instModel = new DefaultComboBoxModel<>();
        instituteBox = new JComboBox<>(instModel);
        instituteBox.addItem(emptyInstituteModel);
        instituteBox.setEditable(true);
        instituteBox.addActionListener(this);
        instituteBox.setPreferredSize(comboBoxDim);

        createInstButton = new JButton();
        createInstButton.setToolTipText(Labels.getProperty("addInstitute"));
        createInstButton.setIcon(Utils.createIcon("/images/Add16.gif"));
        createInstButton.setPreferredSize(buttonDim);
        createInstButton.setEnabled(true);
        editInstButton = new JButton();
        editInstButton.setToolTipText(Labels.getProperty("editInstitite"));
        editInstButton.setIcon(Utils.createIcon("/images/Edit16.gif"));
        editInstButton.setPreferredSize(buttonDim);
        editInstButton.setEnabled(true);
        deleteInstButton = new JButton();
        deleteInstButton.setToolTipText(Labels.getProperty("delInstitite"));
        deleteInstButton.setIcon(Utils.createIcon("/images/Delete16.gif"));
        deleteInstButton.setPreferredSize(buttonDim);
        deleteInstButton.setEnabled(true);

        //Set up department combo box and edit buttons
        DefaultComboBoxModel<DepartmentModel> depModel = new DefaultComboBoxModel<>();
        departmentBox = new JComboBox<>(depModel);
        departmentBox.addItem(emptyDepartmentModel);
        departmentBox.setEditable(true);
        departmentBox.addActionListener(this);
        departmentBox.setPreferredSize(comboBoxDim);

        createDepButton = new JButton();
        createDepButton.setToolTipText(Labels.getProperty("addDepartment"));
        createDepButton.setIcon(Utils.createIcon("/images/Add16.gif"));
        createDepButton.setPreferredSize(buttonDim);
        createDepButton.setEnabled(true);
        editDepButton = new JButton();
        editDepButton.setToolTipText(Labels.getProperty("editDepartment"));
        editDepButton.setIcon(Utils.createIcon("/images/Edit16.gif"));
        editDepButton.setPreferredSize(buttonDim);
        editDepButton.setEnabled(true);
        deleteDepButton = new JButton();
        deleteDepButton.setToolTipText(Labels.getProperty("delDepartment"));
        deleteDepButton.setIcon(Utils.createIcon("/images/Delete16.gif"));
        deleteDepButton.setPreferredSize(buttonDim);
        deleteDepButton.setEnabled(true);

        //Set up SubDepartment combo box and edit buttons
        DefaultComboBoxModel<SubdepartmentModel> subdepModel = new DefaultComboBoxModel<>();
        subdepartmentBox = new JComboBox<>(subdepModel);
        subdepartmentBox.addItem(emptySubdepartmentModel);
        subdepartmentBox.setEditable(true);
        subdepartmentBox.addActionListener(this);
        subdepartmentBox.setPreferredSize(comboBoxDim);

        createSubdepButton = new JButton();
        createSubdepButton.setToolTipText(Labels.getProperty("addSubdepartment"));
        createSubdepButton.setIcon(Utils.createIcon("/images/Add16.gif"));
        createSubdepButton.setPreferredSize(buttonDim);
        createSubdepButton.setEnabled(true);
        editSubdepButton = new JButton();
        editSubdepButton.setToolTipText(Labels.getProperty("editSubdepartment"));
        editSubdepButton.setIcon(Utils.createIcon("/images/Edit16.gif"));
        editSubdepButton.setPreferredSize(buttonDim);
        editSubdepButton.setEnabled(true);
        deleteSubdepButton = new JButton();
        deleteSubdepButton.setToolTipText(Labels.getProperty("delSubdepartment"));
        deleteSubdepButton.setIcon(Utils.createIcon("/images/Delete16.gif"));
        deleteSubdepButton.setPreferredSize(buttonDim);
        deleteSubdepButton.setEnabled(true);

        closeButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        editButtonListeners();

        closeButton.addActionListener(e -> {
            instituteBox.setSelectedIndex(0);
            setVisible(false);
        });
    }

    public void setInstData(List<InstituteModel> instDb) {
        for (InstituteModel anInstDb : instDb) {
            instituteBox.addItem(anInstDb);
        }
    }

    public void setDepData(List<DepartmentModel> depDb) {
        for (DepartmentModel aDepDb : depDb) {
            departmentBox.addItem(aDepDb);
        }
    }

    public void setSubdepData(List<SubdepartmentModel> subdepDb) {
        for (SubdepartmentModel aSubdepDb : subdepDb) {
            subdepartmentBox.addItem(aSubdepDb);
        }
    }

    public void setOrganizationDialogListener(OrganizationDialogListener organizationDialogListener) {
        this.orgListener = organizationDialogListener;
    }

    public void actionPerformed(ActionEvent ev) {
        JComboBox box = (JComboBox) ev.getSource();
        Object obj = box.getSelectedItem();

        if (box == instituteBox) {
            departmentBox.removeAllItems();
            if (obj instanceof InstituteModel) {
                if (!instituteBox.getSelectedItem().equals(emptyInstituteModel)) {
                    privateInstModel = (InstituteModel) instituteBox.getSelectedItem();
                    long instId = privateInstModel.getInstId();
                    if (orgListener != null) {
                        orgListener.instSelectionEventOccurred(instId);
                    }
                }
            } else if (obj instanceof String) {
                String name = (String) instituteBox.getSelectedItem();
                if (!name.equals("")) {
                    newInstName = name;
                }
            }

        } else if (box == departmentBox) {
            if (obj == null) {
                departmentBox.addItem(emptyDepartmentModel);
            }
            subdepartmentBox.removeAllItems();
            subdepartmentBox.addItem(emptySubdepartmentModel);
            if (obj instanceof DepartmentModel) {
                if (!departmentBox.getSelectedItem().equals(emptyDepartmentModel)) {
                    privateDepModel = (DepartmentModel) departmentBox.getSelectedItem();
                    long depId = privateDepModel.getDepId();
                    if (orgListener != null) {
                        orgListener.depSelectionEventOccurred(depId);
                    }
                }
            } else if (obj instanceof String) {
                String name = (String) departmentBox.getSelectedItem();
                if (!name.equals("")) {
                    newDepName = name;
                }
            }
        } else if (box == subdepartmentBox) {
            if (obj instanceof SubdepartmentModel) {
                if (!subdepartmentBox.getSelectedItem().equals(emptySubdepartmentModel)) {
                    privateSybdepModel = (SubdepartmentModel) subdepartmentBox.getSelectedItem();
                }
            } else if (obj instanceof String) {
                String name = (String) subdepartmentBox.getSelectedItem();
                if (!name.equals("")) {
                    newSubdepName = name;
                }
            }
        }
    }

    private void layoutControls() {
        JPanel institutePanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 5;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border instituteBorder = BorderFactory.createEtchedBorder();

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        institutePanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, instituteBorder));

        Insets smallPadding = new Insets(1, 0, 1, 5);
        Insets noPadding = new Insets(1, 0, 1, 0);

        institutePanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        ////// First row//////
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        institutePanel.add(new JLabel(Labels.getProperty("institute") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = smallPadding;
        institutePanel.add(instituteBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        institutePanel.add(createInstButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        institutePanel.add(editInstButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        institutePanel.add(deleteInstButton, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        institutePanel.add(new JLabel(Labels.getProperty("department") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = smallPadding;
        institutePanel.add(departmentBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = noPadding;
        institutePanel.add(createDepButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = noPadding;
        institutePanel.add(editDepButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = noPadding;
        institutePanel.add(deleteDepButton, gc);

        ////// Next row//////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = smallPadding;
        institutePanel.add(new JLabel(Labels.getProperty("subdepartment") + ":"), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = smallPadding;
        institutePanel.add(subdepartmentBox, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = noPadding;
        institutePanel.add(createSubdepButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = noPadding;
        institutePanel.add(editSubdepButton, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = noPadding;
        institutePanel.add(deleteSubdepButton, gc);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(institutePanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void editButtonListeners() {

        createInstButton.addActionListener(e -> {
            if (newInstName != null && privateInstModel.equals(emptyInstituteModel)) {
                InstituteModel model = new InstituteModel(newInstName);
                if (orgListener != null) {
                    instituteBox.removeAllItems();
                    instituteBox.addItem(emptyInstituteModel);
                    orgListener.createInstEventOccurred(model);
                }
            }
            newInstName = null;
            privateInstModel = emptyInstituteModel;
        });

        editInstButton.addActionListener(e -> {
            if (newInstName != null && !privateInstModel.equals(emptyInstituteModel)) {
                privateInstModel.setInstName(newInstName);
                if (orgListener != null) {
                    instituteBox.removeAllItems();
                    instituteBox.addItem(emptyInstituteModel);
                    orgListener.editInstEventOccurred(privateInstModel);
                }
            }
            newInstName = null;
            privateInstModel = emptyInstituteModel;
        });

        deleteInstButton.addActionListener(e -> {
            if (!privateInstModel.equals(emptyInstituteModel) && orgListener != null) {
                instituteBox.removeAllItems();
                instituteBox.addItem(emptyInstituteModel);
                orgListener.deleteInstEventOccurred(privateInstModel);
            }
            newInstName = null;
            privateInstModel = emptyInstituteModel;
        });
        createDepButton.addActionListener(e -> {
            if (!privateInstModel.equals(emptyInstituteModel)
                    && privateDepModel.equals(emptyDepartmentModel)
                    && newDepName != null) {
                DepartmentModel model = new DepartmentModel(newDepName,
                        privateInstModel.getInstId());
                if (orgListener != null) {
                    departmentBox.removeAllItems();
                    departmentBox.addItem(emptyDepartmentModel);
                    orgListener.createDepEventOccurred(model);
                }
            }
            newDepName = null;
            privateDepModel = emptyDepartmentModel;
        });
        editDepButton.addActionListener(e -> {
            if (newDepName != null && !privateDepModel.equals(emptyDepartmentModel)) {
                privateDepModel.setDepName(newDepName);
                if (orgListener != null) {
                    departmentBox.removeAllItems();
                    departmentBox.addItem(emptyDepartmentModel);
                    orgListener.editDepEventOccurred(privateDepModel);
                }
            }
            newDepName = null;
            privateDepModel = emptyDepartmentModel;
        });

        deleteDepButton.addActionListener(e -> {
            if (!privateDepModel.equals(emptyDepartmentModel) && orgListener != null) {
                departmentBox.removeAllItems();
                orgListener.deleteDepEventOccurred(privateDepModel);
            }
            newDepName = null;
            privateDepModel = emptyDepartmentModel;
        });

        createSubdepButton.addActionListener(e -> {
            if (!privateDepModel.equals(emptyDepartmentModel)
                    && privateSybdepModel.equals(emptySubdepartmentModel)
                    && newSubdepName != null) {
                SubdepartmentModel model = new SubdepartmentModel(newSubdepName,
                        privateDepModel.getDepId());
                if (orgListener != null) {
                    subdepartmentBox.removeAllItems();
                    subdepartmentBox.addItem(emptySubdepartmentModel);
                    orgListener.createSubdepEventOccurred(model);
                }
            }
            newSubdepName = null;
            privateSybdepModel = emptySubdepartmentModel;
        });

        editSubdepButton.addActionListener(e -> {
            if (newSubdepName != null && !privateSybdepModel.equals(emptySubdepartmentModel)) {
                privateSybdepModel.setSubdepName(newSubdepName);
                if (orgListener != null) {
                    subdepartmentBox.removeAllItems();
                    subdepartmentBox.addItem(emptySubdepartmentModel);
                    orgListener.editSubdepEventOccurred(privateSybdepModel);
                }
            }
            newSubdepName = null;
            privateSybdepModel = emptySubdepartmentModel;
        });

        deleteSubdepButton.addActionListener(e -> {
            if (!privateSybdepModel.equals(emptySubdepartmentModel) && orgListener != null) {
                subdepartmentBox.removeAllItems();
                subdepartmentBox.addItem(emptySubdepartmentModel);
                orgListener.deleteSubdepEventOccurred(privateSybdepModel);
            }
            newSubdepName = null;
            privateSybdepModel = emptySubdepartmentModel;
        });
    }
}
