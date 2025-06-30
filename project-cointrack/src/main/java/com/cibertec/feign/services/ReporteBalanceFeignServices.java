package com.cibertec.feign.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cibertec.feign.clients.ReporteBalanceFeignClient;
import com.cibertec.feign.dtos.ReporteBalanceFeignDTO;

@Service
public class ReporteBalanceFeignServices {
	
    @Autowired
    private ReporteBalanceFeignClient reporteBalanceClient;

    public List<ReporteBalanceFeignDTO> obtenerGastos(Integer i, Integer anioInicio, Integer mesInicio, Integer anioFin, Integer mesFin) {
    	if(anioInicio == null || anioFin == null || mesInicio == null || mesFin == null) {
    		return reporteBalanceClient.obtenerBalanceTotal(i);
    	}
        return reporteBalanceClient.obtenerBalanceRango(i, anioInicio, mesInicio, anioFin, mesFin);
    }
}