package com.cibertec.controllers;

import com.cibertec.models.Grupos;
import com.cibertec.models.Transacciones;
import com.cibertec.models.Usuario;
import com.cibertec.services.CategoriaService;
import com.cibertec.services.GruposService;
import com.cibertec.services.TransaccionesGrupalesService;
import com.cibertec.services.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grupos/{grupoId}/transacciones-grupales")
public class TransaccionesGrupalesController {

    private final TransaccionesGrupalesService transaccionesGrupalesService;
    private final CategoriaService categoriaService;
    private final GruposService gruposService;
    private final UsuarioService usuarioService;

    public TransaccionesGrupalesController(TransaccionesGrupalesService transaccionesGrupalesService,
                                           CategoriaService categoriaService,
                                           GruposService gruposService,
                                           UsuarioService usuarioService) {
        this.transaccionesGrupalesService = transaccionesGrupalesService;
        this.categoriaService = categoriaService;
        this.gruposService = gruposService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/new")
    public String agregarTransaccionForm(@PathVariable Integer grupoId, Model model) {
        try {
            model.addAttribute("transaccion", new Transacciones());
            model.addAttribute("grupoId", grupoId);
            model.addAttribute("categorias", categoriaService.listarCategorias());
            return "transacciones_grupos/agregartransaccion_form";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el formulario: " + e.getMessage());
            return "redirect:/grupos";
        }
    }

    // Guardar nueva transacción grupal
    @PostMapping("/new")
    public String guardarTransaccion(@PathVariable Integer grupoId,
                                     @ModelAttribute Transacciones transaccion,
                                     Model model) {
        try {
            Usuario usuario = obtenerUsuarioAutenticado();
            Grupos grupo = gruposService.obtenerGrupoPorId(grupoId)
                    .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado"));

            transaccion.setUsuario(usuario);
            transaccion.setGrupo(grupo);
            transaccionesGrupalesService.guardar(transaccion);

            return "redirect:/grupos/ver/" + grupoId;
        } catch (Exception e) {
            model.addAttribute("transaccion", transaccion);
            model.addAttribute("grupoId", grupoId);
            model.addAttribute("categorias", categoriaService.listarCategorias());

            model.addAttribute("error", "Error al guardar la transacción: " + e.getMessage());
            return "transacciones_grupos/agregartransaccion_form";
        }
    }

    // Mostrar formulario de edición
    @GetMapping("/{id}/edit")
    public String editarTransaccionForm(@PathVariable Integer grupoId,
                                    @PathVariable Integer id,
                                    Model model) {
        try {
            Transacciones transaccion = transaccionesGrupalesService.obtenerPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Transacción no encontrada"));

            model.addAttribute("transaccion", transaccion);
            model.addAttribute("grupoId", grupoId);
            model.addAttribute("categorias", categoriaService.listarCategorias());
            return "transacciones_grupos/actualizartransaccion_form";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar la transacción: " + e.getMessage());
            return "redirect:/grupos/" + grupoId + "/detalles";
        }
    }

    // Editar transacción
    @PostMapping("/{id}/edit")
    public String editarTransaccion(@PathVariable Integer grupoId,
                                        @PathVariable Integer id,
                                        @ModelAttribute Transacciones transaccion,
                                        Model model) {
        try {
            transaccion.setId(id);
            transaccion.setGrupo(gruposService.obtenerGrupoPorId(grupoId).orElse(null));
            transaccion.setUsuario(obtenerUsuarioAutenticado()); // ← esta línea es clave
            transaccionesGrupalesService.actualizar(transaccion);
            return "redirect:/grupos/ver/" + grupoId;
        } catch (Exception e) {
            model.addAttribute("transaccion", transaccion);
            model.addAttribute("error", "Error al actualizar: " + e.getMessage());
            model.addAttribute("grupoId", grupoId);
            model.addAttribute("categorias", categoriaService.listarCategorias());

            return "transacciones_grupos/actualizartransaccion_form";
        }
    }

    // Eliminar transacción
    @GetMapping("/{id}/delete")
    public String deleteTransaccionGrupal(@PathVariable Integer grupoId,
                                          @PathVariable Integer id,
                                          Model model) {
        try {
            transaccionesGrupalesService.eliminar(id);
            return "redirect:/grupos/ver/" + grupoId;
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar la transacción: " + e.getMessage());
            return "transacciones_grupos/index";
        }
    }

    // Helpers personalizados
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