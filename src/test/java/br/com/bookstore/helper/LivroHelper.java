package br.com.bookstore.helper;

import br.com.bookstore.dtos.LivroDTO;
import br.com.bookstore.entities.Livro;
import br.com.bookstore.enums.Categoria;

public abstract class LivroHelper {
    public static Livro cadastraLivro() {
        Livro livro = new Livro();
        livro.setTitulo("O Hobbit");
        livro.setAutor("J.R.R. Tolkien");
        livro.setAnoPublicacao(1937);
        livro.setCategoria(Categoria.FANTASIA);

        return livro;
    }

    public static LivroDTO preencheLivroDTO() {
        LivroDTO livroDTO = new LivroDTO("O Senhor dos Anéis", "J.R.R. Tolkien", 1954, Categoria.FANTASIA);
        return livroDTO;
    }
}
