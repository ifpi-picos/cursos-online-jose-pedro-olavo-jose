package br.edu.ifpi;

import br.edu.ifpi.DAO.AlunoDAO;
import br.edu.ifpi.DAO.CursoDAO;
import br.edu.ifpi.DAO.ProfessorDAO;
import br.edu.ifpi.entidades.Curso;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Cadastrar Curso");
            System.out.println("2. Cadastrar Aluno");
            System.out.println("3. Cadastrar Professor");
            System.out.println("4. Cadastrar Curso e Associar a Professor");
            System.out.println("5. Matricular Aluno em Curso");
            System.out.println("6. Desmatricular Aluno de Curso");
            System.out.println("7. Exibir Cursos Concluídos pelo Aluno");
            System.out.println("8. Exibir Cursos em que o Aluno está Matriculado");
            System.out.println("9. Exibir Porcentagem de Aproveitamento nos Cursos");
            System.out.println("0. Sair");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    CursoDAO.cadastrarCurso();
                    break;

                case 2:
                    AlunoDAO.cadastrarAluno();
                    break;

                case 3:
                    ProfessorDAO.cadastrarProfessor();
                    break;

                case 4:
                    ProfessorDAO.cadastrarCurso(scanner.nextLong());
                    break;

                case 5:
                    AlunoDAO.matricularAlunoEmCurso(scanner.nextLong()); 
                    break;

                case 6:
                    AlunoDAO.desmatricularAlunoDeCurso(scanner.nextLong()); 
                    break;

                case 7:
                    System.out.println("Informe o ID do aluno:");
                    long idAlunoConcluido = scanner.nextLong();
                    List<Curso> cursosConcluidos = AlunoDAO.getCursosConcluidos(idAlunoConcluido);
                    System.out.println("Cursos concluídos pelo aluno:");
                    for (Curso curso : cursosConcluidos) {
                        System.out.println(curso.getNome());
                    }
                    break;

                case 8:
                    System.out.println("Informe o ID do aluno:");
                    long idAlunoMatriculado = scanner.nextLong();
                    List<Curso> cursosMatriculados = AlunoDAO.getCursosMatriculados(idAlunoMatriculado);
                    System.out.println("Cursos em que o aluno está matriculado:");
                    for (Curso curso : cursosMatriculados) {
                        System.out.println(curso.getNome());
                    }
                    break;

                case 9:
                    System.out.println("Informe o ID do aluno:");
                    long idAlunoAproveitamento = scanner.nextLong();
                    double aproveitamento = AlunoDAO.getAproveitamento(idAlunoAproveitamento);
                    System.out.println("Porcentagem de aproveitamento nos cursos: " + aproveitamento + "%");
                    break;

                case 0:
                    System.out.println("Saindo do programa!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
