package com.cibertec.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "GRUPOS_USUARIOS")
public class GruposUsuario {
    @EmbeddedId
    private GruposUsuarioId id;

    @ManyToOne
    @MapsId("grupoId")
    @JoinColumn(name = "grupo_id")
    private Grupos grupos;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;
    @Column(name = "saldo_actual")
    private BigDecimal saldoActual;
}
