package com.github.andriilab.promasy.controller;

import com.github.andriilab.promasy.gui.MainFrame;
import com.github.andriilab.promasy.gui.commons.Labels;
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
                Logger.warnEvent(e);
                //Compile report (.jrxml) to .jasper if it is not compiled
                compileReport(reportPath, parent);
                JOptionPane.showMessageDialog(parent, Labels.getProperty("printSetupComplete"), Labels.getProperty("printInfo"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                Logger.errorEvent(parent, Labels.getProperty("printError"), e);
                JOptionPane.showMessageDialog(parent, Labels.getProperty("printSetupError"), Labels.getProperty("printError"), JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private static void compileReport(String reportPath, MainFrame parent) {
        Logger.infoEvent(parent, "Compiling new report file for " + reportPath);
        File jrxmlFile = new File(reportPath + ".jrxml");
        try {
            JasperCompileManager.compileReportToFile(jrxmlFile.getAbsolutePath());
        } catch (JRException e) {
            Logger.warnEvent(e);
        }
    }

    public static void compileReportFileIfNew(String reportPath, MainFrame parent) throws IOException {
        Path jasperFile = FileSystems.getDefault().getPath(reportPath + ".jasper");
        try {
            BasicFileAttributes jasperAttr = Files.readAttributes(jasperFile, BasicFileAttributes.class);
            Path jrxmlFile = FileSystems.getDefault().getPath(reportPath + ".jrxml");
            BasicFileAttributes jrxmlAttr = Files.readAttributes(jrxmlFile, BasicFileAttributes.class);
            if (jasperAttr.lastModifiedTime().compareTo(jrxmlAttr.lastModifiedTime()) < 0) {
                Logger.infoEvent(parent, "Outdated report file: " + reportPath + ".jrxml");
                compileReport(reportPath, parent);
            } else {
                Logger.infoEvent(parent, "Report " + reportPath + ".jrxml does not need to be updated");
            }
        } catch (NoSuchFileException ex) {
            Logger.infoEvent(parent, "Report file doesn't exist " + reportPath);
            compileReport(reportPath, parent);
        }
    }
}
