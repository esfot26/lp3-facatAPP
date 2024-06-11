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
import unae.lp3.FacatAPP.model.Docente;
import unae.lp3.FacatAPP.repository.DocentesRepository;

/**
 *
 * @author ossva
 */
@Controller
@RequestMapping("/docentes")
public class DocenteController {

    @Autowired
    private DocentesRepository mainrepo;

    @GetMapping("/")
    public String index(Model model) {
        List<Docente> datos = new LinkedList<Docente>();
        datos = mainrepo.findAll();
        model.addAttribute("title",
                "Lista de Docentes");
        model.addAttribute("header",
                "Docentes");
        model.addAttribute("datos",
                datos);
        return "docentes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        Docente d = new Docente();

        model.addAttribute("title",
                "Crear Nuevo Profesor:");
        model.addAttribute("dato",
                d);

        return "docentes/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model,
            Docente doce) {
        int id = doce.getId();
        Docente d = mainrepo.findById(id).
                orElse(null);

        model.addAttribute("title",
                "Editar  Docente: ");
        model.addAttribute("dato",
                d);

        return "docentes/form";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(Model model,
            Docente doce) {
        int id = doce.getId();
        mainrepo.deleteById(id);

        return "redirect:/docentes/";
    }

    @PostMapping("/guardar")
    public String guardar(Model model,
            Docente doce) {

        mainrepo.save(doce);
        model.addAttribute("dato",
                doce);
        return "redirect:/docentes/";
    }
}
