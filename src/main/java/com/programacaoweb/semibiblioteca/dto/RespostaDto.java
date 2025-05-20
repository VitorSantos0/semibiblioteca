package com.programacaoweb.semibiblioteca.dto;

public record RespostaDto<T>(Boolean success, String message, T response) {
}
