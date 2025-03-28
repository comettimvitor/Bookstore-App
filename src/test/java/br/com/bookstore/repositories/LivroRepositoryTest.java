package br.com.bookstore.repositories;

import br.com.bookstore.entities.Livro;
import br.com.bookstore.enums.Categoria;
import br.com.bookstore.helper.LivroHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LivroRepositoryTest {

    @Mock
    private LivroRepository livroRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirCadastrarLivro() {
        var livro = LivroHelper.cadastraLivro();
        livro.setId(1L);

        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        var livroCadastrado = livroRepository.save(livro);

        assertThat(livroCadastrado).isNotNull().isInstanceOf(Livro.class);
        assertThat(livroCadastrado.getId()).isEqualTo(1L);
        assertThat(livroCadastrado.getTitulo()).isEqualTo("O Hobbit");
        assertThat(livroCadastrado.getAutor()).isEqualTo("J.R.R. Tolkien");
        assertThat(livroCadastrado.getAnoPublicacao()).isEqualTo(1937);
        assertThat(livroCadastrado.getCategoria()).isEqualTo(Categoria.FANTASIA);

        verify(livroRepository, times(1)).save(livro);
    }

    @Test
    void devePermitirAlterarLivro() {
        var livro = LivroHelper.cadastraLivro();
        livro.setId(1L);

        when(livroRepository.save(any(Livro.class))).thenAnswer(invocation -> {
            Livro livroMockado = invocation.getArgument(0);
            livroMockado.setTitulo("O Senhor dos Anéis");
            livroMockado.setAnoPublicacao(1954);
            return livroMockado;
        });

        var livroAlterado = livroRepository.save(livro);

        assertThat(livroAlterado).isNotNull();
        assertThat(livroAlterado.getId()).isEqualTo(1L);
        assertThat(livroAlterado.getTitulo()).isEqualTo("O Senhor dos Anéis");
        assertThat(livroAlterado.getAutor()).isEqualTo("J.R.R. Tolkien");
        assertThat(livroAlterado.getAnoPublicacao()).isEqualTo(1954);
        assertThat(livroAlterado.getCategoria()).isEqualTo(Categoria.FANTASIA);

        verify(livroRepository, times(1)).save(livro);
    }

    @Test
    void permiteDeletarLivro() {
        var livro = LivroHelper.cadastraLivro();
        livro.setId(1L);

        doNothing().when(livroRepository).deleteById(livro.getId());

        livroRepository.deleteById(livro.getId());

        verify(livroRepository, times(1)).deleteById(livro.getId());
    }

    @Test
    void devePermitirSelecionarLivroPorId() {
        var livro = LivroHelper.cadastraLivro();
        livro.setId(1L);

        when(livroRepository.findById(any(Long.class))).thenReturn(Optional.of(livro));

        // .get() no final acessa o valor dentro do Optional
        var livroEncontrado = livroRepository.findById(livro.getId()).get();

        assertThat(livroEncontrado).isNotNull().isInstanceOf(Livro.class);
        assertThat(livroEncontrado).isEqualTo(livro);

        verify(livroRepository, times(1)).findById(livro.getId());
    }

    @Test
    void devePermitirSelecionarLivroPorTitulo() {
        var livro = LivroHelper.cadastraLivro();
        livro.setId(1L);

        when(livroRepository.selecionaLivroPorTitulo(any(String.class))).thenReturn(List.of(livro));

        var livrosEncontrados = livroRepository.selecionaLivroPorTitulo(livro.getTitulo());

        assertThat(livrosEncontrados).isNotNull().isNotEmpty();
        assertThat(livrosEncontrados).hasSize(1);
        assertThat(livrosEncontrados.get(0)).isEqualTo(livro);

        verify(livroRepository, times(1)).selecionaLivroPorTitulo(livro.getTitulo());
    }

    @Test
    void devePermitirSelecionarLivroPorAutor() {
        var livro = LivroHelper.cadastraLivro();
        livro.setId(1L);

        when(livroRepository.selecionaLivroPorAutor(any(String.class))).thenReturn(List.of(livro));

        var livrosEncontrados = livroRepository.selecionaLivroPorAutor(livro.getAutor());

        assertThat(livrosEncontrados).isNotNull().isNotEmpty();
        assertThat(livrosEncontrados).hasSize(1);
        assertThat(livrosEncontrados.get(0)).isEqualTo(livro);

        verify(livroRepository, times(1)).selecionaLivroPorAutor(livro.getAutor());
    }

    @Test
    void devePermitirSelecionarLivroPorCategoria() {
        var livro = LivroHelper.cadastraLivro();
        livro.setId(1L);

        when(livroRepository.selecionaLivroPorCategoria(any(String.class))).thenReturn(List.of(livro));

        var livrosEncontrados = livroRepository.selecionaLivroPorCategoria(livro.getCategoria().getDescricao());

        assertThat(livrosEncontrados).isNotNull().isNotEmpty();
        assertThat(livrosEncontrados).hasSize(1);
        assertThat(livrosEncontrados.get(0)).isEqualTo(livro);

        verify(livroRepository, times(1)).selecionaLivroPorCategoria(livro.getCategoria().getDescricao());
    }

    @Test
    void devePermitirSelecionarTodosOsLivros() {
        var livro = LivroHelper.cadastraLivro();
        livro.setId(1L);

        when(livroRepository.findAll()).thenReturn(List.of(livro));

        var livrosEncontrados = livroRepository.findAll();

        assertThat(livrosEncontrados).isNotNull().isNotEmpty();
        assertThat(livrosEncontrados).hasSize(1);
        assertThat(livrosEncontrados.get(0)).isEqualTo(livro);

        verify(livroRepository, times(1)).findAll();
    }

    @Test
    void existsByTituloAndAutor() {
        var livro = LivroHelper.cadastraLivro();
        livro.setId(1L);

        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        when(livroRepository.existsByTituloAndAutor(livro.getTitulo(), livro.getTitulo())).thenReturn(true);

        livroRepository.save(livro);

        boolean existeLivro = livroRepository.existsByTituloAndAutor(livro.getTitulo(), livro.getTitulo());

        assertThat(existeLivro).isTrue();
    }
}