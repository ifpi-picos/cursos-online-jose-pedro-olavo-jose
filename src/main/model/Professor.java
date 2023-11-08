package br.edu.ifpi.model;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    private String nome;
    private int id;
    private String email;
    private List<Curso> cursosMinistrados;

    public Professor(String nome, int id, String email) {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.cursosMinistrados = new ArrayList<>();
    }
}
