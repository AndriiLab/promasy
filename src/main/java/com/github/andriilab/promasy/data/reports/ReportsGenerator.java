package com.github.andriilab.promasy.data.reports;

import com.github.andriilab.promasy.app.controller.Logger;
import com.github.andriilab.promasy.app.view.MainFrame;
import com.github.andriilab.promasy.app.commons.Labels;
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
import java.util.List;
import java.util.Map;

/**
 * Loader for Jasper Reports
 */
public class ReportsGenerator {
    public static final String BIDS_REPORT = "reports/Bids_Report";
    public static final String CPV_AMOUNT_REPORT = "reports/CPV_Amount_Report";

    public ReportsGenerator(String reportPath, Map<String, Object> parameters, List modelsList, MainFrame parent) {
        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport(reportPath + ".jasper", parameters, new JRBeanCollectionDataSource(modelsList));
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            EventQueue.invokeLater(() -> {
                jasperViewer.setVisible(true);
                jasperViewer.setFitPageZoomRatio();
                jasperViewer.toFront();
                jasperViewer.repaint();
            });
        } catch (JRException e) {
            if (e.getMessage().startsWith("java.io.FileNotFoundException")) {
                Logger.warnEvent(this.getClass(), e);
                //Compile report (.jrxml) to .jasper if it is not compiled
                compileReport(reportPath, parent);
                JOptionPane.showMessageDialog(parent, Labels.getProperty("printSetupComplete"), Labels.getProperty("printInfo"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                Logger.errorEvent(this.getClass(), parent, Labels.getProperty("printError"), e);
                JOptionPane.showMessageDialog(parent, Labels.getProperty("printSetupError"), Labels.getProperty("printError"), JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public static void precompileReports(MainFrame mainFrame) {
        try {
            ReportsGenerator.compileReportFileIfNew(ReportsGenerator.BIDS_REPORT, mainFrame);
            ReportsGenerator.compileReportFileIfNew(ReportsGenerator.CPV_AMOUNT_REPORT, mainFrame);
        } catch (IOException e) {
            Logger.errorEvent(ReportsGenerator.class, mainFrame, e);
        }
    }

    private static void compileReport(String reportPath, MainFrame parent) {
        Logger.infoEvent(ReportsGenerator.class, parent, "Compiling new report file for " + reportPath);
        File jrxmlFile = new File(reportPath + ".jrxml");
        try {
            JasperCompileManager.compileReportToFile(jrxmlFile.getAbsolutePath());
        } catch (JRException e) {
            Logger.warnEvent(ReportsGenerator.class, e);
        }
    }

    private static void compileReportFileIfNew(String reportPath, MainFrame parent) throws IOException {
        Path jasperFile = FileSystems.getDefault().getPath(reportPath + ".jasper");
        try {
            BasicFileAttributes jasperAttr = Files.readAttributes(jasperFile, BasicFileAttributes.class);
            Path jrxmlFile = FileSystems.getDefault().getPath(reportPath + ".jrxml");
            BasicFileAttributes jrxmlAttr = Files.readAttributes(jrxmlFile, BasicFileAttributes.class);
            if (jasperAttr.lastModifiedTime().compareTo(jrxmlAttr.lastModifiedTime()) < 0) {
                Logger.infoEvent(ReportsGenerator.class, parent, "Outdated report file: " + reportPath + ".jrxml");
                compileReport(reportPath, parent);
            } else {
                Logger.infoEvent(ReportsGenerator.class, parent, "Report " + reportPath + ".jrxml does not need to be updated");
            }
        } catch (NoSuchFileException ex) {
            Logger.infoEvent(ReportsGenerator.class, parent, "Report file doesn't exist " + reportPath);
            compileReport(reportPath, parent);
        }
    }
}
