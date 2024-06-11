package unae.lp3.FacatAPP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


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
        model.addAttribute("usuarios",usuarioServicio.listarUsuarios());
        return "usuarios/lista"; // Aquí estás especificando la ruta dentro de la carpeta templates
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        List<Rol> roles = rolRepository.findAll();
        Usuario usuario = new Usuario();
        model.addAttribute("roles", roles);
        model.addAttribute("title","Agregar Usuario ");
        model.addAttribute("dato", usuario);

        return "usuarios/form";
    }


    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {
        List<Rol> roles = rolRepository.findAll();
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findById(id);
        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get());
            model.addAttribute("title", "Editar usuario:");
            model.addAttribute("roles", roles);
            return "usuarios/form";
        } else {
            return "redirect:/usuarios/";
        }
    }


    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("usuario") Usuario usuario) {
        usuarioRepositorio.save(usuario);
        return "redirect:/usuarios/";
    }
    

    @GetMapping("/borrar/{id}")
    public String borrar(Model model, Usuario usuario)
     {

        usuarioRepositorio.delete(usuario);
        model.addAttribute("dato",usuario);

        return "redirect:/usuarios/";
    }
}
