package br.edu.ifpi.model;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private String nome;
    private int id;
    private String email;
    private List<Curso> cursosMatriculados;

    public Aluno(String nome, int id, String email) {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.cursosMatriculados = new ArrayList<>();
    }
}