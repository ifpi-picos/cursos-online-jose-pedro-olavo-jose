package br.edu.ifpi.entidades;

import java.util.List;
import java.util.Map;

public class Aluno {
    
    // Atributos da classe Aluno
    private Long id;
    private String nome;
    private String email;
    private List<Curso> cursos;
    private Map<Long, Double> notasPorCurso;

    // Construtor da classe Aluno
    public Aluno(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    // Getter para obter o ID do aluno
    public Long getId() {
        return id;
    }

    // Getter para obter o nome do aluno
    public String getNome() {
        return nome;
    }

    // Getter para obter o e-mail do aluno
    public String getEmail() {
        return email;
    }

    // Getter para obter a lista de cursos matriculados pelo aluno
    public List<Curso> getCursos() {
        return cursos;
    }

    // Setter para definir a lista de cursos matriculados pelo aluno
    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    // Getter para obter o mapa de notas por curso do aluno
    public Map<Long, Double> getNotasPorCurso() {
        return notasPorCurso;
    }

    // Setter para definir o mapa de notas por curso do aluno
    public void setNotasPorCurso(Map<Long, Double> notasPorCurso) {
        this.notasPorCurso = notasPorCurso;
    }

    // Método toString para representação em string da classe Aluno
    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", notasPorCurso=" + notasPorCurso +
                '}';
    }
}
