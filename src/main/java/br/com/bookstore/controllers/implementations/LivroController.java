package br.com.bookstore.controllers.implementations;

import br.com.bookstore.controllers.interfaces.ILivroController;
import br.com.bookstore.dtos.LivroDTO;
import br.com.bookstore.entities.Livro;
import br.com.bookstore.enums.Categoria;
import br.com.bookstore.services.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController implements ILivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @Override
    public ResponseEntity<Livro> salvarLivro(LivroDTO livroDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.salvarLivro(livroDTO));
    }

    @Override
    public ResponseEntity<Livro> alterarLivro(LivroDTO livroDTO, Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.alterarLivro(livroDTO, id));
    }

    @Override
    public ResponseEntity<Void> deletarLivro(Long id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Livro> selecionarLivroPorId(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.selecionarLivroPorId(id));
    }

    @Override
    public ResponseEntity<List<Livro>> selecionarLivroPorTitulo(String titulo) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.selecionarLivroPorTitulo(titulo));
    }

    @Override
    public ResponseEntity<List<Livro>> selecionarLivroPorAutor(String autor) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.selecionarLivroPorAutor(autor));
    }

    @Override
    public ResponseEntity<List<Livro>> selecionarLivroPorCategoria(String categoria) {
        List<Livro> livros = livroService.selecionarLivroPorCategoria(categoria);
        return ResponseEntity.ok(livros);
    }

    @Override
    public ResponseEntity<List<Livro>> selecionarTodosOsLivros() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.selecionarTodosOsLivros());
    }
}
