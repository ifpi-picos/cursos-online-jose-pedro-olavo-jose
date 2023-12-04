package br.edu.ifpi.entidades;

import java.util.List;

public class Professor {

    // Atributos da classe Professor
    private Long id;
    private String nome;
    private String email;
    private List<Curso> cursosMinistrados;

    // Construtor da classe Professor
    public Professor(Long id, String nome, String email, List<Curso> cursosMinistrados) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cursosMinistrados = cursosMinistrados;
    }

    // Getter para obter o ID do professor
    public Long getId() {
        return id;
    }

    // Getter para obter o nome do professor
    public String getNome() {
        return nome;
    }

    // Getter para obter o e-mail do professor
    public String getEmail() {
        return email;
    }

    // Getter para obter a lista de cursos ministrados pelo professor
    public List<Curso> getCursosMinistrados() {
        return cursosMinistrados;
    }

    // Método para adicionar um curso à lista de cursos ministrados pelo professor
    public void adicionarCursoMinistrado(Curso curso) {
        this.cursosMinistrados.add(curso);
    }
}
