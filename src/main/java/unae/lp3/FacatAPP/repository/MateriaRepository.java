package unae.lp3.FacatAPP.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import unae.lp3.FacatAPP.model.Carrera;
import unae.lp3.FacatAPP.model.Materia;


public interface MateriaRepository extends JpaRepository<Materia, Integer> {
    List<Materia> findByCarrera(Carrera carrera);
    
}




