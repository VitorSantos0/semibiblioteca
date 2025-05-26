package com.programacaoweb.semibiblioteca.service;

import com.programacaoweb.semibiblioteca.dto.LivroDto;
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

    public List<LivroDto> findAll() {
        List<Livro> listaLivros = this.livroRepository.findAll();
        return listaLivros.stream().map(LivroDto::new).toList();
    }

    public LivroDto findById(Long id) {
        Livro livro =this.livroRepository.findById(id).orElse(null);
        return livro == null ? null : new LivroDto(livro);
    }

    public void save(LivroDto dto) {
        this.livroRepository.save(new Livro(dto));
    }

    public void update(Long id, LivroDto dto) {
        Livro livro = this.livroRepository.findById(id).orElse(null);
        if(livro != null) {
            if(!dto.nome.equals(livro.getNome())) livro.setNome(dto.nome);
            if(!dto.autor.equals(livro.getAutor())) livro.setAutor(dto.autor);
            if(!dto.ano.equals(livro.getAno())) livro.setAno(dto.ano);;
            this.livroRepository.save(livro);
        }
    }

    public void delete(Long id) {
        if(this.findById(id) == null) return;
        this.livroRepository.deleteById(id);
    }

}
