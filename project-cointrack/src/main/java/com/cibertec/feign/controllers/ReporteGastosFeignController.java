package com.cibertec.feign.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.feign.dtos.ReporteGastosFeignDTO;
import com.cibertec.feign.services.ExportarExcelGastosService;
import com.cibertec.feign.services.ReporteGastosFeignServices;
import com.cibertec.services.UsuarioService;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
public class ReporteGastosFeignController {
    
    @Autowired
    private ReporteGastosFeignServices reporteGastosService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ExportarExcelGastosService exportarExcelGastosService;
    
    @GetMapping("/reportes/gastos")
    public String gastos(@RequestParam(value = "categoria", required = false) String categoria, Model model) {
        Integer userId = usuarioService.getUsuarioFromAuthentication().getId();
        if (userId == null & categoria == null) {
            return "reportes/gastos";
        }
        
        // Obtener los datos de la API con el filtro de categoría si está presente
        List<ReporteGastosFeignDTO> reportes = reporteGastosService.obtenerGastos(userId, categoria);

        // Mostrar los resultados en la consola para verificar
        System.out.println("Gastos por Categoría:");
        for (ReporteGastosFeignDTO reporte : reportes) {
            System.out.println("Usuario ID: " + reporte.getUsuarioId() + ", Categoría: " + reporte.getCategoria() + ", Monto Total: " + reporte.getMontoTotal());
        }

        // Añadir los resultados al modelo
        model.addAttribute("reportes", reportes);

        // Retornar la vista
        return "reportes/gastos";
    }
    

    @GetMapping("/reportes/gastos/export")
    public ResponseEntity<byte[]> exportarGastos(Model model) {
        Integer userId = usuarioService.getUsuarioFromAuthentication().getId();
        try {
            List<ReporteGastosFeignDTO> reportes = reporteGastosService.obtenerGastos(userId, null);
            ByteArrayOutputStream excelFile = exportarExcelGastosService.exportGastosToExcel(reportes);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=CoinTrack-Gastos.xlsx");

            return ResponseEntity.ok().headers(headers).body(excelFile.toByteArray());
        } catch (Exception e) {
            System.out.println("Error al exportar gastos: " + e.getMessage());
            model.addAttribute("error", "Error al exportar gastos: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}