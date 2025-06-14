package com.cibertec.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GruposUsuarioId implements Serializable {
    @Column(name = "grupo_id")
    private Integer grupoId;

    @Column(name = "usuario_id")
    private Integer usuarioId;
}
