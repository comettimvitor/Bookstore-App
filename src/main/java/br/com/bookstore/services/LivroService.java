package br.com.bookstore.services;

import br.com.bookstore.dtos.LivroDTO;
import br.com.bookstore.entities.Livro;
import br.com.bookstore.exceptions.LivroJaCadastradoException;
import br.com.bookstore.exceptions.LivroNaoEncontradoException;
import br.com.bookstore.repositories.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro salvarLivro(LivroDTO livroDTO) {
        var livro = new Livro();

        livro.setTitulo(livroDTO.titulo());
        livro.setAutor(livroDTO.autor());
        livro.setAnoPublicacao(livroDTO.anoPublicacao());
        livro.setCategoria(livroDTO.categoria());

        if (livroRepository.existsByTituloAndAutor(livro.getTitulo(), livro.getAutor())) {
            throw new LivroJaCadastradoException("O livro " + livro.getTitulo() + " do autor " + livro.getAutor() + " ja foi cadastrado.");
        }

        return livroRepository.save(livro);
    }

    public Livro alterarLivro(LivroDTO livroDTO, Long id) {
        var livro = livroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado."));

        livro.setTitulo(livroDTO.titulo());
        livro.setAutor(livroDTO.autor());
        livro.setAnoPublicacao(livroDTO.anoPublicacao());
        livro.setCategoria(livroDTO.categoria());

        if (livroRepository.existsByTituloAndAutor(livro.getTitulo(), livro.getAutor())) {
            throw new LivroJaCadastradoException("O livro " + livro.getTitulo() + " do autor " + livro.getAutor() + " ja foi cadastrado.");
        }

        return livroRepository.save(livro);
    }

    public void deletarLivro(Long id) {
        if(!confereExistenciaLivro(id)) {
            throw new LivroNaoEncontradoException("Livro não encontrado.");
        }

        livroRepository.deleteById(id);
    }

    public Livro selecionarLivroPorId(Long id) {
        return livroRepository.findById(id).orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado."));
    }

    public List<Livro> selecionarLivroPorTitulo(String titulo) {
        var livro = livroRepository.selecionaLivroPorTitulo(titulo);

        if (livro.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não encontrado.");
        }

        return livro;
    }

    public List<Livro> selecionarLivroPorAutor(String autor) {
        var livro = livroRepository.selecionaLivroPorAutor(autor);

        if (livro.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não encontrado.");
        }

        return livro;
    }

    public List<Livro> selecionarLivroPorCategoria(String categoria) {
        var livro = livroRepository.selecionaLivroPorCategoria(categoria);

        if (livro.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não encontrado.");
        }

        return livro;
    }

    public List<Livro> selecionarTodosOsLivros() {
        var livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            throw new LivroNaoEncontradoException("Livros não encontrados.");
        }

        return livros;
    }

    protected boolean confereExistenciaLivro(Long id) {
        var livro = selecionarLivroPorId(id);

        if(livro == null) {
            return false;
        }

        return true;
    }
}
