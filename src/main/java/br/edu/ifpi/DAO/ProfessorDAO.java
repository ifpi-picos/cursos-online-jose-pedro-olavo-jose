package br.edu.ifpi.DAO;

import br.edu.ifpi.entidades.Professor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfessorDAO {

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/massinha";
        String user = "postgres";
        String password = "postgres";

        return DriverManager.getConnection(url, user, password);
    }

    public static void inserirProfessor(Professor professor) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO professores (id, nome, email) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, professor.getId());
                preparedStatement.setString(2, professor.getNome());
                preparedStatement.setString(3, professor.getEmail());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
