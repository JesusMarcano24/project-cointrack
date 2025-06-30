package com.cibertec.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.feign.dtos.ReporteIngresosMensualesDTO;
import java.util.List;


@FeignClient(name = "reportesApi", url = "http://localhost:8081/api/reportes")
public interface ReporteIngresosFeignClient {

	@GetMapping("ingresos-mensuales/usuario/{usuarioId}")
	List<ReporteIngresosMensualesDTO> obtenerIngresosTotal(
			@PathVariable("usuarioId") Integer usuarioId 
	);
	
    @GetMapping("ingresos-mensuales/usuario/{usuarioId}/anio/{anio}/mes/{mes}")
    List<ReporteIngresosMensualesDTO> obtenerIngresosMensuales(
            @PathVariable("usuarioId") Integer usuarioId, 
            @PathVariable("anio") Integer anio, 
            @PathVariable("mes") Integer mes
    );
}