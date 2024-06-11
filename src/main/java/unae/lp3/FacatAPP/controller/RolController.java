package unae.lp3.FacatAPP.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import unae.lp3.FacatAPP.model.Rol;
import unae.lp3.FacatAPP.repository.RolRepository;


@Controller
@RequestMapping("/roles")
public class RolController {
    @Autowired
    private RolRepository rolRepository;    
    
    @GetMapping("/")
    public String index(Model model) 
    {
        List<Rol> r = rolRepository.findAll();
        System.out.println("Roles encontradas: " + r.size());
        model.addAttribute("roles", r);
        return "roles/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) 
    {
        Rol r = new Rol();
        r.getNombre();
        model.addAttribute("title","Agregar  Rol: ");
        model.addAttribute("dato",r);
        return "roles/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, Rol r) 
    {
        int rolid = r.getId();
        Rol r2 = rolRepository.findById(rolid).orElse(null);
        model.addAttribute("title","Editar Rol: ");
        model.addAttribute("dato",r2);
        return "roles/form";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(Model model, Rol r) 
    {
        rolRepository.delete(r);
        model.addAttribute("dato",r);
        return "redirect:/roles/";
    }
        
    @PostMapping("/guardar")
    public String guardar(Model model, Rol r)  
    {
        rolRepository.save(r);
        model.addAttribute("dato",r);
        return "redirect:/roles/";
    }
}