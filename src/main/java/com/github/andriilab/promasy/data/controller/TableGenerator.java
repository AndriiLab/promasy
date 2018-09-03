package com.github.andriilab.promasy.data.controller;

import com.github.andriilab.promasy.domain.bid.entities.Bid;
import com.github.andriilab.promasy.presentation.MainFrame;
import com.github.andriilab.promasy.presentation.commons.Icons;
import com.github.andriilab.promasy.presentation.commons.Labels;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generates XLS files with {@link Bid} data
 */
public class TableGenerator {
    private final MainFrame parent;

    public TableGenerator(MainFrame parent) {
        this.parent = parent;
    }

    private static List<Bid> preSort(List<Bid> bids) {
        Map<Integer, Bid> map = new HashMap<>();
        for (Bid bid : bids) {
            int key = (bid.getCpv().getCpvId() + bid.getKEKV() + bid.getAmountUnit() + bid.getOnePrice()).hashCode();
            if (map.containsKey(key)) {
                map.get(key).setTransientAmount(map.get(key).getTransientAmount() + bid.getTransientAmount());
            } else {
                map.put(key, bid);
            }
        }
        return map.entrySet().stream().map(Map.Entry::getValue).sorted(Comparator.comparing(Bid::getKEKV)
                .thenComparing(bm -> bm.getCpv().getCpvId())
                .thenComparing(bm -> bm.getAmountUnit().getModelId())).collect(Collectors.toList());
    }

    private static int columnWidth(int chars) {
        return chars * 256;
    }

    private static HSSFCell makeCell(HSSFRow row, int column, HSSFCellStyle style) {
        HSSFCell cell = row.createCell(column);
        cell.setCellStyle(style);
        return cell;
    }

    private static HSSFCell createCell(HSSFRow row, int column, HSSFCellStyle style, double value) {
        HSSFCell cell = makeCell(row, column, style);
        cell.setCellValue(value);
        return cell;
    }

    private static HSSFCell createCell(HSSFRow row, int column, HSSFCellStyle style, int value) {
        HSSFCell cell = makeCell(row, column, style);
        cell.setCellValue(value);
        return cell;
    }

    private static HSSFCell createCell(HSSFRow row, int column, HSSFCellStyle style, String value) {
        HSSFCell cell = makeCell(row, column, style);
        cell.setCellValue(value);
        return cell;
    }

    public void generateReport(List<Bid> bids, String name) {
        bids = preSort(bids);

        name = name.replace(" ", "_");

        String fileName = "export_" + name + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet spreadsheet = workbook.createSheet("export");
        int rowNum = 0;
        int colNum = 0;
        HSSFRow row = spreadsheet.createRow(rowNum);
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderTop(BorderStyle.THICK);
        headerStyle.setBorderBottom(BorderStyle.THICK);
        headerStyle.setBorderLeft(BorderStyle.THICK);
        headerStyle.setBorderRight(BorderStyle.THICK);
        headerStyle.setWrapText(true);
        headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFFont subHeaderFont = workbook.createFont();
        subHeaderFont.setBold(true);
        HSSFCellStyle subHeaderStyle = workbook.createCellStyle();
        subHeaderStyle.setBorderTop(BorderStyle.THIN);
        subHeaderStyle.setBorderBottom(BorderStyle.THIN);
        subHeaderStyle.setBorderLeft(BorderStyle.THIN);
        subHeaderStyle.setBorderRight(BorderStyle.THIN);
        subHeaderStyle.setWrapText(true);
        subHeaderStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
        subHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        subHeaderStyle.setFont(subHeaderFont);

        HSSFFont generalFont = workbook.createFont();
        generalFont.setItalic(true);
        HSSFCellStyle generalStyle = workbook.createCellStyle();
        generalStyle.setBorderTop(BorderStyle.THIN);
        generalStyle.setBorderBottom(BorderStyle.THIN);
        generalStyle.setBorderLeft(BorderStyle.THIN);
        generalStyle.setBorderRight(BorderStyle.THIN);
        generalStyle.setWrapText(true);
        generalStyle.setFont(generalFont);

        String[] headerTitles = {"", Labels.getProperty("indicators"), Labels.getProperty("units"), Labels.getProperty("amount"), Labels.getProperty("oneUnitPrice"), Labels.getProperty("summ")};

        for (String title : headerTitles) {
            createCell(row, colNum++, headerStyle, title);
        }

        spreadsheet.setColumnWidth(0, columnWidth(5));
        spreadsheet.setColumnWidth(1, columnWidth(50));
        spreadsheet.setColumnWidth(2, columnWidth(9));
        spreadsheet.setColumnWidth(3, columnWidth(9));
        spreadsheet.setColumnWidth(4, columnWidth(15));
        spreadsheet.setColumnWidth(5, columnWidth(15));

        int kekv = 0;
        int groupSize = 0;
        Map<Integer, Integer> groups = new HashMap<>();

        for (Bid bid : bids) {
            if (bid.getKEKV() != kekv) {
                rowNum++;
                groupSize = 0;
                row = spreadsheet.createRow(rowNum);
                for (int col = 0; col < 5; col++) {
                    switch (col) {
                        case 0:
                            createCell(row, col, subHeaderStyle, bid.getKEKV());
                            break;
                        case 2:
                            createCell(row, col, subHeaderStyle, Labels.getProperty("uah"));
                            break;
                        default:
                            createCell(row, col, subHeaderStyle, "");
                            break;
                    }
                }
            }
            groupSize++;
            row = spreadsheet.createRow(++rowNum);
            colNum = 0;
            int groupHeader = rowNum - groupSize;
            groups.put(groupHeader, groupSize);
            kekv = bid.getKEKV();

            createCell(row, colNum++, generalStyle, "");
            createCell(row, colNum++, generalStyle, bid.getCpv().getCpvUkr());
            createCell(row, colNum++, generalStyle, bid.getAmountUnit().getDescription());
            createCell(row, colNum++, generalStyle, bid.getTransientAmount());
            createCell(row, colNum++, generalStyle, bid.getOnePrice().doubleValue());
            HSSFCell totalPriceCell = makeCell(row, colNum, generalStyle);
            totalPriceCell.setCellType(CellType.FORMULA);
            totalPriceCell.setCellFormula(String.format("D%d*E%d", rowNum + 1, rowNum + 1));

            //erasing bid transient amount
            bid.clearTransientAmount();
        }

        for (Integer key : groups.keySet()) {
            row = spreadsheet.getRow(key);
            HSSFCell totalCell = makeCell(row, 5, subHeaderStyle);
            totalCell.setCellType(CellType.FORMULA);
            totalCell.setCellFormula(String.format("SUM(F%d:F%d)", key + 2, key + 1 + groups.get(key)));
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
            parent.showFileSavedDialog(fileName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent, Labels.withSpaceAfter("fileSaveError") + fileName, Labels.getProperty("error"), JOptionPane.ERROR_MESSAGE, Icons.ERROR);
            Logger.errorEvent(this.getClass(), parent, e);
        }
    }
}
