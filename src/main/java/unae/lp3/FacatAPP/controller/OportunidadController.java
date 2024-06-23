package unae.lp3.FacatAPP.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import unae.lp3.FacatAPP.model.Oportunidad;
import unae.lp3.FacatAPP.repository.OportunidadRepository;

@Controller
@RequestMapping("/oportunidades")
public class OportunidadController {

    @Autowired
    private OportunidadRepository oportunidadRepository;

     @GetMapping("/")
    public String index(Model model) {
        List<Oportunidad> oportunidad = oportunidadRepository.findAll();
        model.addAttribute("oportunidades", oportunidad);
        return "oportunidades/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Oportunidad oportunidad = new Oportunidad();
        model.addAttribute("title", "Agregar oportunidad");
        model.addAttribute("oportunidad", oportunidad);
        return "oportunidades/form";
    }
    

    @GetMapping("/editar/{id}")
    public String editarOportunidad(@PathVariable Integer id, Model model) {
        Optional<Oportunidad> oportunidad = oportunidadRepository.findById(id);
        model.addAttribute("oportunidad", oportunidad);
        model.addAttribute("title", "Editar oportunidad");
        return "oportunidades/form";
    }


    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Oportunidad oportunidad) {
        oportunidadRepository.save(oportunidad);
        return "redirect:/oportunidades/";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Integer id) {
        Oportunidad oportunidad = oportunidadRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        oportunidadRepository.delete(oportunidad);
        return "redirect:/oportunidades/";
    }

}
