package gui.organization;

import gui.MainFrame;
import gui.commons.Labels;
import gui.components.CEDButtons;
import gui.components.PJComboBox;
import model.models.DepartmentModel;
import model.models.EmptyModel;
import model.models.InstituteModel;
import model.models.SubdepartmentModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class OrganizationDialog extends JDialog implements ActionListener {

    private final JButton closeButton;
    private final JButton createInstButton;
    private final JButton editInstButton;
    private final JButton deleteInstButton;
    private JButton createDepButton;
    private final JButton editDepButton;
    private final JButton deleteDepButton;
    private JButton createSubdepButton;
    private final JButton editSubdepButton;
    private final JButton deleteSubdepButton;
    private final PJComboBox<InstituteModel> instituteBox;
    private final PJComboBox<DepartmentModel> departmentBox;
    private final PJComboBox<SubdepartmentModel> subdepartmentBox;
    private OrganizationDialogListener orgListener;
    private String newDepName;
    private String newSubdepName;
    private InstituteModel privateInstModel;
    private DepartmentModel privateDepModel;
    private SubdepartmentModel privateSubdepModel;
    private final CreateOrganizationDialog createOrganizationDialog;

    public OrganizationDialog(MainFrame parent) {
        super(parent, Labels.getProperty("addEditOrganizationAndDepartments"), true);
        setSize(610, 200);
        setResizable(false);
        setLocationRelativeTo(parent);

        Dimension comboBoxDim = new Dimension(400, 25);

        createOrganizationDialog = new CreateOrganizationDialog(parent);

        privateInstModel = EmptyModel.INSTITUTE;
        privateDepModel = EmptyModel.DEPARTMENT;
        privateSubdepModel = EmptyModel.SUBDEPARTMENT;

        //Set up institute combo box and edit buttons
        DefaultComboBoxModel<InstituteModel> instModel = new DefaultComboBoxModel<>();
        instituteBox = new PJComboBox<>(instModel);
        instituteBox.addItem(EmptyModel.INSTITUTE);
        instituteBox.setEditable(true);
        instituteBox.addActionListener(this);
        instituteBox.setPreferredSize(comboBoxDim);

        CEDButtons cedInst = new CEDButtons(Labels.getProperty("institute_ced"));
        createInstButton = cedInst.getCreateButton();
        editInstButton = cedInst.getEditButton();
        deleteInstButton = cedInst.getDeleteButton();

        //Set up department combo box and edit buttons
        DefaultComboBoxModel<DepartmentModel> depModel = new DefaultComboBoxModel<>();
        departmentBox = new PJComboBox<>(depModel);
        departmentBox.addItem(EmptyModel.DEPARTMENT);
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

        CEDButtons cedDepartment = new CEDButtons(Labels.getProperty("department_ced"));
        createDepButton = cedDepartment.getCreateButton();
        editDepButton = cedDepartment.getEditButton();
        deleteDepButton = cedDepartment.getDeleteButton();

        //Set up SubDepartment combo box and edit buttons
        DefaultComboBoxModel<SubdepartmentModel> subdepModel = new DefaultComboBoxModel<>();
        subdepartmentBox = new PJComboBox<>(subdepModel);
        subdepartmentBox.addItem(EmptyModel.SUBDEPARTMENT);
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

        CEDButtons cedSubdepartment = new CEDButtons(Labels.getProperty("subdepartment_ced"));
        createSubdepButton = cedSubdepartment.getCreateButton();
        editSubdepButton = cedSubdepartment.getEditButton();
        deleteSubdepButton = cedSubdepartment.getDeleteButton();

        //disabling all CED buttons except add new organization
        disableInstituteEdit();

        closeButton = new JButton(Labels.getProperty("closeBtn"));

        layoutControls();

        createInstButton.addActionListener(e -> {
            createOrganizationDialog.setVisible(true);
            privateInstModel = EmptyModel.INSTITUTE;
        });

        editInstButton.addActionListener(e -> {
            if (!privateInstModel.equals(EmptyModel.INSTITUTE)) {
                createOrganizationDialog.setModel(privateInstModel);
            }
            privateInstModel = EmptyModel.INSTITUTE;
        });

        deleteInstButton.addActionListener(e -> {
            if (!privateInstModel.equals(EmptyModel.INSTITUTE) && cedInst.deleteEntry(parent, privateInstModel.getInstName()) && orgListener != null) {
                privateInstModel.setDeleted();
                orgListener.persistModelEventOccurred(privateInstModel);
            }
            privateInstModel = EmptyModel.INSTITUTE;
        });

        createDepButton.addActionListener(e -> {
            if (!privateInstModel.equals(EmptyModel.INSTITUTE) && newDepName != null) {
                DepartmentModel departmentModel = new DepartmentModel(newDepName);
                //if creating new department always adding new empty subdepartment to model
                SubdepartmentModel subdepartmentModel = new SubdepartmentModel("<" + Labels.getProperty("null") + ">");
                subdepartmentModel.setCreated();
                departmentModel.setCreated();
                departmentModel.addSubdepartment(subdepartmentModel);
                privateInstModel.addDepartment(departmentModel);
                InstituteModel instituteModel = privateInstModel;
                if (orgListener != null) {
                    orgListener.persistModelEventOccurred(departmentModel);
                    instituteBox.setSelectedModel(instituteModel);
                }
            }
            newDepName = null;
            privateDepModel = EmptyModel.DEPARTMENT;
        });

        editDepButton.addActionListener(e -> {
            if (newDepName != null && !privateDepModel.equals(EmptyModel.DEPARTMENT)) {
                privateDepModel.setDepName(newDepName);
                privateDepModel.setUpdated();
                privateInstModel.addDepartment(privateDepModel);
                InstituteModel instituteModel = privateInstModel;
                if (orgListener != null) {
                    orgListener.persistModelEventOccurred(privateDepModel);
                    instituteBox.setSelectedModel(instituteModel);
                }
            }
            newDepName = null;
            privateDepModel = EmptyModel.DEPARTMENT;
        });

        deleteDepButton.addActionListener(e -> {
            if (!privateDepModel.equals(EmptyModel.DEPARTMENT) && cedDepartment.deleteEntry(parent, privateDepModel.getDepName()) && orgListener != null) {
                privateDepModel.setDeleted();
                privateInstModel.addDepartment(privateDepModel);
                InstituteModel instituteModel = privateInstModel;
                orgListener.persistModelEventOccurred(privateDepModel);
                instituteBox.setSelectedModel(instituteModel);
            }
            newDepName = null;
            privateDepModel = EmptyModel.DEPARTMENT;
        });

        createSubdepButton.addActionListener(e -> {
            if (!privateDepModel.equals(EmptyModel.DEPARTMENT) && newSubdepName != null) {
                SubdepartmentModel model = new SubdepartmentModel(newSubdepName);
                model.setCreated();
                privateDepModel.addSubdepartment(model);
                DepartmentModel departmentModel = privateDepModel;
                if (orgListener != null) {
                    orgListener.persistModelEventOccurred(model);
                    departmentBox.setSelectedModel(departmentModel);
                }
            }
            newSubdepName = null;
            privateSubdepModel = EmptyModel.SUBDEPARTMENT;
        });

        editSubdepButton.addActionListener(e -> {
            if (newSubdepName != null && !privateSubdepModel.equals(EmptyModel.SUBDEPARTMENT)) {
                privateSubdepModel.setSubdepName(newSubdepName);
                privateSubdepModel.setUpdated();
                privateDepModel.addSubdepartment(privateSubdepModel);
                DepartmentModel departmentModel = privateDepModel;
                if (orgListener != null) {
                    orgListener.persistModelEventOccurred(privateSubdepModel);
                    departmentBox.setSelectedModel(departmentModel);
                }
            }
            newSubdepName = null;
            privateSubdepModel = EmptyModel.SUBDEPARTMENT;
        });

        deleteSubdepButton.addActionListener(e -> {
            if (!privateSubdepModel.equals(EmptyModel.SUBDEPARTMENT) && cedSubdepartment.deleteEntry(parent, privateSubdepModel.getSubdepName()) && orgListener != null) {
                privateSubdepModel.setDeleted();
                privateDepModel.addSubdepartment(privateSubdepModel);
                DepartmentModel departmentModel = privateDepModel;
                orgListener.persistModelEventOccurred(privateSubdepModel);
                departmentBox.setSelectedModel(departmentModel);
            }
            newSubdepName = null;
            privateSubdepModel = EmptyModel.SUBDEPARTMENT;
        });

        createOrganizationDialog.setListener(model -> {
            if (orgListener != null) {
                orgListener.persistModelEventOccurred(model);
            }
        });

        closeButton.addActionListener(e -> {
            instituteBox.setSelectedIndex(0);
            setVisible(false);
        });
    }

    public void setInstData(List<InstituteModel> instDb) {
        instituteBox.setBoxData(instDb, EmptyModel.INSTITUTE, true);
    }

    public void setDepData(List<DepartmentModel> depDb) {
        departmentBox.setBoxData(depDb, EmptyModel.DEPARTMENT, true);
    }

    public void setSubdepData(List<SubdepartmentModel> subdepDb) {
        subdepartmentBox.setBoxData(subdepDb, EmptyModel.SUBDEPARTMENT, true);
    }

    public void setListener(OrganizationDialogListener organizationDialogListener) {
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
                privateInstModel = (InstituteModel) obj;
                setDepData(privateInstModel.getDepartments());
                if (!obj.equals(EmptyModel.INSTITUTE)) {
                    enableInstituteEdit();
                    departmentBox.setEnabled(true);
                    subdepartmentBox.setEnabled(false);
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
                    if (!privateInstModel.equals(EmptyModel.INSTITUTE)) {
                        enableInstituteEdit();
                    } else {
                        disableInstituteEdit();
                    }
                }
            }

        } else if (box == departmentBox) {
            subdepartmentBox.removeAllItems();
            subdepartmentBox.addItem(EmptyModel.SUBDEPARTMENT);
            if (obj instanceof DepartmentModel) {
                privateDepModel = (DepartmentModel) obj;
                setSubdepData(privateDepModel.getSubdepartments());
                if (!obj.equals(EmptyModel.DEPARTMENT)) {
                    subdepartmentBox.setEnabled(true);
                    enableDepartmentEdit();
                } else {
                    subdepartmentBox.setEnabled(false);
                    disableDepartmentEdit();
                    if (privateInstModel.equals(EmptyModel.INSTITUTE)) {
                        createDepButton.setEnabled(false);
                    }
                }
            } else if (obj instanceof String) {
                String name = (String) obj;
                subdepartmentBox.setEnabled(false);
                if (!name.isEmpty()) {
                    newDepName = name;
                    if (!privateDepModel.equals(EmptyModel.DEPARTMENT)) {
                        enableDepartmentEdit();
                    } else {
                        disableDepartmentEdit();
                    }
                }
            }
        } else if (box == subdepartmentBox) {
            if (obj instanceof SubdepartmentModel) {
                if (!obj.equals(EmptyModel.SUBDEPARTMENT)) {
                    enableSubdepartmentEdit();
                    privateSubdepModel = (SubdepartmentModel) obj;
                } else {
                    disableSubdepartmentEdit();
                }
            } else if (obj instanceof String) {
                String name = (String) obj;
                if (!name.isEmpty()) {
                    newSubdepName = name;
                    if (!privateSubdepModel.equals(EmptyModel.SUBDEPARTMENT)) {
                        enableSubdepartmentEdit();
                    } else {
                        disableSubdepartmentEdit();
                    }
                }
            }
        }
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible && orgListener != null) {
            orgListener.getAllInstitutes();
        }
        super.setVisible(visible);
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
        institutePanel.add(new JLabel(Labels.withColon("institute")), gc);

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
        institutePanel.add(new JLabel(Labels.withColon("department")), gc);

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
        institutePanel.add(new JLabel(Labels.withColon("subdepartment")), gc);

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
