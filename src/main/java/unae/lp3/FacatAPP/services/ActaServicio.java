package unae.lp3.FacatAPP.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unae.lp3.FacatAPP.model.Acta;
import unae.lp3.FacatAPP.repository.ActaRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ActaServicio {

    private final ActaRepository actaRepository;

    public ActaServicio(ActaRepository actaRepository) {
        this.actaRepository = actaRepository;
    }



    public List<Acta> findAll() {
        return actaRepository.findAll();
    }

    public Acta findById(Integer id) {
        return actaRepository.findById(id).orElse(null);
    }

    public void save(Acta acta, MultipartFile file, MultipartFile sheet) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);

            // Si el directorio no existe, se crea
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try {
                // Guardar el archivo del acta
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);
                acta.setFile(filePath.toString());
            } catch (IOException e) {
                throw new IOException("No se pudo guardar el archivo del acta: " + fileName, e);
            }
        }

        if (sheet != null && !sheet.isEmpty()) {
            String sheetName = sheet.getOriginalFilename();
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);

            // Si el directorio no existe, se crea
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try {
                // Guardar el archivo de la planilla
                Path sheetPath = uploadPath.resolve(sheetName);
                Files.copy(sheet.getInputStream(), sheetPath);
                acta.setSheet(sheetPath.toString());
            } catch (IOException e) {
                throw new IOException("No se pudo guardar el archivo de la planilla: " + sheetName, e);
            }
        }

        // Guardar la entidad Acta en la base de datos
        actaRepository.save(acta);
    }

    public void deleteById(Integer id) {
        actaRepository.deleteById(id);
    }
}
