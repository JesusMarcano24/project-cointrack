package com.cibertec.edu.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPORTES_INGRESOS_MENSUALES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteIngresosMensuales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private Integer anio;

    private Integer mes;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalIngresos;

    private LocalDateTime fechaReporte;
}