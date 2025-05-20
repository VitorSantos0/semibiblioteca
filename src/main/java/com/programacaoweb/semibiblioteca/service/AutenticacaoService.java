package com.programacaoweb.semibiblioteca.service;

import com.programacaoweb.semibiblioteca.dto.UsuarioRequestDto;
import com.programacaoweb.semibiblioteca.model.TipoUsuario;
import com.programacaoweb.semibiblioteca.model.Usuario;
import com.programacaoweb.semibiblioteca.service.autenticacao.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UsuarioService usuarioService;

    public AutenticacaoService(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    public String autenticar(String login, String senha) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, senha));
        Usuario usuario = (Usuario) authentication.getPrincipal();
        if(usuario != null) return tokenService.generateToken(usuario);
        return null;
    }

    public Usuario registrar(UsuarioRequestDto requestDto) {
        if(this.usuarioService.findByEmail(requestDto.email()) != null) return null;
        if(this.usuarioService.findByLogin(requestDto.login()) != null) return null;
        Usuario usuario = new Usuario();
        usuario.setNome(requestDto.nome());
        usuario.setEmail(requestDto.email());
        usuario.setTipo(requestDto.tipo().equals("USER") ? TipoUsuario.USER : TipoUsuario.ADM);
        usuario.setLogin(requestDto.login());
        usuario.setSenha(new BCryptPasswordEncoder().encode(requestDto.senha()));
        usuario.setMatricula(requestDto.matricula());
        usuario.setCurso(requestDto.curso());
        this.usuarioService.save(usuario);
        return usuario;
    }
}
