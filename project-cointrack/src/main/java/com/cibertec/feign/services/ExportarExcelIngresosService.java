package com.cibertec.feign.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.cibertec.feign.dtos.ReporteIngresosMensualesDTO;

@Service
public class ExportarExcelIngresosService {

    public ByteArrayOutputStream exportIngresosToExcel(List<ReporteIngresosMensualesDTO> reportes) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ingresos Mensuales");

        // Estilos
        Font titleFont = workbook.createFont();
        titleFont.setFontName("Calibri");
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.DARK_BLUE.getIndex());

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Título
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("CoinTrack - Reporte de Ingresos Mensuales");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

        // Cabecera
        Row headerRow = sheet.createRow(1);
        String[] headers = {"Usuario ID", "Año", "Mes", "Total Ingresos", "Fecha Reporte"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Datos
        int rowNum = 2;
        for (ReporteIngresosMensualesDTO reporte : reportes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(reporte.getUsuarioId());
            row.createCell(1).setCellValue(reporte.getAnio());
            row.createCell(2).setCellValue(reporte.getMes());
            row.createCell(3).setCellValue(reporte.getTotalIngresos() != null ? reporte.getTotalIngresos().doubleValue() : 0);
            row.createCell(4).setCellValue(reporte.getFechaReporte() != null ? reporte.getFechaReporte().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : "");
        }

        // Ajustar ancho de columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Pie
        int footerRowNum = rowNum + 2;
        Row footerRow = sheet.createRow(footerRowNum);
        Cell footerCell = footerRow.createCell(0);
        footerCell.setCellValue("Reporte generado el " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out;
    }
}
