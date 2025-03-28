package br.com.bookstore.enums;

public enum Categoria {
    FANTASIA("Fantasia"),
    ROMANCE("Romance"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    MISTERIO("Mistério"),
    AVENTURA("Aventura"),
    HISTORICO("Histórico"),
    DRAMA("Drama"),
    AUTOAJUDA("Autoajuda"),
    INFANTIL("Infantil"),
    EDUCACAO("Educação");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
