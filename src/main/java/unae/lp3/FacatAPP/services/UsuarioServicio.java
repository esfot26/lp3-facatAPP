package unae.lp3.FacatAPP.services;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import unae.lp3.FacatAPP.model.Usuario;
//import unae.lp3.FacatAPP.repository.RolRepository;
import unae.lp3.FacatAPP.repository.UsuarioRepositorio;

@Service
@Transactional
public class UsuarioServicio implements UserDetailsService {

    @Autowired 
    private UsuarioRepositorio usuarioRepositorio;

    // @Autowired 
    // private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void guardar(Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepositorio.save(usuario);
    }   

    public Usuario findByUsername(String username){
        return usuarioRepositorio.findByUsername(username);
    }

    

    
    @Transactional
    public void registrarUsuario(Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        // Rol defaultRole = rolRepository.findByNombre("ROLE_SECRETARIO");
        // usuario.setRoles(new HashSet<>(Set.of(defaultRole)));
        usuarioRepositorio.save(usuario);
    }

    @SuppressWarnings("unchecked")
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new org.springframework.security.core.userdetails.User(usuario.getUsername(), usuario.getPassword(), (Collection<? extends GrantedAuthority>) usuario.getRoles());
    }

    public Usuario buscarPorId(Integer id){
        return usuarioRepositorio.findById(id).orElse(null);
    }

    @Transactional
    public boolean actualizarUsuario(Integer id, Usuario usuarioDatos) {
        Usuario usuarioExistente = usuarioRepositorio.findById(id).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setName(usuarioDatos.getName());
            usuarioExistente.setEmail(usuarioDatos.getEmail());
            usuarioExistente.setUsername(usuarioDatos.getUsername());
            usuarioExistente.setPassword(usuarioDatos.getPassword());
            usuarioRepositorio.save(usuarioExistente);
            return true;
        }
        return false;
    }
}
