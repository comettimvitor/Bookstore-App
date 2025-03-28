package br.com.bookstore.controllers.handler;

import br.com.bookstore.exceptions.LivroJaCadastradoException;
import br.com.bookstore.exceptions.LivroNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(LivroJaCadastradoException.class)
    public ResponseEntity<Map<String, String>> handleLivroJaCadastradoException(LivroJaCadastradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "400");
        error.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(LivroNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleLivroNaoEncontradoException(LivroNaoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "404");
        error.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
