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
import unae.lp3.FacatAPP.model.Docente;
import unae.lp3.FacatAPP.model.Materia;
import unae.lp3.FacatAPP.repository.CarreraRepository;
import unae.lp3.FacatAPP.repository.DocentesRepository;
import unae.lp3.FacatAPP.repository.MateriaRepository;


@Controller
@RequestMapping("/materias")
public class MateriaController {

    
    
    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private DocentesRepository docerepo;

    @GetMapping("/")
    public String index(Model model) {
        List<Materia> materias = materiaRepository.findAll();
        List<Carrera> carreras = carreraRepository.findAll();
        List<Docente> docentes = docerepo.findAll();
        model.addAttribute("materias", materias);
        model.addAttribute("carreras", carreras);
        model.addAttribute("docentes", docentes);
        return "materias/lista";
    }
    


    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Materia materia = new Materia();
        List<Carrera> carreras = carreraRepository.findAll();
        List<Docente> docentes = docerepo.findAll();
        model.addAttribute("title", "Crear Nueva Materia:");
        model.addAttribute("materia", materia);
        model.addAttribute("carreras", carreras);
        model.addAttribute("docentes", docentes);
        return "materias/form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Materia materia = materiaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        List<Carrera> carreras = carreraRepository.findAll();
        List<Docente> docentes = docerepo.findAll();
        model.addAttribute("title", "Editar Materia:");
        model.addAttribute("materia", materia);
        model.addAttribute("carreras", carreras);
        model.addAttribute("docentes", docentes);
        return "materias/form";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Integer id) {
        Materia materia = materiaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        materiaRepository.delete(materia);
        return "redirect:/materias/";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Materia materia) {
        materiaRepository.save(materia);
        return "redirect:/materias/";
    }
}
