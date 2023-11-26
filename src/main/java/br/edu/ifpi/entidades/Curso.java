package br.edu.ifpi.entidades;

import java.util.List;

import br.edu.ifpi.enums.StatusCurso;

public class Curso {

    private Long id;
    private String nome;
    private StatusCurso status;
    private int cargaHoraria;
    private List<Professor> professores;
    private List<Aluno> alunos;

    public Curso(Long id, String nome, StatusCurso status, int cargaHoraria) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.cargaHoraria = cargaHoraria;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public StatusCurso getStatus() {
        return status;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    // Outros métodos e getters/setters necessários
}
