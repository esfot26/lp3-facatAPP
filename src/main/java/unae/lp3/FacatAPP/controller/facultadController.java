/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package unae.lp3.FacatAPP.controller;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import unae.lp3.FacatAPP.model.Facultad;
import unae.lp3.FacatAPP.repository.facultadRepository;

/**
 *
 * @author ossva
 */
@Controller
@RequestMapping("/facultades")
public class facultadController {

    @Autowired
    private facultadRepository facurepo;

    @GetMapping("/")
    public String list(Model model) {
        List<Facultad> facultades = new LinkedList<Facultad>();
        facultades = facurepo.findAll();
        model.addAttribute("title",
                "Lista de Facultades");
        model.addAttribute("header",
                "Facultades");
        model.addAttribute("facultades",
                facultades);
        return "facultades/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Facultad facu = new Facultad();
        model.addAttribute("title",
                "Agregar Facultad ");
        model.addAttribute("dato",
                facu);

        return "facultades/form";
    }

    @GetMapping("/editar/{id}")
    public String actualizar(Model model,
            Facultad facu) {
        int facuid = facu.getId();
        Facultad f = facurepo.findById(facuid).
                orElse(null);

        model.addAttribute("title",
                "Editar Facultad: ");
        model.addAttribute("dato",
                f);

        return "facultades/form";
    }

    @PostMapping("/form")
    public String guardar(Model model,
            Facultad facu)
    {

        facurepo.save(facu);
        model.addAttribute("dato",
                facu);
        return "redirect:/facultades/";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(Model model,
            Facultad facu) {

        facurepo.delete(facu);
        model.addAttribute("dato",
                facu);

        return "redirect:/facultades/";
    }
}
