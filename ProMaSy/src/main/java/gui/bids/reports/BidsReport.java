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
import java.util.HashMap;
import java.util.List;

/**
 * Class compiles .jasper file and loads it into {@link JasperViewer}
 */
public class BidsReport {

    public BidsReport(List<BidsReportModel> bidsList, MainFrame parent) {
        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport("reports/Bids_Report.jasper", new HashMap<>(), new JRBeanCollectionDataSource(bidsList));
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
                compileReport();
                JOptionPane.showMessageDialog(parent, Labels.getProperty("printSetupComplete"), Labels.getProperty("printInfo"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                Logger.errorEvent(parent, Labels.getProperty("printError"), e);
                JOptionPane.showMessageDialog(parent, Labels.getProperty("printSetupError"), Labels.getProperty("printError"), JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private static void compileReport(){
        System.out.println("Compiling new report file");
        File jrxmlFile = new File("reports/Bids_Report.jrxml");
        try {
            JasperCompileManager.compileReportToFile(jrxmlFile.getAbsolutePath());
        } catch (JRException e) {
            Logger.warnEvent(e);
        }
    }
}
