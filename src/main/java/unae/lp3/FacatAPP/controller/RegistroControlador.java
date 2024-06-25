package unae.lp3.FacatAPP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import unae.lp3.FacatAPP.model.Usuario;
import unae.lp3.FacatAPP.services.UsuarioServicio;

@Controller
public class RegistroControlador {

    @Autowired
    private UsuarioServicio servicio;

    // @GetMapping("/login")
    // public String iniciarSesion() {
    //     return "login";
    // }

    // @GetMapping("/")
    // public String verPaginaDeInicio(Model modelo) {
    //     return "index";
    // }

    @GetMapping("/login")
    public String iniciarSesion() {
        return "login";
    }

    @GetMapping("/")
    public String verPaginaDeInicio(Model modelo) {
        return "redirect:/login"; // Redirige siempre al login al acceder a la raíz
    }

    @GetMapping("/index")
    public String mostrarIndex(Model modelo) {
        return "index";
    }

    @GetMapping("/registro")
    public String mostrarFormulario(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("usuario", new Usuario());
		redirectAttributes.addFlashAttribute("mensaje exitoso", "Usuario registrado exitosamente!");
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        servicio.registrarUsuario(usuario);
        return "redirect:/login";
    } 

	 @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicion(@PathVariable("id") Integer id, Model model) {
        Usuario usuarioExistente = servicio.buscarPorId(id);
        if (usuarioExistente != null) {
            model.addAttribute("usuario", usuarioExistente);
            return "editar";
        } else {
            // Manejar el caso en que el usuario no se encuentra
            return "redirect:/registro";
        }
    }

    // Método para procesar la actualización del usuario
    @PostMapping("/editar/{id}")
    public String actualizarUsuario(@PathVariable("id") Integer id, @ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttributes) {
        boolean actualizado = servicio.actualizarUsuario(id, usuario);
        if (actualizado) {
            redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado exitosamente!");
        } else {
            // Manejar el caso en que la actualización falla
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar el usuario.");
        }
        return "redirect:/registro";
    }
}

