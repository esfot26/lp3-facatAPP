package unae.lp3.FacatAPP.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import unae.lp3.FacatAPP.model.Rol;



public interface RolRepository  extends JpaRepository<Rol, Integer>{

    Rol findByNombre(String nombre);

}
