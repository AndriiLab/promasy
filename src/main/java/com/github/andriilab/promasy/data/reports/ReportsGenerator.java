package com.github.andriilab.promasy.data.reports;

import com.github.andriilab.promasy.app.controller.Logger;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Labels;
import com.github.andriilab.promasy.data.reports.models.BidsReportRequest;
import com.github.andriilab.promasy.data.reports.models.CpvAmountsReportRequest;
import com.github.andriilab.promasy.data.reports.models.ReportRequest;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Loader for Jasper Reports
 */
public class ReportsGenerator {
    private static final String JASPER_EXTENSION = ".jasper";
    private static final String JRXML_EXTENSION = ".jrxml";
    private static final String BIDS_REPORT = "reports/Bids_Report";
    private static final String CPV_AMOUNT_REPORT = "reports/CPV_Amount_Report";
    private final MainFrame parent;

    public ReportsGenerator(MainFrame parent){

        this.parent = parent;
    }
    public void showPrintDialog(ReportRequest<?> request) {
        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport(getReportPath(request) + JASPER_EXTENSION, request.getParameters(), new JRBeanCollectionDataSource(request.getModelsList()));
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            EventQueue.invokeLater(() -> {
                jasperViewer.setVisible(true);
                jasperViewer.setFitPageZoomRatio();
                jasperViewer.toFront();
                jasperViewer.repaint();
            });
        } catch (NoSuchFileException e) {
            Logger.errorEvent(request.getClass(), parent, Labels.getProperty("printError"), e);
            JOptionPane.showMessageDialog(parent, e.getMessage(), Labels.getProperty("printError"), JOptionPane.ERROR_MESSAGE);
        } catch (JRException e) {
            if (e.getMessage().startsWith("java.io.FileNotFoundException")) {
                Logger.warnEvent(request.getClass(), e);
                try {
                    //Compile report (.jrxml) to .jasper if it is not compiled
                    compileReport(getReportPath(request));
                } catch (NoSuchFileException ex) {
                    Logger.errorEvent(request.getClass(), parent, Labels.getProperty("printError"), e);
                    JOptionPane.showMessageDialog(parent, e.getMessage(), Labels.getProperty("printError"), JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(parent, Labels.getProperty("printSetupComplete"), Labels.getProperty("printInfo"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                Logger.errorEvent(request.getClass(), parent, Labels.getProperty("printError"), e);
                JOptionPane.showMessageDialog(parent, Labels.getProperty("printSetupError"), Labels.getProperty("printError"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void precompileReports() {
        try {
            compileReportFileIfNew(ReportsGenerator.BIDS_REPORT);
            compileReportFileIfNew(ReportsGenerator.CPV_AMOUNT_REPORT);
        } catch (IOException e) {
            Logger.errorEvent(ReportsGenerator.class, parent, e);
        }
    }

    private void compileReport(String reportPath) {
        Logger.infoEvent(ReportsGenerator.class, parent, "Compiling new report file for " + reportPath);
        File jrxmlFile = new File(reportPath + JRXML_EXTENSION);
        try {
            JasperCompileManager.compileReportToFile(jrxmlFile.getAbsolutePath());
        } catch (JRException e) {
            Logger.warnEvent(ReportsGenerator.class, e);
        }
    }

    private void compileReportFileIfNew(String reportPath) throws IOException {
        Path jasperFile = FileSystems.getDefault().getPath(reportPath + JASPER_EXTENSION);
        try {
            BasicFileAttributes jasperAttr = Files.readAttributes(jasperFile, BasicFileAttributes.class);
            Path jrxmlFile = FileSystems.getDefault().getPath(reportPath + JRXML_EXTENSION);
            BasicFileAttributes jrxmlAttr = Files.readAttributes(jrxmlFile, BasicFileAttributes.class);
            if (jasperAttr.lastModifiedTime().compareTo(jrxmlAttr.lastModifiedTime()) < 0) {
                Logger.infoEvent(ReportsGenerator.class, parent, "Outdated report file: " + reportPath + JRXML_EXTENSION);
                compileReport(reportPath);
            } else {
                Logger.infoEvent(ReportsGenerator.class, parent, "Report " + reportPath + ".jrxml does not need to be updated");
            }
        } catch (NoSuchFileException ex) {
            Logger.infoEvent(ReportsGenerator.class, parent, "Report file doesn't exist " + reportPath);
            compileReport(reportPath);
        }
    }

    private static String getReportPath(ReportRequest<?> request) throws NoSuchFileException {
        if (request instanceof BidsReportRequest) {
            return BIDS_REPORT;
        }

        if (request instanceof CpvAmountsReportRequest) {
            return CPV_AMOUNT_REPORT;
        }

        throw new NoSuchFileException("Report template not created");
    }
}
