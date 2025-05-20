package com.programacaoweb.semibiblioteca.service.autenticacao;

import com.programacaoweb.semibiblioteca.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutorizacaoService implements UserDetailsService {
    final UsuarioRepository usuarioRepository;

    public AutorizacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDetails userDetails = usuarioRepository.findByLogin(login);
        if(userDetails == null) throw new UsernameNotFoundException("Usuário não encontrado");
        return userDetails;
    }

}
