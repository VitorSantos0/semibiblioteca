package com.programacaoweb.semibiblioteca.service;

import com.programacaoweb.semibiblioteca.model.Livro;
import com.programacaoweb.semibiblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {
    LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> findAll() {
        return this.livroRepository.findAll();
    }

    public Livro findById(Long id) {
        return this.livroRepository.findById(id).orElse(null);
    }

    public Livro save(Livro livro) {
        return this.livroRepository.save(livro);
    }

    public void delete(Long id) {
        this.livroRepository.deleteById(id);
    }

}
