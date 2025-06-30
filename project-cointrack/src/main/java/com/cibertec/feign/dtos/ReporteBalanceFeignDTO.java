package com.cibertec.feign.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReporteBalanceFeignDTO {
    private Long id;
    private Long usuarioId;
    private BigDecimal totalIngresos;
    private BigDecimal totalGastos;
    private BigDecimal balance;
    private LocalDateTime fechaReporte;
    private java.time.LocalDate fechaInicio;
    private java.time.LocalDate fechaFin;
}