package com.cibertec.edu.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPORTES_BALANCE_GENERAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteBalanceGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalIngresos;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalGastos;

    @Column(precision = 10, scale = 2)
    private BigDecimal balance;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private LocalDateTime fechaReporte;
}