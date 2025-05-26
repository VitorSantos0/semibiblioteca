package com.programacaoweb.semibiblioteca.model;

import com.programacaoweb.semibiblioteca.dto.LivroDto;
import jakarta.persistence.*;

@Entity @Table(name = "livros")
public class Livro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String autor;
    private String ano;

    public Livro() {}

    public Livro(String nome, String autor, String ano) {
        this.nome = nome;
        this.autor = autor;
        this.ano = ano;
    }

    public Livro(LivroDto dto) {
        this.nome = dto.nome;
        this.autor = dto.autor;
        this.ano = dto.ano;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}
