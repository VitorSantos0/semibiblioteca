package com.programacaoweb.semibiblioteca.dto;

import org.antlr.v4.runtime.misc.NotNull;

public class UsuarioDto {
    @NotNull
    public String nome;
    @NotNull
    public String email;
    @NotNull
    public String login;
    @NotNull
    public String senha;
    @NotNull
    public String tipo;
    public String matricula;
    public String curso;

    public UsuarioDto(String nome, String email, String login, String senha, String tipo, String matricula, String curso) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
        this.matricula = matricula;
        this.curso = curso;
    }

}
