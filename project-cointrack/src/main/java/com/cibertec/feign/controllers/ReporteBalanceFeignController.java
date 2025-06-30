package com.cibertec.feign.controllers;

import java.io.ByteArrayOutputStream;
import org.springframework.http.HttpHeaders;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.feign.dtos.ReporteBalanceFeignDTO;
import com.cibertec.feign.services.ExportarExcelBalanceService;
import com.cibertec.feign.services.ReporteBalanceFeignServices;
import com.cibertec.models.Usuario;
import com.cibertec.services.UsuarioService;

@Controller
public class ReporteBalanceFeignController {

    @Autowired
    private ReporteBalanceFeignServices reporteBalanceService;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ExportarExcelBalanceService exportarExcelBalanceService;

    @GetMapping("/reportes/balance")
    public String balance(Model model) {
        Integer userId = usuarioService.getUsuarioFromAuthentication().getId();
        if (userId == null) {
            return "reportes/balance";
        }

        List<ReporteBalanceFeignDTO> reportes = reporteBalanceService.obtenerBalance(userId, null, null, null, null);

        // Calcular totales
        double totalIngresos = reportes.stream()
            .mapToDouble(r -> r.getTotalIngresos() != null ? r.getTotalIngresos().doubleValue() : 0)
            .sum();

        double totalGastos = reportes.stream()
            .mapToDouble(r -> r.getTotalGastos() != null ? r.getTotalGastos().doubleValue() : 0)
            .sum();

        double totalBalance = totalIngresos - totalGastos;

        // Agregar atributos al modelo
        model.addAttribute("reportes", reportes);
        model.addAttribute("totalIngresos", totalIngresos);
        model.addAttribute("totalGastos", totalGastos);
        model.addAttribute("totalBalance", totalBalance);

        return "reportes/balance";
    }
    
    @GetMapping("/reportes/balance/export")
    public ResponseEntity<byte[]> exportBalance(Model model) {
    	Integer userId = usuarioService.getUsuarioFromAuthentication().getId();
        try {
            List<ReporteBalanceFeignDTO> reportes = reporteBalanceService.obtenerBalance(userId, null, null, null, null);
            ByteArrayOutputStream excelFile = exportarExcelBalanceService.exportBalanceToExcel(reportes);

            // Configurar los encabezados para la descarga del archivo
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=CoinTrack-BalanceGeneral.xlsx");

            return ResponseEntity.ok().headers(headers).body(excelFile.toByteArray());
        } catch (Exception e) {
            System.out.println("Error al exportar balance: " + e.getMessage());
            model.addAttribute("error", "Error al exportar balance: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}