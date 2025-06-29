package com.cibertec.feign.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReporteGastosFeignDTO {

    private Long id;
    private Long usuarioId;
    private String categoria;
    private BigDecimal montoTotal;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDateTime fechaReporte;
}
