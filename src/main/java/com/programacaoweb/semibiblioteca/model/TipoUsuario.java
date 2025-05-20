package com.programacaoweb.semibiblioteca.model;

public enum TipoUsuario {
    ADM("ADM"),
    USER("USER");
    private String tipo;
    TipoUsuario(String tipo){
        this.tipo = tipo;
    }
    public String getTipo(){
        return tipo;
    }
}
