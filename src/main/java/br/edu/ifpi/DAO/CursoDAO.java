package br.edu.ifpi.DAO;

import br.edu.ifpi.enums.StatusCurso;
import br.edu.ifpi.entidades.Curso;
import br.edu.ifpi.entidades.Professor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CursoDAO {

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/massinha";
        String user = "postgres";
        String password = "postgres";

        return DriverManager.getConnection(url, user, password);
    }

    public static void inserirCurso(Curso curso) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO cursos (id, nome, status, carga_horaria) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, curso.getId());
                preparedStatement.setString(2, curso.getNome());
                preparedStatement.setString(3, curso.getStatus().toString());
                preparedStatement.setInt(4, curso.getCargaHoraria());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
