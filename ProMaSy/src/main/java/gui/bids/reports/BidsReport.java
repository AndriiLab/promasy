package main.java.gui.bids.reports;

import main.java.model.BidsReportModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by laban on 22.06.2016.
 */
public class BidsReport {

    public BidsReport (List<BidsReportModel> bidsList){
        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport("resources\\Bids_Report.jasper", new HashMap<>(), new JRBeanCollectionDataSource(bidsList));
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setAlwaysOnTop(true);
            jasperViewer.setVisible(true);
        } catch (JRException e) {
            if (e.getMessage().startsWith("java.io.FileNotFoundException")){
                //Compile report (.jrxml) to .jasper if it is not compiled
                compileReport();
                System.out.println("Compiling report file");
            }
            e.printStackTrace();
        }
    }

    private static void compileReport(){
        // Bad file path. Possible doesn't work in jar
        File jrxmlFile = new File("resources/Bids_Report.jrxml");
        try {
            JasperCompileManager.compileReportToFile(jrxmlFile.getAbsolutePath());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
