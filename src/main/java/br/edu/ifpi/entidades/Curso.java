package br.edu.ifpi.entidades;

import br.edu.ifpi.enums.StatusCurso;

import java.util.List;

public class Curso {

    // Atributos da classe Curso
    private Long id;
    private String nome;
    private StatusCurso status;
    private int cargaHoraria;
    private List<Professor> professores;
    private List<Aluno> alunos;
    private List<Double> notasDosAlunos;

    // Construtor da classe Curso
    public Curso(Long id, String nome, StatusCurso status, int cargaHoraria) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.cargaHoraria = cargaHoraria;
    }

    // Getter para obter o ID do curso
    public Long getId() {
        return id;
    }

    // Getter para obter o nome do curso
    public String getNome() {
        return nome;
    }

    // Getter para obter o status do curso
    public StatusCurso getStatus() {
        return status;
    }

    // Getter para obter a carga horária do curso
    public int getCargaHoraria() {
        return cargaHoraria;
    }

    // Getter para obter a lista de professores associados ao curso
    public List<Professor> getProfessores() {
        return professores;
    }

    // Setter para definir a lista de professores associados ao curso
    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    // Getter para obter a lista de alunos matriculados no curso
    public List<Aluno> getAlunos() {
        return alunos;
    }

    // Setter para definir a lista de alunos matriculados no curso
    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    // Getter para obter a lista de notas dos alunos no curso
    public List<Double> getNotasDosAlunos() {
        return notasDosAlunos;
    }

    // Setter para definir a lista de notas dos alunos no curso
    public void setNotasDosAlunos(List<Double> notasDosAlunos) {
        this.notasDosAlunos = notasDosAlunos;
    }

    // Método para calcular a média das notas dos alunos no curso
    public double calcularMedia() {
        // Verifica se a lista de notas é nula ou vazia
        if (notasDosAlunos == null || notasDosAlunos.isEmpty()) {
            return 0.0;
        }

        double soma = 0.0;
        // Calcula a soma das notas
        for (Double nota : notasDosAlunos) {
            soma += nota;
        }

        // Calcula a média das notas
        return soma / notasDosAlunos.size();
    }

    // Método para contar o número de alunos aprovados no curso
    public int contarAprovados() {
        // Verifica se a lista de notas é nula ou vazia
        if (notasDosAlunos == null || notasDosAlunos.isEmpty()) {
            return 0;
        }

        int aprovados = 0;
        // Conta o número de alunos com nota maior ou igual a 7
        for (Double nota : notasDosAlunos) {
            if (nota >= 7.0) {
                aprovados++;
            }
        }

        return aprovados;
    }

    // Método para calcular a porcentagem de alunos aprovados no curso
    public double calcularPorcentagemAprovados() {
        // Verifica se a lista de notas é nula ou vazia
        if (notasDosAlunos == null || notasDosAlunos.isEmpty()) {
            return 0.0;
        }

        int totalAlunos = notasDosAlunos.size();
        int aprovados = contarAprovados();

        // Calcula a porcentagem de alunos aprovados
        return ((double) aprovados / totalAlunos) * 100.0;
    }

    // Método toString para representação em string da classe Curso
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
