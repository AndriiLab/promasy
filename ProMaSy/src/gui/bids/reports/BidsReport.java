package gui.bids.reports;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by laban on 22.06.2016.
 */
public class BidsReport {

    public BidsReport(List<BidsReportModel> bidsList){
        JasperPrint jasperPrint;
        String reportPath = "/resources/Bids_Report.jasper";
        InputStream reportStream = BidsReport.class.getResourceAsStream(reportPath);
        try {
            //Run code below only if report (.jrxml) is not compiled to .jasper
//            JasperCompileManager.compileReportToFile("D:\\Dropbox\\Git\\ProMaSy\\src\\resources\\Bids_Report.jrxml");
            jasperPrint = JasperFillManager.fillReport(reportStream, new HashMap<String, Object>(), new JRBeanCollectionDataSource(bidsList));
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
}
