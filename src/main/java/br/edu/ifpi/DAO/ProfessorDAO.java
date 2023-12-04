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

    // Método para obter uma conexão com o banco de dados PostgreSQL
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/trabalho";
        String user = "postgres";
        String password = "postgres";
        return DriverManager.getConnection(url, user, password);
    }

    // Método para cadastrar um professor no banco de dados
    public static void cadastrarProfessor() {
        try (Connection connection = getConnection()) {
            Scanner scanner = new Scanner(System.in);

            // Solicita informações do professor ao usuário
            System.out.println("Informe o ID do professor:");
            long id = scanner.nextLong();
            System.out.println("Informe o nome do professor:");
            String nome = scanner.next();
            System.out.println("Informe o e-mail do professor:");
            String email = scanner.next();

            // Cria um objeto Professor com as informações fornecidas
            Professor professor = new Professor(id, nome, email, null);

            // Chama o método para inserir o professor no banco de dados
            inserirProfessor(professor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método privado para inserir um professor no banco de dados
    private static void inserirProfessor(Professor professor) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO professores (id, nome, email) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Define os parâmetros do PreparedStatement com as informações do professor
                preparedStatement.setLong(1, professor.getId());
                preparedStatement.setString(2, professor.getNome());
                preparedStatement.setString(3, professor.getEmail());

                // Executa a inserção no banco de dados
                preparedStatement.executeUpdate();
                System.out.println("Professor cadastrado com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para associar um professor a um curso no banco de dados
    public static void associarProfessorACurso() {
        try (Connection connection = getConnection()) {
            Scanner scanner = new Scanner(System.in);

            // Solicita IDs do professor e do curso ao usuário
            System.out.println("Informe o ID do professor:");
            long idProfessor = scanner.nextLong();
            System.out.println("Informe o ID do curso:");
            long idCurso = scanner.nextLong();

            // Chama o método privado para associar o professor ao curso
            associarProfessorACurso(idProfessor, idCurso);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método privado para associar um professor a um curso no banco de dados
    private static void associarProfessorACurso(long idProfessor, long idCurso) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO professores_cursos (id_professor, id_curso) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Define os parâmetros do PreparedStatement com os IDs do professor e do curso
                preparedStatement.setLong(1, idProfessor);
                preparedStatement.setLong(2, idCurso);

                // Executa a associação no banco de dados
                preparedStatement.executeUpdate();
                System.out.println("Professor associado ao curso com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
