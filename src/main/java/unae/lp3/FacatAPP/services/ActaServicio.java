package unae.lp3.FacatAPP.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unae.lp3.FacatAPP.model.Acta;
import unae.lp3.FacatAPP.model.Materia;
import unae.lp3.FacatAPP.model.Oportunidad;
import unae.lp3.FacatAPP.model.Usuario;
import unae.lp3.FacatAPP.repository.ActaRepository;


import java.io.IOException;
//import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ActaServicio {

    @Autowired
    private ActaRepository actaRepository;

    public Acta buscarArchivo(String codigo, MultipartFile file, MultipartFile sheet) throws IOException {
        Acta acta = new Acta();
        acta.setCodigo(codigo);
        if (!file.isEmpty()) {
            acta.setFile(file.getBytes());
        }
        if (!sheet.isEmpty()) {
            acta.setSheet(sheet.getBytes());
        }
        return actaRepository.save(acta);
    }

    public Acta getFile(Integer id) {
        return actaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acta not found with id " + id));
    }

    public void deleteById(Integer id) {
        actaRepository.deleteById(id);
    }

    public List<Acta> findAll() {
        return actaRepository.findAll();
    }

    public Acta getActaById(Integer id) {
        return actaRepository.findById(id).orElse(null);
    }
    
    public Acta saveActa(String codigo, MultipartFile file, MultipartFile sheet, Date date, Date rdate, Usuario usuario, Materia materia, Oportunidad oportunidad) throws IOException {
        Acta acta = new Acta();
        acta.setCodigo(codigo);
        acta.setFile(file.getBytes());
        acta.setSheet(sheet.getBytes());
        acta.setDate(date);
        acta.setRdate(rdate);
        acta.setUsuario(usuario);
        acta.setMateria(materia);
        acta.setOportunidad(oportunidad);
        acta.setCreated_at(LocalDateTime.now());
        acta.setUpdated_at(LocalDateTime.now());
        return actaRepository.save(acta);
    }
}
