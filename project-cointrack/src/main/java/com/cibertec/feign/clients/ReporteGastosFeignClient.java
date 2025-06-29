package com.cibertec.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cibertec.feign.dtos.ReporteGastosFeignDTO;

import java.util.List;

@FeignClient(name = "reportesApi", url = "http://3.17.29.202:8081/api/reportes")
public interface ReporteGastosFeignClient {

	@GetMapping("gastos-categoria/usuario/{usuarioId}")
	List<ReporteGastosFeignDTO> obtenerGastosTotal(
			@PathVariable("usuarioId") Integer usuarioId 
	);
	
    @GetMapping("gastos-categoria/usuario/{usuarioId}/buscar-categoria/{categoria}")
    List<ReporteGastosFeignDTO> obtenerGastosCategoria(
            @PathVariable("usuarioId") Integer usuarioId, 
            @PathVariable("categoria") String categoria
    );
}
