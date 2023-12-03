package br.edu.ifpi.entidades;

import java.util.List;

public class Professor {

    private Long id;
    private String nome;
    private String email;
    private List<Curso> cursosMinistrados;

    public Professor(Long id, String nome, String email, List<Curso> cursosMinistrados) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cursosMinistrados = cursosMinistrados;
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

    public List<Curso> getCursosMinistrados() {
        return cursosMinistrados;
    }

    public void adicionarCursoMinistrado(Curso curso) {
        this.cursosMinistrados.add(curso);
    }
}
