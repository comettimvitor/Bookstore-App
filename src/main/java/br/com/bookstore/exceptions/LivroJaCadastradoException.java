package br.com.bookstore.exceptions;

public class LivroJaCadastradoException extends RuntimeException {
    public LivroJaCadastradoException(String message) {
        super(message);
    }
}
