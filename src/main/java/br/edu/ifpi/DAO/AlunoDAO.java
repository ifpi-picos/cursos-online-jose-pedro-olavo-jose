package br.edu.ifpi.DAO;

import br.edu.ifpi.entidades.Aluno;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AlunoDAO {

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/massinha";
        String user = "postgres";
        String password = "postgres";

        return DriverManager.getConnection(url, user, password);
    }

    public static void inserirAluno(Aluno aluno) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO alunos (id, nome, email) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, aluno.getId());
                preparedStatement.setString(2, aluno.getNome());
                preparedStatement.setString(3, aluno.getEmail());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserirAlunoDoUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o ID do aluno:");
        long id = scanner.nextLong();
        System.out.println("Informe o nome do aluno:");
        String nome = scanner.next();
        System.out.println("Informe o e-mail do aluno:");
        String email = scanner.next();

        Aluno aluno = new Aluno(id, nome, email);
        inserirAluno(aluno);
    }
}
