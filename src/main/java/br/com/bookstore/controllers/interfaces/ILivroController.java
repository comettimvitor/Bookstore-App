package br.com.bookstore.controllers.interfaces;

import br.com.bookstore.dtos.LivroDTO;
import br.com.bookstore.entities.Livro;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Livraria API", description = "API para cadastro de Livros.")
public interface ILivroController {

    @Operation(
            description = "Cadastro de um novo livro.",
            summary = "Cadastro de um novo livro.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Objeto com dados para alteração de cadastro de livro.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{ \"titulo\": \"O Senhor dos Anéis\", " +
                                                    "\"autor\": \"J.R.R. Tolkien\", " +
                                                    "\"anoPublicacao\": 1954, " +
                                                    "\"categoria\": \"FANTASIA\" }"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": \"1\", " +
                                                            "\"titulo\": \"O Senhor dos Anéis\", " +
                                                            "\"autor\": \"J.R.R. Tolkien\", " +
                                                            "\"anoPublicacao\": 1954, " +
                                                            "\"categoria\": \"FANTASIA\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "Erro de cadastro",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"error\": \"400\", " +
                                                            "\"message\": \"O Livro ja foi cadastrado\" }"
                                            )
                                    }
                            )
                    )
            }
    )
    @PostMapping("/cadastrar")
    ResponseEntity<Livro> salvarLivro(@RequestBody LivroDTO livroDTO);

    @Operation(
            description = "Alteração de cadastro de livro.",
            summary = "Alteração de cadastro de livro.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID do livro a ser alterado",
                            required = true,
                            schema = @Schema(type = "Long", example = "1"),
                            in = ParameterIn.PATH
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Objeto com dados para alteração de cadastro de livro.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{ \"titulo\": \"O Senhor dos Anéis\", " +
                                                    "\"autor\": \"J.R.R. Tolkien\", " +
                                                    "\"anoPublicacao\": 1954, " +
                                                    "\"categoria\": \"FANTASIA\" }"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": \"1\", " +
                                                            "\"titulo\": \"O Senhor dos Anéis\", " +
                                                            "\"autor\": \"J.R.R. Tolkien\", " +
                                                            "\"anoPublicacao\": 1954, " +
                                                            "\"categoria\": \"FANTASIA\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de cadastro",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"error\": \"400\", " +
                                                            "\"message\": \"O Livro ja foi cadastrado\" }"
                                            )
                                    }
                            )
                    )
            }
    )
    @PutMapping("/alterar/{id}")
    ResponseEntity<Livro> alterarLivro(@RequestBody LivroDTO livroDTO, @PathVariable("id") Long id);

    @Operation(
            description = "Deleta cadastro de um livro.",
            summary = "Deleta cadastro de um livro.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "1"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"error\": \"404\", " +
                                                            "\"message\": \"Livro não encontrado.\" }"
                                            )
                                    }
                            )
                    )

            }
    )
    @DeleteMapping("/deletar/{id}")
    ResponseEntity<Void> deletarLivro(@PathVariable("id") Long id);

    @Operation(
            description = "Busca um livro.",
            summary = "Busca cadastro de um livro de acordo com ID informado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": \"1\", " +
                                                            "\"titulo\": \"O Senhor dos Anéis\", " +
                                                            "\"autor\": \"J.R.R. Tolkien\", " +
                                                            "\"anoPublicacao\": 1954, " +
                                                            "\"categoria\": \"FANTASIA\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"error\": \"404\", " +
                                                            "\"message\": \"Livro não encontrado.\" }"
                                            )
                                    }
                            )
                    )
            }
    )
    @GetMapping("/buscar/{id}")
    ResponseEntity<Livro> selecionarLivroPorId(@PathVariable("id") Long id);

    @Operation(
            description = "Busca livros por título.",
            summary = "Busca cadastro de livros de acordo com o título informado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": \"1\", " +
                                                            "\"titulo\": \"O Senhor dos Anéis\", " +
                                                            "\"autor\": \"J.R.R. Tolkien\", " +
                                                            "\"anoPublicacao\": 1954, " +
                                                            "\"categoria\": \"FANTASIA\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"error\": \"404\", " +
                                                            "\"message\": \"Livro não encontrado.\" }"
                                            )
                                    }
                            )
                    )
            }
    )
    @GetMapping("/buscar/titulo")
    ResponseEntity<List<Livro>> selecionarLivroPorTitulo(@RequestParam String titulo);

    @Operation(
            description = "Busca livros por autor.",
            summary = "Busca cadastro de livros de acordo com autor informado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": \"1\", " +
                                                            "\"titulo\": \"O Senhor dos Anéis\", " +
                                                            "\"autor\": \"J.R.R. Tolkien\", " +
                                                            "\"anoPublicacao\": 1954, " +
                                                            "\"categoria\": \"FANTASIA\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"error\": \"404\", " +
                                                            "\"message\": \"Livro não encontrado.\" }"
                                            )
                                    }
                            )
                    )
            }
    )
    @GetMapping("/buscar/autor")
    ResponseEntity<List<Livro>> selecionarLivroPorAutor(@RequestParam String autor);

    @Operation(
            description = "Busca livros por categoria.",
            summary = "Busca cadastro de livroa de acordo com categoria informada.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": \"1\", " +
                                                            "\"titulo\": \"O Senhor dos Anéis\", " +
                                                            "\"autor\": \"J.R.R. Tolkien\", " +
                                                            "\"anoPublicacao\": 1954, " +
                                                            "\"categoria\": \"FANTASIA\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"error\": \"404\", " +
                                                            "\"message\": \"Livro não encontrado.\" }"
                                            )
                                    }
                            )
                    )
            }
    )
    @GetMapping("/buscar/categoria")
    ResponseEntity<List<Livro>> selecionarLivroPorCategoria(@RequestParam String categoria);

    @Operation(
            description = "Busca todos os livros.",
            summary = "Busca cadastro de todos os livros.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": \"1\", " +
                                                            "\"titulo\": \"O Senhor dos Anéis\", " +
                                                            "\"autor\": \"J.R.R. Tolkien\", " +
                                                            "\"anoPublicacao\": 1954, " +
                                                            "\"categoria\": \"FANTASIA\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"error\": \"404\", " +
                                                            "\"message\": \"Livro não encontrado.\" }"
                                            )
                                    }
                            )
                    )
            }
    )
    @GetMapping("/buscar")
    ResponseEntity<List<Livro>> selecionarTodosOsLivros();
}
