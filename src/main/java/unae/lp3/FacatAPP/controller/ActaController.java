package unae.lp3.FacatAPP.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import unae.lp3.FacatAPP.model.Acta;
import unae.lp3.FacatAPP.model.Materia;
import unae.lp3.FacatAPP.model.Oportunidad;
import unae.lp3.FacatAPP.model.Usuario;
import unae.lp3.FacatAPP.repository.MateriaRepository;
import unae.lp3.FacatAPP.repository.OportunidadRepository;
import unae.lp3.FacatAPP.repository.UsuarioRepositorio;
import unae.lp3.FacatAPP.services.ActaServicio;

@Controller
@RequestMapping("/actas")
public class ActaController {

    @Autowired
    private ActaServicio actaServicio;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private OportunidadRepository oportunidadRepository;

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Acta> actas = actaServicio.findAll();
        model.addAttribute("actas", actas);
        return "actas/lista";  // Replace with your lista.html template path
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Acta acta = new Acta();
        List<Materia> materias = materiaRepository.findAll();
        List<Oportunidad> oportunidades = oportunidadRepository.findAll();
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("oportunidades", oportunidades);
        model.addAttribute("materias", materias);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("acta", acta);
        return "actas/form";
    }

    // @PostMapping("/guardar")
    // public String guardar(@ModelAttribute("acta") Acta acta) {
    //     actaServicio.save(acta);
    //     return "redirect:/actas/";
    // }
@PostMapping("/guardar")
public String guardar(@ModelAttribute("acta") Acta acta,
                      @RequestParam("file") MultipartFile file,
                      @RequestParam("sheet") MultipartFile sheet) {
    try {
        // Convertir el contenido de los archivos a una representación de String (por ejemplo, Base64)
        String fileContent = Base64.getEncoder().encodeToString(file.getBytes());
        String sheetContent = Base64.getEncoder().encodeToString(sheet.getBytes());

        // Guardar el contenido de los archivos como String
        acta.setFile(fileContent);
        acta.setSheet(sheetContent);

        // Luego puedes guardar el objeto Acta
        actaServicio.save(acta,file,sheet);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "redirect:/actas/";
}

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Acta acta = actaServicio.findById(id);
        List<Materia> materias = materiaRepository.findAll();
        List<Oportunidad> oportunidades = oportunidadRepository.findAll();
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("acta", acta);
        model.addAttribute("oportunidades", oportunidades);
        model.addAttribute("materias", materias);
        model.addAttribute("usuarios", usuarios);
        return "actas/form";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Integer id) {
        actaServicio.deleteById(id);
        return "redirect:/actas/";
    }
}
