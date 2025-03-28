package br.com.bookstore.dtos;

import br.com.bookstore.enums.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para manipulação da classe Livro.")
public record LivroDTO(
        @Schema(description = "Título do livro.")
        String titulo,

        @Schema(description = "Nome do Autor.")
        String autor,

        @Schema(description = "Ano em que foi publicado.")
        int anoPublicacao,

        @Schema(description = "Categoria do livro.", examples = "'FANTASIA', 'DRAMA', 'EDUCAÇÃO'")
        Categoria categoria
) {
}
