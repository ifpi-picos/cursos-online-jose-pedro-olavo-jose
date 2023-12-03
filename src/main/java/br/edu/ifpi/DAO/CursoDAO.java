package br.edu.ifpi.DAO;

import br.edu.ifpi.entidades.Curso;
import br.edu.ifpi.enums.StatusCurso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/trabalho";
        String user = "postgres";
        String password = "postgres";
        return DriverManager.getConnection(url, user, password);
    }

    public static void cadastrarCurso() {
        try (Connection connection = getConnection()) {
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Double> getNotasDosAlunosNoCurso(long idCurso) {
        List<Double> notas = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT nota FROM notas WHERE id_curso = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idCurso);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        double nota = resultSet.getDouble("nota");
                        notas.add(nota);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notas;
    }

    public static double calcularMedia(long idCurso) {
        List<Double> notas = getNotasDosAlunosNoCurso(idCurso);

        if (notas.isEmpty()) {
            return 0.0; 
        }

        double somaNotas = 0;
        for (double nota : notas) {
            somaNotas += nota;
        }

        return somaNotas / notas.size();
    }

    public static int contarAprovados(long idCurso) {
        List<Double> notas = getNotasDosAlunosNoCurso(idCurso);
        int aprovados = 0;

        for (double nota : notas) {
            if (nota >= 7.0) {
                aprovados++;
            }
        }

        return aprovados;
    }

    public static double calcularPorcentagemAprovados(long idCurso) {
        List<Double> notas = getNotasDosAlunosNoCurso(idCurso);

        if (notas.isEmpty()) {
            return 0.0; 
        }

        int aprovados = contarAprovados(idCurso);
        return (double) aprovados / notas.size() * 100;
    }
}
