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
import unae.lp3.FacatAPP.repository.UsuarioRepositorio;

@Service
@Transactional
public class UsuarioServicio implements UserDetailsService {

    @Autowired 
    private UsuarioRepositorio usuarioRepositorio;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public void guardar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepositorio.save(usuario);
    }

    public Usuario findByUsername(String username) {
        return usuarioRepositorio.findByUsername(username);
    }

    @Transactional
    public void registrarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
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

    public Usuario buscarPorId(Integer id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    @Transactional
    public boolean actualizarUsuario(Integer id, Usuario usuarioDatos) {
        Usuario usuarioExistente = usuarioRepositorio.findById(id).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setName(usuarioDatos.getName());
            usuarioExistente.setEmail(usuarioDatos.getEmail());
            usuarioExistente.setUsername(usuarioDatos.getUsername());
            
            // Verificar y cifrar la nueva contraseña solo si es diferente y no está vacía
            if (usuarioDatos.getPassword() != null && !usuarioDatos.getPassword().isEmpty() &&
                !passwordEncoder.matches(usuarioDatos.getPassword(), usuarioExistente.getPassword())) {
                usuarioExistente.setPassword(passwordEncoder.encode(usuarioDatos.getPassword()));
            }
    
            usuarioRepositorio.save(usuarioExistente);
            return true;
        }
        return false;
    }
    
    public boolean validarAnteriorpassword(Usuario usuario, String oldPassword) {
        return passwordEncoder.matches(oldPassword, usuario.getPassword());
    }

    public void cambiarPassword(Usuario usuario, String newPassword) {
        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuarioRepositorio.save(usuario);
    }
    
}
