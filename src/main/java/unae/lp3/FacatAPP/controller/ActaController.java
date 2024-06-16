package unae.lp3.FacatAPP.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
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
import unae.lp3.FacatAPP.repository.ActaRepository;
import unae.lp3.FacatAPP.repository.MateriaRepository;
import unae.lp3.FacatAPP.repository.OportunidadRepository;
import unae.lp3.FacatAPP.repository.UsuarioRepositorio;
import unae.lp3.FacatAPP.services.ActaServicio;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;




@Controller
@RequestMapping("/actas")
public class ActaController {

    @Autowired
    private ActaRepository actaRepository;

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
        return "actas/lista";  
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

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Acta acta, @RequestParam MultipartFile file, @RequestParam MultipartFile sheet) {
        try {
            actaServicio.save(acta, file, sheet);
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


        @GetMapping("/verArchivo/{id}")
        public ResponseEntity<Resource> verArchivo(@PathVariable Integer id) {
            Acta acta = actaRepository.findById(id).orElse(null);
            if (acta == null || acta.getFile() == null) {
                return ResponseEntity.notFound().build();
            }

            ByteArrayResource resource = new ByteArrayResource(acta.getFile());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + acta.getCodigo() + ".pdf\"")
                    .body(resource);
        }

        @GetMapping("/verArchivo/{id}/sheet")
        public ResponseEntity<Resource> verArchivoSheet(@PathVariable Integer id) {
            Acta acta = actaRepository.findById(id).orElse(null);
            if (acta == null || acta.getSheet() == null) {
                return ResponseEntity.notFound().build();
            }

            ByteArrayResource resource = new ByteArrayResource(acta.getSheet());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + acta.getCodigo() + "_planilla.pdf\"")
                    .body(resource);
        }
}

