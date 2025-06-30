package com.cibertec.feign.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.feign.dtos.ReporteGastosFeignDTO;
import com.cibertec.feign.services.ReporteGastosFeignServices;
import com.cibertec.services.UsuarioService;

import java.util.List;

@Controller
public class ReporteGastosFeignController {
    
    @Autowired
    private ReporteGastosFeignServices reporteGastosService;
    
    @Autowired
    private UsuarioService usuarioService;
    
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
}