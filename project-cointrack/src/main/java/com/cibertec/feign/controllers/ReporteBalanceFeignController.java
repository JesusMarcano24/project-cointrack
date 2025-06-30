package com.cibertec.feign.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cibertec.feign.dtos.ReporteBalanceFeignDTO;
import com.cibertec.feign.services.ReporteBalanceFeignServices;
import com.cibertec.models.Usuario;
import com.cibertec.services.UsuarioService;

@Controller
public class ReporteBalanceFeignController {

    @Autowired
    private ReporteBalanceFeignServices reporteBalanceService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/reportes/balance")
    public String balance(Model model) {
        Integer userId = usuarioService.getUsuarioFromAuthentication().getId();
        if (userId == null) {
            return "reportes/balance";
        }

        List<ReporteBalanceFeignDTO> reportes = reporteBalanceService.obtenerGastos(userId, null, null, null, null);

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
}
