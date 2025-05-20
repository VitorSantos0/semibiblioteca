package com.programacaoweb.semibiblioteca.dto;

import org.antlr.v4.runtime.misc.NotNull;

public record UsuarioRequestDto(
        @NotNull
        String nome,
        @NotNull
        String email,
        @NotNull
        String login,
        @NotNull
        String senha,
        @NotNull
        String tipo,
        String matricula,
        String curso) {
}
