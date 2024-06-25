package unae.lp3.FacatAPP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import unae.lp3.FacatAPP.model.Rol;
import unae.lp3.FacatAPP.model.Usuario;
import unae.lp3.FacatAPP.repository.RolRepository;
import unae.lp3.FacatAPP.repository.UsuarioRepositorio;
import unae.lp3.FacatAPP.services.UsuarioServicio;



@Controller
@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    UsuarioServicio usuarioServicio;
    
    @Autowired
    UsuarioRepositorio usuarioRepositorio;



    @Autowired
    RolRepository rolRepository;

    @GetMapping("/")
    public String listaUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        List<Rol> roles = rolRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("usuarios", usuarios);
        return "usuarios/lista"; // Aquí estás especificando la ruta dentro de la carpeta templates
    }

       @GetMapping("/nuevo")
       public String nuevo(Model model) {
           List<Rol> roles = rolRepository.findAll();
           model.addAttribute("roles", roles);
           model.addAttribute("title", "Agregar Usuario");
           model.addAttribute("usuario", new Usuario());
           return "usuarios/form";
       }
   
       @GetMapping("/editar/{id}")
       public String editar(@PathVariable Integer id, Model model) {
           Optional<Usuario> usuarioOpt = usuarioRepositorio.findById(id);
           if (usuarioOpt.isPresent()) {
               model.addAttribute("usuario", usuarioOpt.get());
               model.addAttribute("roles", rolRepository.findAll());
               model.addAttribute("title", "Editar usuario:");
               return "usuarios/form";
           } else {
               return "redirect:/usuarios/";
           }
       }
   
       @PostMapping("/guardar")
       public String guardar(@ModelAttribute Usuario usuario, @RequestParam("roles") List<Integer> rolesIds, RedirectAttributes redirectAttributes) {
           Set<Rol> roles = rolesIds.stream()
                   .map(rolRepository::findById)
                   .filter(Optional::isPresent)
                   .map(Optional::get)
                   .collect(Collectors.toSet());
           usuario.setRoles(roles);
           usuarioRepositorio.save(usuario);
           redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado exitosamente!");
           return "redirect:/usuarios/";
       }
    
    

    @GetMapping("/borrar/{id}")
    public String borrar(Model model, Usuario usuario)
    {

        usuarioRepositorio.delete(usuario);
        model.addAttribute("dato",usuario);

        return "redirect:/usuarios/";
    }

     @GetMapping("/cambiar-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("title", "Cambiar Contraseña");
        return "usuarios/cambiar_password";
    }

    // Método para procesar el cambio de contraseña
    @PostMapping("/cambiar-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 RedirectAttributes redirectAttributes) {
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioServicio.findByUsername(auth.getName());

        // Verificar la contraseña actual
        if (!usuarioServicio.validarAnteriorpassword(usuario, oldPassword)) {
            redirectAttributes.addFlashAttribute("error", "La contraseña actual es incorrecta.");
            return "redirect:/usuarios/cambiar-password";
        }

        // Cambiar la contraseña
        usuarioServicio.cambiarPassword(usuario, newPassword);
        redirectAttributes.addFlashAttribute("success", "Contraseña cambiada exitosamente.");
        return "redirect:/usuarios/cambiar-password";
    }
}
