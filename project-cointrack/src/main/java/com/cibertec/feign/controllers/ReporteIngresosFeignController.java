package com.cibertec.feign.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.feign.dtos.ReporteIngresosMensualesDTO;
import com.cibertec.feign.services.ReporteIngresosFeignServices;
import com.cibertec.models.Usuario;
import com.cibertec.services.UsuarioService;

import java.util.List;

@Controller
public class ReporteIngresosFeignController {
    
    @Autowired
    private ReporteIngresosFeignServices reporteService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/reportes/ingresos")
    public String ingresos(@RequestParam(value = "anio", required = false) Integer anio,
                           @RequestParam(value = "mes", required = false) Integer mes, Model model) {
        
        Integer userId = usuarioService.getUsuarioFromAuthentication().getId();
        if (userId == null) {
            return "reportes/ingresos";
        }

        // Obtener los datos de la API con el filtro de año y mes si están presentes
        List<ReporteIngresosMensualesDTO> reportes = reporteService.obtenerIngresosMensuales(userId, anio, mes);

        // Mostrar los resultados en la consola para verificar
        System.out.println("Ingresos Mensuales:");
        for (ReporteIngresosMensualesDTO reporte : reportes) {
            System.out.println("Usuario ID: " + reporte.getUsuarioId() + ", Año: " + reporte.getAnio() + ", Mes: " + reporte.getMes() + ", Total Ingresos: " + reporte.getTotalIngresos());
        }

        // Añadir los resultados al modelo
        model.addAttribute("reportes", reportes);

        // Retornar la vista
        return "reportes/ingresos";
    }
}