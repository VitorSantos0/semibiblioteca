package com.programacaoweb.semibiblioteca.controller;

import com.programacaoweb.semibiblioteca.dto.AutenticacaoDto;
import com.programacaoweb.semibiblioteca.dto.RespostaDto;
import com.programacaoweb.semibiblioteca.dto.UsuarioRequestDto;
import com.programacaoweb.semibiblioteca.model.Usuario;
import com.programacaoweb.semibiblioteca.service.AutenticacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    private AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AutenticacaoDto autenticacaoDto) {
        try {
            String token = this.autenticacaoService.autenticar(autenticacaoDto.login(), autenticacaoDto.senha());
            if(token != null) return ResponseEntity.ok(new RespostaDto<>(true, "Login realizado com sucesso", token));
            return ResponseEntity.ok(new RespostaDto<>(false, "Login ou senha inválido", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespostaDto<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> registrar(@RequestBody UsuarioRequestDto requestDto) {
        try {
            Usuario usuario = this.autenticacaoService.registrar(requestDto);
            if(usuario != null) return ResponseEntity.ok(new RespostaDto<>(true, "Usuário registrado com sucesso", usuario));
            return ResponseEntity.ok(new RespostaDto<>(false, "Login ou e-mail já registrado", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespostaDto<>(false, e.getMessage(), null));
        }
    }

}
