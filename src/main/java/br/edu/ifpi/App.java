package br.edu.ifpi;

import br.edu.ifpi.DAO.AlunoDAO;
import br.edu.ifpi.DAO.CursoDAO;
import br.edu.ifpi.DAO.ProfessorDAO;
import br.edu.ifpi.entidades.Curso;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Cadastrar Curso");
            System.out.println("2. Cadastrar Aluno");
            System.out.println("3. Cadastrar Professor");
            System.out.println("4. Inseriar Professor em Cursos");
            System.out.println("5. Matricular Aluno em Curso");
            System.out.println("6. Desmatricular Aluno de Curso");
            System.out.println("7. Exibir Cursos Concluídos pelo Aluno");
            System.out.println("8. Exibir Cursos em que o Aluno está Matriculado");
            System.out.println("9. Exibir Porcentagem de Aproveitamento nos Cursos");
            System.out.println("10. Atribuir Notas a Alunos em um Curso");
            System.out.println("0. Sair");

            int opcao;

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scanner.nextLine(); 
                continue; 
            }

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
                
                ProfessorDAO.associarProfessorACurso();
                break;
            

                case 5:
                    try (Connection connection = AlunoDAO.getConnection()) {
                        AlunoDAO.matricularAlunoNoCurso(connection);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 6:
                AlunoDAO.desmatricularAlunoDeCurso();
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

                case 10:
                    System.out.println("Informe o ID do curso:");
                    long idCursoNotas = scanner.nextLong();
                    try (Connection connection = AlunoDAO.getConnection()) {
                        AlunoDAO.atribuirNotas(connection, idCursoNotas);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
