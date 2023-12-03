package br.edu.ifpi.entidades;

import br.edu.ifpi.enums.StatusCurso;

import java.util.List;

public class Curso {

    private Long id;
    private String nome;
    private StatusCurso status;
    private int cargaHoraria;
    private List<Professor> professores;
    private List<Aluno> alunos;
    private List<Double> notasDosAlunos;

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

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<Double> getNotasDosAlunos() {
        return notasDosAlunos;
    }

    public void setNotasDosAlunos(List<Double> notasDosAlunos) {
        this.notasDosAlunos = notasDosAlunos;
    }

    public double calcularMedia() {
        // Lógica para calcular a média das notas
        if (notasDosAlunos == null || notasDosAlunos.isEmpty()) {
            return 0.0;
        }

        double soma = 0.0;
        for (Double nota : notasDosAlunos) {
            soma += nota;
        }

        return soma / notasDosAlunos.size();
    }

    public int contarAprovados() {
        // Lógica para contar alunos aprovados
        if (notasDosAlunos == null || notasDosAlunos.isEmpty()) {
            return 0;
        }

        int aprovados = 0;
        for (Double nota : notasDosAlunos) {
            if (nota >= 7.0) {
                aprovados++;
            }
        }

        return aprovados;
    }

    public double calcularPorcentagemAprovados() {
       
        if (notasDosAlunos == null || notasDosAlunos.isEmpty()) {
            return 0.0;
        }

        int totalAlunos = notasDosAlunos.size();
        int aprovados = contarAprovados();

        return ((double) aprovados / totalAlunos) * 100.0;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", status=" + status +
                ", cargaHoraria=" + cargaHoraria +
                ", professores=" + professores +
                ", alunos=" + alunos +
                ", notasDosAlunos=" + notasDosAlunos +
                '}';
    }
}
