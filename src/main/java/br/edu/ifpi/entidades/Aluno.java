package br.edu.ifpi.entidades;

import java.util.List;
import java.util.Map;

public class Aluno {
    private Long id;
    private String nome;
    private String email;
    private List<Curso> cursos;
    private Map<Long, Double> notasPorCurso;

    public Aluno(Long id, String nome, String email) {
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

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public Map<Long, Double> getNotasPorCurso() {
        return notasPorCurso;
    }

    public void setNotasPorCurso(Map<Long, Double> notasPorCurso) {
        this.notasPorCurso = notasPorCurso;
    }

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
