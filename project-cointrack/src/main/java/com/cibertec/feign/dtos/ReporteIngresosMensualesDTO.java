package com.cibertec.feign.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReporteIngresosMensualesDTO {
    private Long id;
    private Long usuarioId;
    private Integer anio;
    private Integer mes;
    private BigDecimal totalIngresos;
    private LocalDateTime fechaReporte;
}