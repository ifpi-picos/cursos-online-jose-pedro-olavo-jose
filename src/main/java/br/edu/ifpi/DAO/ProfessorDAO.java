package br.edu.ifpi.DAO;

import br.edu.ifpi.entidades.Curso;
import br.edu.ifpi.entidades.Professor;
import br.edu.ifpi.enums.StatusCurso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ProfessorDAO {

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/trabalho";
        String user = "postgres";
        String password = "postgres";
        return DriverManager.getConnection(url, user, password);
    }

    public static void cadastrarProfessor() {
        try (Connection connection = getConnection()) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Informe o ID do professor:");
            long id = scanner.nextLong();
            System.out.println("Informe o nome do professor:");
            String nome = scanner.next();
            System.out.println("Informe o e-mail do professor:");
            String email = scanner.next();

            Professor professor = new Professor(id, nome, email, null);
            inserirProfessor(professor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void inserirProfessor(Professor professor) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO professor (id, nome, email) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, professor.getId());
                preparedStatement.setString(2, professor.getNome());
                preparedStatement.setString(3, professor.getEmail());

                preparedStatement.executeUpdate();
                System.out.println("Professor cadastrado com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cadastrarCurso(long idProfessor) {
        try (Connection connection = getConnection()) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Informe o ID do curso:");
            long idCurso = scanner.nextLong();
            System.out.println("Informe o nome do curso:");
            String nomeCurso = scanner.next();
            System.out.println("Informe o status do curso (ABERTO/FECHADO):");
            String statusCurso = scanner.next();
            System.out.println("Informe a carga horária do curso:");
            int cargaHoraria = scanner.nextInt();

            StatusCurso status = StatusCurso.ABERTO;

            try {
                status = StatusCurso.valueOf(statusCurso.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Status do curso inválido. Usando valor padrão: " + status);
            }

            Curso curso = new Curso(idCurso, nomeCurso, status, cargaHoraria);
            inserirCurso(curso);
            associarProfessorACurso(idProfessor, idCurso);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void inserirCurso(Curso curso) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO cursos (id, nome, status, carga_horaria) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, curso.getId());
                preparedStatement.setString(2, curso.getNome());
                preparedStatement.setString(3, curso.getStatus().toString());
                preparedStatement.setInt(4, curso.getCargaHoraria());

                preparedStatement.executeUpdate();
                System.out.println("Curso cadastrado com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void associarProfessorACurso(long idProfessor, long idCurso) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO professores_cursos (id_professor, id_curso) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idProfessor);
                preparedStatement.setLong(2, idCurso);

                preparedStatement.executeUpdate();
                System.out.println("Professor associado ao curso com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
