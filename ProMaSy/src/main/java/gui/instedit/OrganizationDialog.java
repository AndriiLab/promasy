package main.java.gui.instedit;

import main.java.gui.CrEdDelButtons;
import main.java.gui.Labels;
import main.java.model.DepartmentModel;
import main.java.model.InstituteModel;
import main.java.model.SubdepartmentModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class OrganizationDialog extends JDialog implements ActionListener {

    private final InstituteModel emptyInstituteModel = new InstituteModel();
    private final DepartmentModel emptyDepartmentModel = new DepartmentModel();
    private final SubdepartmentModel emptySubdepartmentModel = new SubdepartmentModel();
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
    private SubdepartmentModel privateSubdepModel;

    public OrganizationDialog(JFrame parent) {
        super(parent, Labels.getProperty("addEditOrganizationAndDepartments"), true);
        setSize(610, 200);
        setResizable(false);
        setLocationRelativeTo(parent);

        Dimension comboBoxDim = new Dimension(400, 25);

        privateInstModel = emptyInstituteModel;
        privateDepModel = emptyDepartmentModel;
        privateSubdepModel = emptySubdepartmentModel;

        //Set up institute combo box and edit buttons
        DefaultComboBoxModel<InstituteModel> instModel = new DefaultComboBoxModel<>();
        instituteBox = new JComboBox<>(instModel);
        instituteBox.addItem(emptyInstituteModel);
        instituteBox.setEditable(true);
        instituteBox.addActionListener(this);
        instituteBox.setPreferredSize(comboBoxDim);

        CrEdDelButtons cedInst = new CrEdDelButtons(Labels.getProperty("institute_ced"));
        createInstButton = cedInst.getCreateButton();
        editInstButton = cedInst.getEditButton();
        deleteInstButton = cedInst.getDeleteButton();
        editInstButton.setEnabled(false);
        deleteInstButton.setEnabled(false);

        //Set up department combo box and edit buttons
        DefaultComboBoxModel<DepartmentModel> depModel = new DefaultComboBoxModel<>();
        departmentBox = new JComboBox<>(depModel);
        departmentBox.addItem(emptyDepartmentModel);
        departmentBox.setEditable(true);
        departmentBox.setEnabled(false);
        departmentBox.addActionListener(this);
        departmentBox.setPreferredSize(comboBoxDim);
        departmentBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String depName = departmentBox.getEditor().getItem().toString();
                createDepButton.setEnabled(!depName.isEmpty());
            }
        });

        CrEdDelButtons cedDepartment = new CrEdDelButtons(Labels.getProperty("department_ced"));
        createDepButton = cedDepartment.getCreateButton();
        editDepButton = cedDepartment.getEditButton();
        deleteDepButton = cedDepartment.getDeleteButton();
        createDepButton.setEnabled(false);
        editDepButton.setEnabled(false);
        deleteDepButton.setEnabled(false);

        //Set up SubDepartment combo box and edit buttons
        DefaultComboBoxModel<SubdepartmentModel> subdepModel = new DefaultComboBoxModel<>();
        subdepartmentBox = new JComboBox<>(subdepModel);
        subdepartmentBox.addItem(emptySubdepartmentModel);
        subdepartmentBox.setEditable(true);
        subdepartmentBox.setEnabled(false);
        subdepartmentBox.addActionListener(this);
        subdepartmentBox.setPreferredSize(comboBoxDim);
        subdepartmentBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String subdepName = subdepartmentBox.getEditor().getItem().toString();
                createSubdepButton.setEnabled(!subdepName.isEmpty());
            }
        });

        CrEdDelButtons cedSubdepartment = new CrEdDelButtons(Labels.getProperty("subdepartment_ced"));
        createSubdepButton = cedSubdepartment.getCreateButton();
        editSubdepButton = cedSubdepartment.getEditButton();
        deleteSubdepButton = cedSubdepartment.getDeleteButton();
        createSubdepButton.setEnabled(false);
        editSubdepButton.setEnabled(false);
        deleteSubdepButton.setEnabled(false);

        closeButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        createInstButton.addActionListener(e -> {
            if (newInstName != null) {
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
            if (!privateInstModel.equals(emptyInstituteModel) && cedInst.deleteEntry(parent, privateInstModel.getInstName()) && orgListener != null) {
                instituteBox.removeAllItems();
                instituteBox.addItem(emptyInstituteModel);
                orgListener.deleteInstEventOccurred(privateInstModel);
            }
            newInstName = null;
            privateInstModel = emptyInstituteModel;
        });

        createDepButton.addActionListener(e -> {
            if (!privateInstModel.equals(emptyInstituteModel) && newDepName != null) {
                DepartmentModel model = new DepartmentModel(newDepName,
                        privateInstModel.getModelId());
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
            if (!privateDepModel.equals(emptyDepartmentModel) && cedDepartment.deleteEntry(parent, privateDepModel.getDepName()) && orgListener != null) {
                departmentBox.removeAllItems();
                orgListener.deleteDepEventOccurred(privateDepModel);
            }
            newDepName = null;
            privateDepModel = emptyDepartmentModel;
        });

        createSubdepButton.addActionListener(e -> {
            if (!privateDepModel.equals(emptyDepartmentModel) && newSubdepName != null) {
                SubdepartmentModel model = new SubdepartmentModel(newSubdepName,
                        privateDepModel.getModelId());
                if (orgListener != null) {
                    subdepartmentBox.removeAllItems();
                    subdepartmentBox.addItem(emptySubdepartmentModel);
                    orgListener.createSubdepEventOccurred(model);
                }
            }
            newSubdepName = null;
            privateSubdepModel = emptySubdepartmentModel;
        });

        editSubdepButton.addActionListener(e -> {
            if (newSubdepName != null && !privateSubdepModel.equals(emptySubdepartmentModel)) {
                privateSubdepModel.setSubdepName(newSubdepName);
                if (orgListener != null) {
                    subdepartmentBox.removeAllItems();
                    subdepartmentBox.addItem(emptySubdepartmentModel);
                    orgListener.editSubdepEventOccurred(privateSubdepModel);
                }
            }
            newSubdepName = null;
            privateSubdepModel = emptySubdepartmentModel;
        });

        deleteSubdepButton.addActionListener(e -> {
            if (!privateSubdepModel.equals(emptySubdepartmentModel) && cedSubdepartment.deleteEntry(parent, privateSubdepModel.getSubdepName()) && orgListener != null) {
                subdepartmentBox.removeAllItems();
                subdepartmentBox.addItem(emptySubdepartmentModel);
                orgListener.deleteSubdepEventOccurred(privateSubdepModel);
            }
            newSubdepName = null;
            privateSubdepModel = emptySubdepartmentModel;
        });

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

    private void disableInstituteEdit() {
        editInstButton.setEnabled(false);
        deleteInstButton.setEnabled(false);
        createDepButton.setEnabled(false);
        editDepButton.setEnabled(false);
        deleteDepButton.setEnabled(false);
        createSubdepButton.setEnabled(false);
        editSubdepButton.setEnabled(false);
        deleteSubdepButton.setEnabled(false);
    }

    private void enableInstituteEdit() {
        editInstButton.setEnabled(true);
        deleteInstButton.setEnabled(true);
        createDepButton.setEnabled(false);
        editDepButton.setEnabled(false);
        deleteDepButton.setEnabled(false);
        createSubdepButton.setEnabled(false);
        editSubdepButton.setEnabled(false);
        deleteSubdepButton.setEnabled(false);
    }

    private void disableDepartmentEdit() {
        editInstButton.setEnabled(true);
        deleteInstButton.setEnabled(true);
        createDepButton.setEnabled(true);
        editDepButton.setEnabled(false);
        deleteDepButton.setEnabled(false);
        createSubdepButton.setEnabled(false);
        editSubdepButton.setEnabled(false);
        deleteSubdepButton.setEnabled(false);
    }

    private void enableDepartmentEdit() {
        editInstButton.setEnabled(true);
        deleteInstButton.setEnabled(true);
        createDepButton.setEnabled(true);
        editDepButton.setEnabled(true);
        deleteDepButton.setEnabled(true);
        createSubdepButton.setEnabled(false);
        editSubdepButton.setEnabled(false);
        deleteSubdepButton.setEnabled(false);
    }

    private void disableSubdepartmentEdit() {
        editInstButton.setEnabled(true);
        deleteInstButton.setEnabled(true);
        createDepButton.setEnabled(true);
        editDepButton.setEnabled(true);
        deleteDepButton.setEnabled(true);
        createSubdepButton.setEnabled(true);
        editSubdepButton.setEnabled(false);
        deleteSubdepButton.setEnabled(false);
    }

    private void enableSubdepartmentEdit() {
        editInstButton.setEnabled(true);
        deleteInstButton.setEnabled(true);
        createDepButton.setEnabled(true);
        editDepButton.setEnabled(true);
        deleteDepButton.setEnabled(true);
        createSubdepButton.setEnabled(true);
        editSubdepButton.setEnabled(true);
        deleteSubdepButton.setEnabled(true);
    }

    public void actionPerformed(ActionEvent ev) {
        JComboBox box = (JComboBox) ev.getSource();
        Object obj = box.getSelectedItem();

        if (box == instituteBox) {
            departmentBox.removeAllItems();
            if (obj instanceof InstituteModel) {
                if (!obj.equals(emptyInstituteModel)) {
                    enableInstituteEdit();
                    departmentBox.setEnabled(true);
                    subdepartmentBox.setEnabled(false);
                    privateInstModel = (InstituteModel) obj;
                    long instId = privateInstModel.getModelId();
                    if (orgListener != null) {
                        orgListener.instSelectionEventOccurred(instId);
                    }
                } else {
                    departmentBox.setEnabled(false);
                    subdepartmentBox.setEnabled(false);
                    disableInstituteEdit();
                }
            } else if (obj instanceof String) {
                String name = (String) obj;
                departmentBox.setEnabled(false);
                subdepartmentBox.setEnabled(false);
                if (!name.isEmpty()) {
                    newInstName = name;
                    if (!privateInstModel.equals(emptyInstituteModel)) {
                        enableInstituteEdit();
                    } else {
                        disableInstituteEdit();
                    }
                }
            }

        } else if (box == departmentBox) {
            if (obj == null) {
                departmentBox.addItem(emptyDepartmentModel);
            }
            subdepartmentBox.removeAllItems();
            subdepartmentBox.addItem(emptySubdepartmentModel);
            if (obj instanceof DepartmentModel) {
                if (!obj.equals(emptyDepartmentModel)) {
                    subdepartmentBox.setEnabled(true);
                    enableDepartmentEdit();
                    privateDepModel = (DepartmentModel) obj;
                    long depId = privateDepModel.getModelId();
                    if (orgListener != null) {
                        orgListener.depSelectionEventOccurred(depId);
                    }
                } else {
                    subdepartmentBox.setEnabled(false);
                    disableDepartmentEdit();
                }
            } else if (obj instanceof String) {
                String name = (String) obj;
                subdepartmentBox.setEnabled(false);
                if (!name.isEmpty()) {
                    newDepName = name;
                    if (!privateDepModel.equals(emptyDepartmentModel)) {
                        enableDepartmentEdit();
                    } else {
                        disableDepartmentEdit();
                    }
                }
            }
        } else if (box == subdepartmentBox) {
            if (obj instanceof SubdepartmentModel) {
                if (!obj.equals(emptySubdepartmentModel)) {
                    enableSubdepartmentEdit();
                    privateSubdepModel = (SubdepartmentModel) obj;
                } else {
                    disableSubdepartmentEdit();
                }
            } else if (obj instanceof String) {
                String name = (String) obj;
                if (!name.isEmpty()) {
                    newSubdepName = name;
                    if (!privateSubdepModel.equals(emptySubdepartmentModel)) {
                        enableSubdepartmentEdit();
                    } else {
                        disableSubdepartmentEdit();
                    }
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
}
