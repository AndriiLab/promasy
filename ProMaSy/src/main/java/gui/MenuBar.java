package gui;

import gui.commons.Icons;
import gui.commons.Labels;
import model.enums.Role;

import javax.swing.*;

/**
 * Generates menu bar
 */
class MenuBar {

    private Role role;
    private MenuBarListener listener;

    MenuBar(Role role) {
        this.role = role;
    }

    JMenuBar getMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu(Labels.getProperty("file"));
        JMenuItem printItem = new JMenuItem(Labels.withThreeDots("print"));
        printItem.setIcon(Icons.PRINT);
        JMenuItem exportToTableItem = new JMenuItem(Labels.getProperty("exportToTableFile"));
        exportToTableItem.setIcon(Icons.EXCEL_FILE);
        JMenuItem exitItem = new JMenuItem(Labels.getProperty("exit"));
        fileMenu.add(printItem);
        fileMenu.add(exportToTableItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu(Labels.getProperty("edit"));
        JMenuItem editAmUnitsItem = new JMenuItem(Labels.withThreeDots("amUnitsDialogSuper"));
        editAmUnitsItem.setIcon(Icons.UNITS);
        JMenuItem editProdItem = new JMenuItem(Labels.withThreeDots("prodDialogSuper"));
        editProdItem.setIcon(Icons.PRODUCER);
        JMenuItem editSuplItem = new JMenuItem(Labels.withThreeDots("suplDialogSuper"));
        editSuplItem.setIcon(Icons.SUPPLIER);
        JMenuItem editCurrentUserItem = new JMenuItem(Labels.withThreeDots("editCurrentUser"));
        editCurrentUserItem.setIcon(Icons.USER);
        editMenu.add(editAmUnitsItem);
        editMenu.add(editProdItem);
        editMenu.add(editSuplItem);
        editMenu.addSeparator();
        editMenu.add(editCurrentUserItem);

        JMenu helpMenu = new JMenu(Labels.getProperty("help"));
        JMenuItem infoItem = new JMenuItem(Labels.getProperty("aboutSoftware"));
        infoItem.setIcon(Icons.ABOUT);
        helpMenu.add(infoItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        // advanced menu for non users
        if (role.equals(Role.ADMIN)) {
            JMenuItem editOrgItem = new JMenuItem(Labels.withThreeDots("editOrganizationsDepartments"));
            editOrgItem.setIcon(Icons.ORGANIZATION);

            editMenu.add(editOrgItem);

            JMenu settingsMenu = new JMenu(Labels.getProperty("settings"));
            JMenuItem conSettItem = new JMenuItem(Labels.withThreeDots("connectionWithDBSettings"));
            conSettItem.setIcon(Icons.CONNECTION_SETTINGS);
            settingsMenu.add(conSettItem);

            JMenuItem editEmpItem = new JMenuItem(Labels.withThreeDots("editEmployees"));
            editEmpItem.setIcon(Icons.USERS);
            editMenu.add(editEmpItem);
            editEmpItem.addActionListener(e -> listener.showEmpEditDialog());

            JMenuItem setCurrentVersionAsMinimum = new JMenuItem(Labels.getProperty("setMinimumVersion"));
            settingsMenu.addSeparator();
            settingsMenu.add(setCurrentVersionAsMinimum);
            setCurrentVersionAsMinimum.addActionListener(e -> listener.setCurrentVersionAsMinimum());

            JMenuItem changeRegistrationsNumber = new JMenuItem(Labels.withThreeDots("changeRegistrationsNumber"));
            settingsMenu.add(changeRegistrationsNumber);
            changeRegistrationsNumber.addActionListener(e -> listener.changeRegistrationsNumber());

            menuBar.add(settingsMenu);

            editOrgItem.addActionListener(e -> listener.showEditOrgDialog());

            conSettItem.addActionListener(e -> listener.showConSetDialog());
        }
        printItem.addActionListener(e -> listener.printAction());

        exportToTableItem.addActionListener(e -> listener.exportToTableAction());

        exitItem.addActionListener(e -> listener.exitAction());

        if (!role.equals(Role.USER) && !role.equals(Role.PERSONALLY_LIABLE_EMPLOYEE) && !role.equals(Role.HEAD_OF_DEPARTMENT)) {
            JMenu reports = new JMenu(Labels.getProperty("reports"));

            JMenuItem procByCpv = new JMenuItem(Labels.withThreeDots("cpvAmounts"));
            reports.add(procByCpv);

            menuBar.add(reports);

            procByCpv.addActionListener(e -> listener.showCpvAmountDialog());

        }

        editAmUnitsItem.addActionListener(e -> listener.showAmUnitsDialog());
        editProdItem.addActionListener(e -> listener.showProducerDialog());
        editSuplItem.addActionListener(e -> listener.showSupplierDialog());
        editCurrentUserItem.addActionListener(e -> listener.editCurrentUserAction());
        infoItem.addActionListener(e -> listener.showInfoDialog());


        menuBar.add(helpMenu);

        return menuBar;
    }

    void setMenuBarListener(MenuBarListener listener) {
        this.listener = listener;
    }
}
