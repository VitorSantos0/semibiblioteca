package com.programacaoweb.semibiblioteca.dto;

import com.programacaoweb.semibiblioteca.model.Livro;

public class LivroDto {
    public String nome;
    public String autor;
    public String ano;

    public LivroDto() {}

    public LivroDto(String nome, String autor, String ano) {
        this.nome = nome;
        this.autor = autor;
        this.ano = ano;
    }

    public LivroDto(Livro livro) {
        this.nome = livro.getNome();
        this.autor = livro.getAutor();
        this.ano = livro.getAno();
    }

}
