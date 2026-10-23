package com.github.andriilab.promasy.data.reports;

import com.github.andriilab.promasy.data.queries.bids.GetBidsQuery;
import com.github.andriilab.promasy.data.queries.cpv.CpvRequestQuery;
import com.github.andriilab.promasy.data.reports.models.BidReportModel;
import com.github.andriilab.promasy.data.reports.models.BidsReportRequest;
import com.github.andriilab.promasy.data.reports.models.CpvAmountReportModel;
import com.github.andriilab.promasy.data.reports.models.CpvAmountsReportRequest;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.domain.bid.entities.CpvAmount;
import com.github.andriilab.promasy.domain.bid.enums.BidType;
import com.github.andriilab.promasy.domain.finance.entities.Finance;
import com.github.andriilab.promasy.domain.finance.entities.FinanceDepartment;
import com.github.andriilab.promasy.domain.organization.entities.Department;
import com.github.andriilab.promasy.domain.organization.entities.Institute;
import com.github.andriilab.promasy.domain.organization.entities.Subdepartment;
import com.github.andriilab.promasy.it.AbstractIntegrationTest;
import com.github.andriilab.promasy.it.ItFixtures;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRPrintPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The two Jasper templates with data loaded from the database: they compile, the Cyrillic font extension resolves,
 * and the filled report exports to PDF. Nothing is shown on screen. The templates are read from {@code reports/}
 * in the working directory, which Gradle sets to the project directory.
 */
class ReportsGeneratorIT extends AbstractIntegrationTest {

    private static final String DESCRIPTION = "Реактив для аналізу";
    private static final String DEPARTMENT = "Відділ біохімії";

    private static ReportsGenerator generator;

    @TempDir
    Path tempDir;

    private List<Bid> bids;

    private static List<String> texts(JasperPrint print) {
        return print.getPages().stream()
                .map(JRPrintPage::getElements)
                .flatMap(List::stream)
                .filter(JRPrintText.class::isInstance)
                .map(element -> ((JRPrintText) element).getFullText())
                .collect(Collectors.toList());
    }

    @BeforeAll
    static void compileTemplates() {
        generator = new ReportsGenerator(null);
        generator.precompileReports();
    }

    @BeforeEach
    void createBids() {
        Institute institute = ItFixtures.institute(storage, "Інститут біохімії");
        Department department = ItFixtures.department(storage, institute, DEPARTMENT);
        Subdepartment subdepartment = ItFixtures.subdepartment(storage, department, "Лабораторія");
        Finance finance = ItFixtures.finance(storage, "F-1", "2024-01-01", "2024-12-31", "1000", "1000", "1000");
        FinanceDepartment financeDepartment = ItFixtures.financeDepartment(storage, finance, subdepartment, "1000", "1000", "1000");
        AmountUnit unit = new AmountUnit("упак.");
        storage.AMOUNTUNITS.create(unit);
        Bid soya = ItFixtures.bid(storage, financeDepartment, BidType.MATERIALS, "10.50", 3, DESCRIPTION,
                storage.CPV.validateCode("03111100-3"));
        Bid office = ItFixtures.bid(storage, financeDepartment, BidType.EQUIPMENT, "7", 2, "Принтер",
                storage.CPV.validateCode("30000000-9"));
        for (Bid bid : new Bid[]{soya, office}) {
            bid.setAmountUnit(unit);
            storage.BIDS.update(bid);
        }
        bids = storage.BIDS.retrieve(new GetBidsQuery(2024));
    }

    @Test
    void fillReport_bidsLoadedFromDatabase_containsCyrillicDataAndExportsToPdf() throws Exception {
        List<BidReportModel> models = bids.stream().map(BidReportModel::new).collect(Collectors.toList());
        Map<String, Object> parameters = new HashMap<>();
        for (String name : new String[]{"headRoleName", "headName", "departmentHeadName", "personallyLiableEmpName",
                "accountantName", "economistName", "headTenderName", "customerName", "customerPhone", "generatedDate"}) {
            parameters.put(name, "Іваненко " + name);
        }

        JasperPrint print = generator.fillReport(new BidsReportRequest(parameters, models));

        assertThat(print.getPages()).isNotEmpty();
        List<String> texts = texts(print);
        assertThat(texts).as("bid description").anyMatch(text -> text.contains(DESCRIPTION));
        assertThat(texts).as("department").anyMatch(text -> text.contains(DEPARTMENT));
        assertThat(texts).as("second bid").anyMatch(text -> text.contains("Принтер"));
        assertThat(texts).as("parameter").anyMatch(text -> text.contains("Іваненко headRoleName"));
        assertPdfExports(print);
    }

    @Test
    void fillReport_cpvAmountsGroupedFromDatabase_containsCyrillicDataAndExportsToPdf() throws Exception {
        List<CpvAmount> amounts = CpvAmount.groupByCpvAndType(bids,
                cpvCode -> storage.CPV.get(new CpvRequestQuery(cpvCode, 0)).get(0));
        List<CpvAmountReportModel> models = amounts.stream().map(CpvAmountReportModel::new).collect(Collectors.toList());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("year", 2024);
        parameters.put("organization", "Інститут біохімії");
        parameters.put("edrpou", 12345678);
        parameters.put("headTender", "Петренко П. П.");
        parameters.put("secretaryTender", "Сидоренко С. С.");
        parameters.put("resolutionDate", "01.02.2024");
        parameters.put("resolutionNum", "42");

        JasperPrint print = generator.fillReport(new CpvAmountsReportRequest(parameters, models));

        assertThat(models).hasSize(2);
        assertThat(print.getPages()).isNotEmpty();
        List<String> texts = texts(print);
        // the template prints the organization in capitals
        assertThat(texts).as("organization and EDRPOU").anyMatch(text -> text.contains("ІНСТИТУТ БІОХІМІЇ, код за ЄДРПОУ 12345678"));
        assertThat(texts).as("CPV name").anyMatch(text -> text.contains(amounts.get(0).getCpv().getCpvUkr()));
        assertPdfExports(print);
    }

    private void assertPdfExports(JasperPrint print) throws Exception {
        Path pdf = tempDir.resolve("report.pdf");

        JasperExportManager.exportReportToPdfFile(print, pdf.toString());

        byte[] content = Files.readAllBytes(pdf);
        assertThat(new String(content, 0, 5, StandardCharsets.ISO_8859_1)).isEqualTo("%PDF-");
        assertThat(content.length).isGreaterThan(1000);
    }
}
