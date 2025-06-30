package com.cibertec.feign.services;

import com.cibertec.feign.dtos.ReporteGastosFeignDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportarExcelGastosService {

    public ByteArrayOutputStream exportGastosToExcel(List<ReporteGastosFeignDTO> reportes) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Gastos");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Usuario ID");
        header.createCell(1).setCellValue("Categoría");
        header.createCell(2).setCellValue("Monto Total");
        header.createCell(3).setCellValue("Fecha Inicio");
        header.createCell(4).setCellValue("Fecha Fin");
        header.createCell(5).setCellValue("Fecha Reporte");

        int rowNum = 1;
        for (ReporteGastosFeignDTO reporte : reportes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(reporte.getUsuarioId());
            row.createCell(1).setCellValue(reporte.getCategoria());
            row.createCell(2).setCellValue(reporte.getMontoTotal() != null ? reporte.getMontoTotal().doubleValue() : 0);
            row.createCell(3).setCellValue(reporte.getFechaInicio() != null ? reporte.getFechaInicio().toString() : "");
            row.createCell(4).setCellValue(reporte.getFechaFin() != null ? reporte.getFechaFin().toString() : "");
            row.createCell(5).setCellValue(reporte.getFechaReporte() != null ? reporte.getFechaReporte().toString() : "");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }
}
