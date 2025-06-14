package com.cibertec.services;

import com.cibertec.models.Grupos;
import com.cibertec.models.GruposUsuario;
import com.cibertec.models.GruposUsuarioId;
import com.cibertec.models.Usuario;
import com.cibertec.repositories.GruposUsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GruposUsuarioService {
    private final GruposUsuarioRepository gruposUsuarioRepository;

    public GruposUsuarioService(GruposUsuarioRepository gruposUsuarioRepository) {
        this.gruposUsuarioRepository = gruposUsuarioRepository;
    }

    public GruposUsuario agregarParticipante(Grupos grupo, Usuario usuario, BigDecimal saldoInicial) {
        GruposUsuarioId id = new GruposUsuarioId(grupo.getId(), usuario.getId());
        if (gruposUsuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Este usuario ya pertenece al grupo");
        }

        GruposUsuario participante = new GruposUsuario();
        participante.setId(id);
        participante.setGrupos(grupo);
        participante.setUsuario(usuario);
        participante.setSaldoInicial(saldoInicial);
        participante.setSaldoActual(saldoInicial);

        return gruposUsuarioRepository.save(participante);
    }

    public void eliminarParticipante(Integer grupoId, Integer usuarioId) {
        GruposUsuarioId id = new GruposUsuarioId(grupoId, usuarioId);
        gruposUsuarioRepository.deleteById(id);
    }

    public List<GruposUsuario> obtenerParticipantesPorGrupo(Integer grupoId) {
        return gruposUsuarioRepository.findAll()
                .stream()
                .filter(p -> p.getGrupos().getId().equals(grupoId))
                .toList();
    }

    public BigDecimal obtenerTotalIngresos(Integer grupoId) {
        return obtenerParticipantesPorGrupo(grupoId).stream()
                .map(GruposUsuario::getSaldoInicial)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
