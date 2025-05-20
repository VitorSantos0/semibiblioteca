package com.programacaoweb.semibiblioteca.controller;

import com.programacaoweb.semibiblioteca.dto.AutenticacaoDto;
import com.programacaoweb.semibiblioteca.dto.RespostaDto;
import com.programacaoweb.semibiblioteca.dto.UsuarioRequestDto;
import com.programacaoweb.semibiblioteca.model.TipoUsuario;
import com.programacaoweb.semibiblioteca.model.Usuario;
import com.programacaoweb.semibiblioteca.service.UsuarioService;
import com.programacaoweb.semibiblioteca.service.autenticacao.TokenService;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UsuarioService usuarioService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AutenticacaoDto autenticacaoDto) {
        try {
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(autenticacaoDto.login(), autenticacaoDto.senha()));
            Usuario usuario = (Usuario) authentication.getPrincipal();
            if(usuario != null) {
                var token = tokenService.generateToken((Usuario) authentication.getPrincipal());
                return ResponseEntity.ok(new RespostaDto<>(true, "Login realizado com sucesso", token));
            }
            return ResponseEntity.ok(new RespostaDto<>(false, "Login ou senha inválido", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespostaDto<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> registrar(@RequestBody UsuarioRequestDto requestDto) {
        try {
            if(this.usuarioService.findByEmail(requestDto.email()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new RespostaDto<>(false, "E-mail já registrado", null));
            }
            if(this.usuarioService.findByLogin(requestDto.login()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new RespostaDto<>(false, "Login já registrado", null));
            }
            Usuario usuario = new Usuario();
            usuario.setNome(requestDto.nome());
            usuario.setEmail(requestDto.email());
            usuario.setTipo(TipoUsuario.USER);
            usuario.setLogin(requestDto.login());
            usuario.setSenha(new BCryptPasswordEncoder().encode(requestDto.senha()));
            usuario.setMatricula(requestDto.matricula());
            usuario.setCurso(requestDto.curso());
            this.usuarioService.save(usuario);
            return ResponseEntity.ok(new RespostaDto<>(true, "Usuário registrado com sucesso", usuario));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespostaDto<>(false, e.getMessage(), null));
        }
    }

}
