package com.cibertec.feign.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.feign.clients.ReporteGastosFeignClient;
import com.cibertec.feign.dtos.ReporteGastosFeignDTO;

@Service
public class ReporteGastosFeignServices {

    @Autowired
    private ReporteGastosFeignClient reporteGastosClient;

    public List<ReporteGastosFeignDTO> obtenerGastos(Integer i, String categoria) {
    	if(categoria == null) {
    		return reporteGastosClient.obtenerGastosTotal(i);
    	}
        return reporteGastosClient.obtenerGastosCategoria(i, categoria);
    }
}
