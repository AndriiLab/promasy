package com.github.andriilab.promasy.gui;

/**
 * Listener for {@link MenuBar}
 */
interface MenuBarListener {

    void setCurrentVersionAsMinimum();

    void showEmpEditDialog();

    void showEditOrgDialog();

    void showConSetDialog();

    void printAction();

    void exportToTableAction();

    void exitAction();

    void showCpvAmountDialog();

    void showAmUnitsDialog();

    void showProducerDialog();

    void showSupplierDialog();

    void editCurrentUserAction();

    void showInfoDialog();

    void changeRegistrationsNumber();

    void showManual();
}