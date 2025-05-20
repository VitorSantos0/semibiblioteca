package com.programacaoweb.semibiblioteca.service;

import com.programacaoweb.semibiblioteca.model.Usuario;
import com.programacaoweb.semibiblioteca.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return this.usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return this.usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        this.usuarioRepository.deleteById(id);
    }

    public UserDetails findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public UserDetails findByEmail(String email){
        return this.usuarioRepository.findByEmail(email);
    }

}
