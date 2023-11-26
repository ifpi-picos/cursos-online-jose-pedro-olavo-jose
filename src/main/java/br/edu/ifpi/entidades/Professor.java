package br.edu.ifpi.entidades;

import java.util.List;

public class Professor {

    private Long id;
    private String nome;
    private String email;
    private List<Curso> cursosMinistrados;

    public Professor(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    // Outros métodos e getters/setters necessários
}
