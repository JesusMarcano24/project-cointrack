package com.cibertec.edu.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPORTES_METAS_AHORRO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteMetasAhorro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private String metaNombre;

    @Column(precision = 10, scale = 2)
    private BigDecimal montoObjetivo;

    @Column(precision = 10, scale = 2)
    private BigDecimal montoActual;

    @Column(precision = 5, scale = 2)
    private BigDecimal porcentajeAvance;

    private LocalDateTime fechaReporte;
}