package com.cibertec.feign.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.cibertec.feign.dtos.ReporteBalanceFeignDTO;

@FeignClient(name = "reportesApi", url = "http://3.17.29.202:8081/api/reportes")
public interface ReporteBalanceFeignClient {

		@GetMapping("/balance-general/usuario/{usuarioId}")
		List<ReporteBalanceFeignDTO> obtenerBalanceTotal(
				@PathVariable("usuarioId") Integer usuarioId 
		);
		
	    @GetMapping("/balance-general/rango")
	    List<ReporteBalanceFeignDTO> obtenerBalanceRango(
	    		@RequestParam Integer usuarioId,
	            @RequestParam Integer anioInicio,
	            @RequestParam Integer mesInicio,
	            @RequestParam Integer anioFin,
	            @RequestParam Integer mesFin
	    );	
}
