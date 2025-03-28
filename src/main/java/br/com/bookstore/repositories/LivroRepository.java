package br.com.bookstore.repositories;

import br.com.bookstore.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query(value = "SELECT id, titulo, autor, ano_publicacao, categoria FROM livro WHERE titulo ILIKE CONCAT('%', :titulo, '%')", nativeQuery = true)
    List<Livro> selecionaLivroPorTitulo(@Param("titulo") String titulo);

    @Query(value = "SELECT id, titulo, autor, ano_publicacao, categoria FROM livro WHERE autor ILIKE CONCAT('%', :autor, '%')", nativeQuery = true)
    List<Livro> selecionaLivroPorAutor(@Param("autor") String autor);

    @Query(value = "SELECT id, titulo, autor, ano_publicacao, categoria FROM livro WHERE categoria ILIKE :categoria", nativeQuery = true)
    List<Livro> selecionaLivroPorCategoria(@Param("categoria") String categoria);

//    Metodo do JPA que verifica existencia de registro
    boolean existsByTituloAndAutor(String titulo, String autor);
}
