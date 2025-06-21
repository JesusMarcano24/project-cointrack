package com.cibertec.edu.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPORTES_GASTOS_CATEGORIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteGastosCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private String categoria;

    @Column(name = "monto_total", precision = 10, scale = 2)
    private BigDecimal montoTotal;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private LocalDateTime fechaReporte;
}