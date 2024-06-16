package unae.lp3.FacatAPP.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unae.lp3.FacatAPP.model.Usuario;



@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{

	public Usuario findByUsername(String username);
	

}
