package br.edu.ifpi.model;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nome;
    private StatusCurso status;
    private int cargaHoraria;
    private List<Professor> professores;
    private List<Aluno> alunos;

    public Curso(String nome, StatusCurso status, int cargaHoraria) {
        this.nome = nome;
        this.status = status;
        this.cargaHoraria = cargaHoraria;
        this.professores = new ArrayList<>();
        this.alunos = new ArrayList<>();
    }

    // Outros métodos e getters/setters
}
