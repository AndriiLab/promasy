package com.github.andriilab.promasy.app.components;

import com.github.andriilab.promasy.domain.organization.enums.Role;
import com.github.andriilab.promasy.app.commons.Icons;
import com.github.andriilab.promasy.app.commons.Labels;

import javax.swing.*;

/**
 * Generates menu bar
 */
public class MainFrameMenuBar extends JMenuBar {

    private MenuBarListener listener;

    public MainFrameMenuBar(Role role) {

        JMenu fileMenu = new JMenu(Labels.getProperty("file"));
        JMenuItem printItem = new JMenuItem(Labels.withThreeDots("print"), Icons.PRINT);
        JMenuItem exportToTableItem = new JMenuItem(Labels.getProperty("exportToTableFile"), Icons.EXCEL_FILE);
        JMenuItem exitItem = new JMenuItem(Labels.getProperty("exit"));
        fileMenu.add(printItem);
        fileMenu.add(exportToTableItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu(Labels.getProperty("edit"));
        JMenuItem editAmUnitsItem = new JMenuItem(Labels.withThreeDots("amUnitsDialogSuper"), Icons.UNITS);
        JMenuItem editProdItem = new JMenuItem(Labels.withThreeDots("prodDialogSuper"), Icons.PRODUCER);
        JMenuItem editSuplItem = new JMenuItem(Labels.withThreeDots("suplDialogSuper"), Icons.SUPPLIER);
        JMenuItem editCurrentUserItem = new JMenuItem(Labels.withThreeDots("editCurrentUser"), Icons.USER);
        editMenu.add(editAmUnitsItem);
        editMenu.add(editProdItem);
        editMenu.add(editSuplItem);
        editMenu.addSeparator();
        editMenu.add(editCurrentUserItem);

        JMenu toolsMenu = new JMenu(Labels.getProperty("tools"));
        JMenuItem calcularorItem = new JMenuItem(Labels.withThreeDots("calculator"), Icons.CALCULATOR);
        JMenuItem cpvSelectorItem = new JMenuItem(Labels.withThreeDots("cpvPanelTab"), Icons.BOOK);
        toolsMenu.add(calcularorItem);
        toolsMenu.add(cpvSelectorItem);

        JMenu helpMenu = new JMenu(Labels.getProperty("help"));
        JMenuItem manualItem = new JMenuItem(Labels.getProperty("manual"), Icons.MANUAL);
        JMenuItem siteWithUpdatesItem = new JMenuItem(Labels.getProperty("updatesPage"), Icons.UPDATE);
        JMenuItem infoItem = new JMenuItem(Labels.getProperty("aboutSoftware"), Icons.ABOUT);
        helpMenu.add(manualItem);
        helpMenu.addSeparator();
        helpMenu.add(siteWithUpdatesItem);
        helpMenu.add(infoItem);

        add(fileMenu);
        add(editMenu);
        add(toolsMenu);

        // advanced menu for non users
        if (role.equals(Role.ADMIN)) {
            JMenuItem editOrgItem = new JMenuItem(Labels.withThreeDots("editOrganizationsDepartments"), Icons.ORGANIZATION);

            editMenu.add(editOrgItem);

            JMenu settingsMenu = new JMenu(Labels.getProperty("settings"));
            JMenuItem conSettItem = new JMenuItem(Labels.withThreeDots("connectionWithDBSettings"), Icons.CONNECTION_SETTINGS);
            settingsMenu.add(conSettItem);

            JMenuItem editEmpItem = new JMenuItem(Labels.withThreeDots("editEmployees"), Icons.USERS);
            editMenu.add(editEmpItem);
            editEmpItem.addActionListener(e -> listener.showEmpEditDialog());

            JMenuItem setCurrentVersionAsMinimum = new JMenuItem(Labels.getProperty("setMinimumVersion"));
            settingsMenu.addSeparator();
            settingsMenu.add(setCurrentVersionAsMinimum);
            setCurrentVersionAsMinimum.addActionListener(e -> listener.setCurrentVersionAsMinimum());

            JMenuItem changeRegistrationsNumber = new JMenuItem(Labels.withThreeDots("changeRegistrationsNumber"));
            settingsMenu.add(changeRegistrationsNumber);
            changeRegistrationsNumber.addActionListener(e -> listener.changeRegistrationsNumber());

            add(settingsMenu);

            editOrgItem.addActionListener(e -> listener.showEditOrgDialog());

            conSettItem.addActionListener(e -> listener.showConSetDialog());
        }
        printItem.addActionListener(e -> listener.printAction());

        exportToTableItem.addActionListener(e -> listener.exportToTableAction());

        exitItem.addActionListener(e -> listener.exitAction());

        if (!role.equals(Role.USER) && !role.equals(Role.PERSONALLY_LIABLE_EMPLOYEE) && !role.equals(Role.HEAD_OF_DEPARTMENT)) {
            JMenuItem procByCpv = new JMenuItem(Labels.withThreeDots("cpvAmounts"));
            toolsMenu.add(procByCpv);
            procByCpv.addActionListener(e -> listener.showCpvAmountDialog());
        }

        editAmUnitsItem.addActionListener(e -> listener.showAmUnitsDialog());
        editProdItem.addActionListener(e -> listener.showProducerDialog());
        editSuplItem.addActionListener(e -> listener.showSupplierDialog());
        editCurrentUserItem.addActionListener(e -> listener.editCurrentUserAction());
        calcularorItem.addActionListener(e -> listener.showCalculator());
        cpvSelectorItem.addActionListener(e -> listener.showCpvSelectorDialog());
        manualItem.addActionListener(e -> listener.showManual());
        siteWithUpdatesItem.addActionListener(e -> listener.visitUpdatesSite());
        infoItem.addActionListener(e -> listener.showInfoDialog());

        add(helpMenu);
    }

    public void setMenuBarListener(MenuBarListener listener) {
        this.listener = listener;
    }
}
