package gui.bids.reports;

import controller.Logger;
import gui.MainFrame;
import gui.commons.Labels;
import model.models.BidsReportModel;
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
 * Class compiles .jasper file and loads it into {@link JasperViewer}
 */
public class BidsReport {

    public BidsReport(Map<String, Object> parameters, List<BidsReportModel> bidsList, MainFrame parent) {
        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport("reports/Bids_Report.jasper", parameters, new JRBeanCollectionDataSource(bidsList));
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            EventQueue.invokeLater(() -> {
                jasperViewer.setVisible(true);
                jasperViewer.setFitPageZoomRatio();
                jasperViewer.toFront();
                jasperViewer.repaint();
            });
        } catch (JRException e) {
            if (e.getMessage().startsWith("java.io.FileNotFoundException")){
                Logger.warnEvent(e);
                //Compile report (.jrxml) to .jasper if it is not compiled
                compileReport(parent);
                JOptionPane.showMessageDialog(parent, Labels.getProperty("printSetupComplete"), Labels.getProperty("printInfo"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                Logger.errorEvent(parent, Labels.getProperty("printError"), e);
                JOptionPane.showMessageDialog(parent, Labels.getProperty("printSetupError"), Labels.getProperty("printError"), JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private static void compileReport(MainFrame parent) {
        Logger.infoEvent(parent, "Compiling new report file");
        File jrxmlFile = new File("reports/Bids_Report.jrxml");
        try {
            JasperCompileManager.compileReportToFile(jrxmlFile.getAbsolutePath());
        } catch (JRException e) {
            Logger.warnEvent(e);
        }
    }

    public static void compileReportFileIfNew(MainFrame parent) throws IOException {
        Path jasperFile = FileSystems.getDefault().getPath("reports", "Bids_Report.jasper");
        try {
            BasicFileAttributes jasperAttr = Files.readAttributes(jasperFile, BasicFileAttributes.class);
            Path jrxmlFile = FileSystems.getDefault().getPath("reports", "Bids_Report.jrxml");
            BasicFileAttributes jrxmlAttr = Files.readAttributes(jrxmlFile, BasicFileAttributes.class);
            if (jasperAttr.lastModifiedTime().compareTo(jrxmlAttr.lastModifiedTime()) == -1) {
                Logger.infoEvent(parent, "Report files are out-of-date");
                compileReport(parent);
            } else {
                Logger.infoEvent(parent, "Report files are up–to–date");
            }
        } catch (NoSuchFileException ex) {
            Logger.infoEvent(parent, "Report file doesn't exist");
            compileReport(parent);
        }

    }
}
