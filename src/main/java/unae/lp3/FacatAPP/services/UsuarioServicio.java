package unae.lp3.FacatAPP.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import unae.lp3.FacatAPP.controller.dto.UsuarioRegistroDTO;
import unae.lp3.FacatAPP.model.Usuario;


public interface UsuarioServicio extends UserDetailsService{

	public Usuario guardar(UsuarioRegistroDTO registroDTO);
	
	 List<Usuario> listarUsuarios();

     Usuario obtenerUsuario(Integer id);
	
	
	
}
