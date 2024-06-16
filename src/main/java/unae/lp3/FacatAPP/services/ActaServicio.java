package unae.lp3.FacatAPP.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unae.lp3.FacatAPP.model.Acta;
import unae.lp3.FacatAPP.repository.ActaRepository;

import java.io.IOException;
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

    public Acta findById(Integer id) {
        return actaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acta not found with id " + id));
    }

    public Acta save(Acta acta, MultipartFile file, MultipartFile sheet) throws IOException {
        if (!file.isEmpty()) {
            acta.setFile(file.getBytes());
        }
        if (!sheet.isEmpty()) {
            acta.setSheet(sheet.getBytes());
        }
        return actaRepository.save(acta);
    }
}
