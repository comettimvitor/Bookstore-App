package br.com.bookstore.services;

import br.com.bookstore.dtos.LivroDTO;
import br.com.bookstore.entities.Livro;
import br.com.bookstore.enums.Categoria;
import br.com.bookstore.exceptions.LivroJaCadastradoException;
import br.com.bookstore.exceptions.LivroNaoEncontradoException;
import br.com.bookstore.helper.LivroHelper;
import br.com.bookstore.repositories.LivroRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LivroServiceTest {

    @Mock
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        livroService = new LivroService(livroRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        mock.close();
    }

    @Test
    void devePermitirSalvarLivro() {
        // Arrange
        var livroDTO = LivroHelper.preencheLivroDTO();

        // Mockando repositorio
        when(livroRepository.save(any(Livro.class))).thenAnswer(i -> {
            Livro livroSalvo = i.getArgument(0);
            livroSalvo.setId(1L);
            return livroSalvo;
        });

        // Act
        var livroCadastrado = livroService.salvarLivro(livroDTO);

        //Asserts
        assertThat(livroCadastrado).isNotNull().isInstanceOf(Livro.class);
        assertThat(livroCadastrado.getId()).isEqualTo(1L);
        assertThat(livroCadastrado.getTitulo()).isEqualTo("O Senhor dos Anéis");
        assertThat(livroCadastrado.getAutor()).isEqualTo("J.R.R. Tolkien");
        assertThat(livroCadastrado.getAnoPublicacao()).isEqualTo(1954);
        assertThat(livroCadastrado.getCategoria()).isEqualTo(Categoria.FANTASIA);

        verify(livroRepository, times(1)).save(livroCadastrado);
    }

    @Test
    void naoPermiteCadastrarLivroJaExistente() {
        var livro = LivroHelper.preencheLivroDTO();

        when(livroRepository.existsByTituloAndAutor(livro.titulo(), livro.autor())).thenReturn(true);

        assertThatThrownBy(() -> livroService.salvarLivro(livro))
                .isInstanceOf(LivroJaCadastradoException.class)
                .hasMessage("O livro " + livro.titulo() + " do autor " + livro.autor() + " ja foi cadastrado.");

        verify(livroRepository, times(1)).existsByTituloAndAutor(livro.titulo(), livro.autor());
    }

    @Test
    void devePermitirAlterarLivro() {
        // Arrange
        var id = 1L;
        var livro = LivroHelper.cadastraLivro();
        livro.setId(id);

        var livroDTO = LivroHelper.preencheLivroDTO();

        // Mockando repositorio
        when(livroRepository.findById(id)).thenReturn(Optional.of(livro));

        when(livroRepository.save(any(Livro.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        var livroAlterado = livroService.alterarLivro(livroDTO, id);

        // Asserts
        assertThat(livroAlterado).isNotNull().isInstanceOf(Livro.class);
        assertThat(livroAlterado.getId()).isEqualTo(1L);
        assertThat(livroAlterado.getTitulo()).isEqualTo("O Senhor dos Anéis");
        assertThat(livroAlterado.getAutor()).isEqualTo("J.R.R. Tolkien");
        assertThat(livroAlterado.getAnoPublicacao()).isEqualTo(1954);
        assertThat(livroAlterado.getCategoria()).isEqualTo(Categoria.FANTASIA);

        verify(livroRepository, times(1)).findById(id);
        verify(livroRepository, times(1)).save(livroAlterado);
    }

    @Test
    void naoPermiteAlterarLivroParaJaExistente() {
        var livro = LivroHelper.cadastraLivro();
        livro.setId(1L);

        var livroDTO = new LivroDTO("O Hobbit", "J.R.R. Tolkien", 1937, Categoria.FANTASIA);

        when(livroRepository.findById(livro.getId())).thenReturn(Optional.of(livro));

        when(livroRepository.existsByTituloAndAutor(livro.getTitulo(), livro.getAutor())).thenReturn(true);

        assertThatThrownBy(() -> livroService.alterarLivro(livroDTO, livro.getId()))
                .isInstanceOf(LivroJaCadastradoException.class)
                .hasMessage("O livro " + livro.getTitulo() + " do autor " + livro.getAutor() + " ja foi cadastrado.");

        verify(livroRepository, times(1)).existsByTituloAndAutor(livro.getTitulo(), livro.getAutor());
    }

    @Test
    void devePermitirDeletarLivro() {
        // Arrange
        var id = 1L;
        var livro = LivroHelper.cadastraLivro();
        livro.setId(id);

        when(livroRepository.findById(id)).thenReturn(Optional.of(livro));

        // Mockando repositorio
        doNothing().when(livroRepository).deleteById(id);

        // Act
        livroService.deletarLivro(id);

        // Assert
        verify(livroRepository, times(1)).deleteById(id);
    }

    @Test
    void naoPermiteDeletarLivroInexistente() {
        var id = 999L;

        when(livroRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> livroService.deletarLivro(id))
                .isInstanceOf(LivroNaoEncontradoException.class)
                .hasMessage("Livro não encontrado.");
    }

    @Test
    void devePermitirSelecionarLivroPorId() {
        // Arrange
        var id = 1L;
        var livro = LivroHelper.cadastraLivro();
        livro.setId(id);

        // Mockando repositorio
        when(livroRepository.findById(any(Long.class))).thenReturn(Optional.of(livro));

        //Act
        var livroEncontrado = livroService.selecionarLivroPorId(id);

        // Asserts
        assertThat(livroEncontrado).isNotNull().isInstanceOf(Livro.class);
        assertThat(livroEncontrado).isEqualTo(livro);

        verify(livroRepository, times(1)).findById(id);
    }

    @Test
    void naoPermiteSelecionarLivroPorId() {
        var id = 999L;

        when(livroRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> livroService.selecionarLivroPorId(id))
                .isInstanceOf(LivroNaoEncontradoException.class)
                .hasMessage("Livro não encontrado.");
    }

    @Test
    void devePermitirSelecionarLivroPorTitulo() {
        // Arrange
        var id = 1L;
        var livro = LivroHelper.cadastraLivro();
        livro.setId(id);

        // Mockando repositorio
        when(livroRepository.selecionaLivroPorTitulo(any(String.class))).thenReturn(List.of(livro));

        // Act
        var livrosEncontrados = livroService.selecionarLivroPorTitulo(livro.getTitulo());

        // Assert
        assertThat(livrosEncontrados).isNotNull().isNotEmpty();
        assertThat(livrosEncontrados).hasSize(1);
        assertThat(livrosEncontrados.get(0)).isEqualTo(livro);

        verify(livroRepository, times(1)).selecionaLivroPorTitulo(livro.getTitulo());
    }

    @Test
    void naoPermiteSelecionarLivroPorTitulo() {
        var titulo = "A Menina Do Outro Lado";

        when(livroRepository.selecionaLivroPorTitulo(titulo)).thenReturn(List.of());

        assertThatThrownBy(() -> livroService.selecionarLivroPorTitulo(titulo))
                .isInstanceOf(LivroNaoEncontradoException.class)
                .hasMessage("Livro não encontrado.");
    }

    @Test
    void devePermitirSelecionarLivroPorAutor() {
        // Arrange
        var id = 1L;
        var livro = LivroHelper.cadastraLivro();
        livro.setId(id);

        // Mockando repositorio
        when(livroRepository.selecionaLivroPorAutor(any(String.class))).thenReturn(List.of(livro));

        // Act
        var livrosEncontrados = livroService.selecionarLivroPorAutor(livro.getAutor());

        // Assert
        assertThat(livrosEncontrados).isNotNull().isNotEmpty();
        assertThat(livrosEncontrados).hasSize(1);
        assertThat(livrosEncontrados.get(0)).isEqualTo(livro);

        verify(livroRepository, times(1)).selecionaLivroPorAutor(livro.getAutor());
    }

    @Test
    void naoPermiteSelecionarLivroPorAutor() {
        var autor = "Herbert Schildt";

        when(livroRepository.selecionaLivroPorAutor(autor)).thenReturn(List.of());

        assertThatThrownBy(() -> livroService.selecionarLivroPorAutor(autor))
                .isInstanceOf(LivroNaoEncontradoException.class)
                .hasMessage("Livro não encontrado.");
    }

    @Test
    void devePermitirSelecionarLivroPorCategoria() {
        // Arrange
        var id = 1L;
        var livro = LivroHelper.cadastraLivro();
        livro.setId(id);

        // Mockando repositorio
        when(livroRepository.selecionaLivroPorCategoria(any(String.class))).thenReturn(List.of(livro));

        // Act
        var livrosEncontrados = livroService.selecionarLivroPorCategoria(livro.getCategoria().getDescricao());

        // Assert
        assertThat(livrosEncontrados).isNotNull().isNotEmpty();
        assertThat(livrosEncontrados).hasSize(1);
        assertThat(livrosEncontrados.get(0)).isEqualTo(livro);

        verify(livroRepository, times(1)).selecionaLivroPorCategoria(livro.getCategoria().getDescricao());
    }

    @Test
    void naoPermiteSelecionarLivroPorCategoria() {
        var categoria = "SUSPENSE";

        when(livroRepository.selecionaLivroPorCategoria(categoria)).thenReturn(List.of());

        assertThatThrownBy(() -> livroService.selecionarLivroPorCategoria(categoria))
                .isInstanceOf(LivroNaoEncontradoException.class)
                .hasMessage("Livro não encontrado.");
    }

    @Test
    void devePermitirSelecionarTodosOsLivros() {
        // Arrange
        var id = 1L;
        var livro = LivroHelper.cadastraLivro();
        livro.setId(id);

        // Mockando repositorio
        when(livroRepository.findAll()).thenReturn(List.of(livro));

        // Act
        var livrosEncontrados = livroService.selecionarTodosOsLivros();

        // Assert
        assertThat(livrosEncontrados).isNotNull().isNotEmpty();
        assertThat(livrosEncontrados).hasSize(1);
        assertThat(livrosEncontrados.get(0)).isEqualTo(livro);

        verify(livroRepository, times(1)).findAll();
    }

    @Test
    void naoPermiteSelecionarTodosOsLivros() {
        when(livroRepository.findAll()).thenReturn(List.of());

        assertThatThrownBy(() -> livroService.selecionarTodosOsLivros())
                .isInstanceOf(LivroNaoEncontradoException.class)
                .hasMessage("Livros não encontrados.");
    }

    @Test
    void existsByTituloAndAutorTest() {
        // Arrange
        var livroDTO = LivroHelper.preencheLivroDTO();

        // Mockando repositorio
        when(livroRepository.save(any(Livro.class))).thenAnswer(i -> {
            Livro livroSalvo = i.getArgument(0);
            livroSalvo.setId(1L);
            return livroSalvo;
        });

        var livro = livroService.salvarLivro(livroDTO);

        when(livroRepository.existsByTituloAndAutor(livro.getTitulo(), livro.getTitulo())).thenReturn(true);

        boolean existeLivro = livroRepository.existsByTituloAndAutor(livro.getTitulo(), livro.getTitulo());

        assertThat(existeLivro).isTrue();
    }

    @Test
    void confereExistenciaLivroIgualTrue() {
        var livro = LivroHelper.cadastraLivro();
        livro.setId(1L);

        when(livroRepository.findById(livro.getId())).thenReturn(Optional.of(livro));

        boolean resultado = livroService.confereExistenciaLivro(livro.getId());

        assertThat(resultado).isTrue();
    }

    @Test
    void confereExistenciaLivroIgualFalse() {
        var id = 2L;

        when(livroRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> livroService.confereExistenciaLivro(id))
                .isInstanceOf(LivroNaoEncontradoException.class)
                .hasMessage("Livro não encontrado.");
    }
}