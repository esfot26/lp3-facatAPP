package unae.lp3.FacatAPP.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import unae.lp3.FacatAPP.model.Carrera;
import unae.lp3.FacatAPP.model.Facultad;
import unae.lp3.FacatAPP.repository.CarreraRepository;
import unae.lp3.FacatAPP.repository.facultadRepository;



@Controller
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private facultadRepository facultadRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Carrera> carrera = carreraRepository.findAll();
        model.addAttribute("carreras", carrera);
        return "carreras/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Carrera carrera = new Carrera();
        List<Facultad> facultades = facultadRepository.findAll();
        model.addAttribute("carrera", carrera);
        model.addAttribute("facultades", facultades);
        return "carreras/form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Carrera carrera = carreraRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        List<Facultad> facultades = facultadRepository.findAll();
        model.addAttribute("carrera", carrera);
        model.addAttribute("facultades", facultades);
        return "carreras/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("carrera") Carrera carrera) {
        carreraRepository.save(carrera);
        return "redirect:/carreras/";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Integer id) {
        Carrera carrera = carreraRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        carreraRepository.delete(carrera);
        return "redirect:/carreras/";
    }
}



