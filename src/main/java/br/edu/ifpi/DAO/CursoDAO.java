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
import java.util.Scanner;

public class CursoDAO {
    // Scanner para entrada de dados pelo console
    private static Scanner scanner = new Scanner(System.in);

    // Método para obter uma conexão com o banco de dados PostgreSQL
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/trabalho";
        String user = "postgres";
        String password = "postgres";
        return DriverManager.getConnection(url, user, password);
    }

    // Método para cadastrar um curso no banco de dados
    public static void cadastrarCurso() {
        try (Connection connection = getConnection(); Scanner scanner = new Scanner(System.in)) {
            // Solicita informações do curso ao usuário
            System.out.println("Informe o nome do curso:");
            String nome = scanner.next();
            System.out.println("Informe o status do curso (ATIVO ou INATIVO):");
            String status = scanner.next().toUpperCase();
            System.out.println("Informe a carga horária do curso:");
            int cargaHoraria = scanner.nextInt();

            // Converte o status para o enum correspondente
            StatusCurso statusCurso = StatusCurso.valueOf(status);

            // Cria um objeto Curso com as informações fornecidas
            Curso curso = new Curso(null, nome, statusCurso, cargaHoraria);

            // Chama o método para inserir o curso no banco de dados
            inserirCurso(connection, curso);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método privado para inserir um curso no banco de dados
    private static void inserirCurso(Connection connection, Curso curso) {
        try {
            String sql = "INSERT INTO cursos (nome, status, carga_horaria) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Define os parâmetros do PreparedStatement com as informações do curso
                preparedStatement.setString(1, curso.getNome());
                preparedStatement.setString(2, curso.getStatus().toString());
                preparedStatement.setInt(3, curso.getCargaHoraria());

                // Executa a inserção no banco de dados
                preparedStatement.executeUpdate();
                System.out.println("Curso cadastrado com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obter as notas dos alunos em um curso
    public static List<Double> getNotasDosAlunosNoCurso(long idCurso) {
        List<Double> notas = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT nota FROM notas WHERE id_curso = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Define o parâmetro do PreparedStatement com o ID do curso
                preparedStatement.setLong(1, idCurso);

                // Executa a consulta no banco de dados
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Itera sobre os resultados e adiciona as notas à lista
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
}
