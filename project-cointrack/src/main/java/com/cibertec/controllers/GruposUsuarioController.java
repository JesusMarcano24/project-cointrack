package com.cibertec.controllers;

import com.cibertec.models.Grupos;
import com.cibertec.models.GruposUsuario;
import com.cibertec.models.GruposUsuarioId;
import com.cibertec.models.Usuario;
import com.cibertec.services.GruposService;
import com.cibertec.services.GruposUsuarioService;
import com.cibertec.services.UsuarioService;
import com.cibertec.utils.PasswordUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/grupos")
public class GruposUsuarioController {

    private final GruposUsuarioService gruposUsuarioService;
    private final GruposService gruposService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public GruposUsuarioController(GruposUsuarioService gruposUsuarioService, GruposService gruposService, UsuarioService usuarioService,PasswordEncoder passwordEncoder) {
        this.gruposUsuarioService = gruposUsuarioService;
        this.gruposService = gruposService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }



    // Vista para mostrar participantes
    @GetMapping("/{grupoId}/participantes")
    public String verParticipantesGrupo(@PathVariable Integer grupoId, Model model) {
        Optional<Grupos> grupoOpt = gruposService.obtenerGrupoPorId(grupoId);
        if (grupoOpt.isPresent()) {
            Grupos grupo = grupoOpt.get();
            List<GruposUsuario> participantes = gruposUsuarioService.obtenerParticipantesPorGrupo(grupoId);
            BigDecimal totalIngresos = gruposUsuarioService.obtenerTotalIngresos(grupoId);

            model.addAttribute("grupo", grupo);
            model.addAttribute("participantes", participantes);
            model.addAttribute("totalIngresos", totalIngresos);
            return "fragments/participantes_grupos_fragment";
        } else {
            return "redirect:/grupos/lista";
        }
    }

    // Mostrar formulario para agregar participante
    @GetMapping("/{grupoId}/participantes/nuevo")
    public String mostrarFormularioAgregarParticipante(@PathVariable Integer grupoId, Model model) {
        Optional<Grupos> grupoOpt = gruposService.obtenerGrupoPorId(grupoId);
        if (grupoOpt.isPresent()) {
            model.addAttribute("grupo", grupoOpt.get());
            model.addAttribute("usuarios", usuarioService.listarUsuarios()); // para seleccionar de una lista
            return "transacciones_grupos/participantes_form";
        }
        return "redirect:/grupos/lista";
    }

    @PostMapping("/{grupoId}/participantes/guardar")
    public String guardarParticipante(@PathVariable Integer grupoId,
                                      @RequestParam("username") String usuarioInput,
                                      @RequestParam("saldoInicial") BigDecimal saldoInicial,
                                      Model model) {
        try {
            Grupos grupo = gruposService.obtenerGrupoPorId(grupoId)
                    .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado"));

            // Buscar usuario: si es número, asume ID, si no, busca por username
            Usuario usuario;
            try {
                Integer usuarioId = Integer.parseInt(usuarioInput);
                usuario = usuarioService.findById(usuarioId)
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            } catch (NumberFormatException e) {
                // Si no es número, buscar por username o crear nuevo
                usuario = usuarioService.findByUsername(usuarioInput)
                        .orElseGet(() -> {
                            Usuario nuevoUsuario = new Usuario();
                            nuevoUsuario.setUsername(usuarioInput);
                            String passwordAleatoria = PasswordUtils.generarPasswordAleatoria(12);
                            nuevoUsuario.setPassword(passwordEncoder.encode(passwordAleatoria));
                            nuevoUsuario.setRol("USER");
                            return usuarioService.save(nuevoUsuario);
                        });
            }

            // Crear ID compuesto y entidad
            GruposUsuarioId id = new GruposUsuarioId(grupo.getId(), usuario.getId());
            GruposUsuario nuevoParticipante = new GruposUsuario();
            nuevoParticipante.setId(id);
            nuevoParticipante.setGrupos(grupo);
            nuevoParticipante.setUsuario(usuario);
            nuevoParticipante.setSaldoInicial(saldoInicial);
            nuevoParticipante.setSaldoActual(saldoInicial);

            gruposUsuarioService.guardarParticipante(nuevoParticipante);

            return "redirect:/grupos/ver/" + grupoId;
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el participante: " + e.getMessage());
            model.addAttribute("grupo", gruposService.obtenerGrupoPorId(grupoId).orElse(null));
            model.addAttribute("usuarios", usuarioService.listarUsuarios());
            return "transacciones_grupos/participantes_form";
        }
    }


    // Eliminar participante
    @GetMapping("/{grupoId}/participantes/eliminar/{usuarioId}")
    public String eliminarParticipante(@PathVariable Integer grupoId, @PathVariable Integer usuarioId) {
        gruposUsuarioService.eliminarParticipante(grupoId, usuarioId);
        return  "redirect:/grupos/ver/{grupoId}";
    }



    // === Helper para obtener usuario logueado ===
    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();

            return usuarioService.findByUsername(username).orElseThrow(
                    () -> new IllegalStateException("Usuario no encontrado para el username: " + username));
        }

        throw new IllegalStateException("No se encontró un usuario autenticado.");
    }
}