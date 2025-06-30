package com.cibertec.feign.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.cibertec.feign.dtos.ReporteBalanceFeignDTO;

@Service
public class ExportarExcelBalanceService {

    public ByteArrayOutputStream exportBalanceToExcel(List<ReporteBalanceFeignDTO> reportes) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Balance General");

        // Estilos
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle moneyStyle = createMoneyStyle(workbook);

        // Título
        createTitle(sheet, headerStyle);

        // Cabeceras
        createHeaders(sheet, headerStyle);

        // Datos
        int rowNum = fillData(sheet, reportes, moneyStyle);

        // Totales (si es necesario)
        addTotals(sheet, rowNum, moneyStyle);

        // Ajustes finales
        adjustColumnWidths(sheet);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return headerStyle;
    }

    private CellStyle createMoneyStyle(Workbook workbook) {
        CellStyle moneyStyle = workbook.createCellStyle();
        moneyStyle.setDataFormat((short) 8); // Built-in accounting format
        return moneyStyle;
    }

    private void createTitle(Sheet sheet, CellStyle titleStyle) {
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("CoinTrack - Reporte de Balance General");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
    }

    private void createHeaders(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(1);
        String[] headers = { "Usuario ID", "Total Ingresos", "Total Gastos", "Balance", "Fecha Inicio", "Fecha Fin", "Fecha Reporte" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private int fillData(Sheet sheet, List<ReporteBalanceFeignDTO> reportes, CellStyle moneyStyle) {
        int rowNum = 2;
        for (ReporteBalanceFeignDTO reporte : reportes) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(reporte.getUsuarioId());
            row.createCell(1).setCellValue(reporte.getTotalIngresos().doubleValue());
            row.getCell(1).setCellStyle(moneyStyle);

            row.createCell(2).setCellValue(reporte.getTotalGastos().doubleValue());
            row.getCell(2).setCellStyle(moneyStyle);

            row.createCell(3).setCellValue(reporte.getBalance().doubleValue());
            row.getCell(3).setCellStyle(moneyStyle);

            row.createCell(4).setCellValue(reporte.getFechaInicio().toString());
            row.createCell(5).setCellValue(reporte.getFechaFin().toString());
            row.createCell(6).setCellValue(reporte.getFechaReporte().toString());
        }
        return rowNum;
    }

    private void addTotals(Sheet sheet, int rowNum, CellStyle moneyStyle) {
        // Agregar fila de totales si es necesario
    }

    private void adjustColumnWidths(Sheet sheet) {
        sheet.setColumnWidth(0, 4000); // Usuario ID
        sheet.setColumnWidth(1, 6000); // Total Ingresos
        sheet.setColumnWidth(2, 6000); // Total Gastos
        sheet.setColumnWidth(3, 6000); // Balance
        sheet.setColumnWidth(4, 4000); // Fecha Inicio
        sheet.setColumnWidth(5, 4000); // Fecha Fin
        sheet.setColumnWidth(6, 4000); // Fecha Reporte
    }
}
