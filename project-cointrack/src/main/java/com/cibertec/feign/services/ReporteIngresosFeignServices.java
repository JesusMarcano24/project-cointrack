package com.cibertec.feign.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.feign.clients.ReporteIngresosFeignClient;
import com.cibertec.feign.dtos.ReporteIngresosMensualesDTO;

@Service
public class ReporteIngresosFeignServices {

    @Autowired
    private ReporteIngresosFeignClient reporteApiClient;

    public List<ReporteIngresosMensualesDTO> obtenerIngresosMensuales(int i, Integer anio, Integer mes) {
    	if(anio == null || mes == null ) {
    		return reporteApiClient.obtenerIngresosTotal(i);
    	}
        return reporteApiClient.obtenerIngresosMensuales(i, anio, mes);
    }
}
