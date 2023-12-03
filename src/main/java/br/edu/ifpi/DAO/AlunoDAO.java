package br.edu.ifpi.DAO;

import br.edu.ifpi.entidades.Aluno;
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

public class AlunoDAO {
    private static Scanner scanner = new Scanner(System.in);

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/trabalho";
        String user = "postgres";
        String password = "postgres";
        return DriverManager.getConnection(url, user, password);
    }

    public static void cadastrarAluno() {
        try (Connection connection = getConnection(); Scanner scanner = new Scanner(System.in)) {
            System.out.println("Informe o ID do aluno:");
            long id = scanner.nextLong();
            System.out.println("Informe o nome do aluno:");
            String nome = scanner.next();
            System.out.println("Informe o e-mail do aluno:");
            String email = scanner.next();

            Aluno aluno = new Aluno(id, nome, email);
            inserirAluno(connection, aluno);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void matricularAlunoEmCurso(long idAluno) {
        try (Connection connection = getConnection()) {
            System.out.println("Informe o ID do curso:");
            long idCurso = scanner.nextLong();

            matricularAlunoNoCurso(connection, idAluno, idCurso);
            atribuirNotas(idCurso);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void desmatricularAlunoDeCurso(long idAluno) {
        try (Connection connection = getConnection()) {
            System.out.println("Informe o ID do curso:");
            long idCurso = scanner.nextLong();

            desmatricularAlunoDoCurso(connection, idAluno, idCurso);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Curso> getCursosConcluidos(long idAluno) {
        List<Curso> cursosConcluidos = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT c.id, c.nome, c.status, c.carga_horaria FROM cursos c " +
                         "JOIN matriculas m ON c.id = m.id_curso " +
                         "WHERE m.id_aluno = ? AND EXISTS(SELECT 1 FROM notas n " +
                         "WHERE n.id_aluno = m.id_aluno AND n.id_curso = m.id_curso AND n.nota >= 7)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        long idCurso = resultSet.getLong("id");
                        String nome = resultSet.getString("nome");
                        String status = resultSet.getString("status");
                        int cargaHoraria = resultSet.getInt("carga_horaria");

                        Curso curso = new Curso(idCurso, nome, StatusCurso.valueOf(status.toUpperCase()), cargaHoraria);
                        cursosConcluidos.add(curso);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursosConcluidos;
    }

    public static List<Curso> getCursosMatriculados(long idAluno) {
        List<Curso> cursosMatriculados = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT c.id, c.nome, c.status, c.carga_horaria FROM cursos c " +
                         "JOIN matriculas m ON c.id = m.id_curso " +
                         "WHERE m.id_aluno = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        long idCurso = resultSet.getLong("id");
                        String nome = resultSet.getString("nome");
                        String status = resultSet.getString("status");
                        int cargaHoraria = resultSet.getInt("carga_horaria");

                        Curso curso = new Curso(idCurso, nome, StatusCurso.valueOf(status.toUpperCase()), cargaHoraria);
                        cursosMatriculados.add(curso);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursosMatriculados;
    }

    public static double getAproveitamento(long idAluno) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT COUNT(*) FROM matriculas m " +
                         "JOIN notas n ON m.id_aluno = n.id_aluno AND m.id_curso = n.id_curso " +
                         "WHERE m.id_aluno = ? AND n.nota >= 7";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int cursosAprovados = resultSet.getInt(1);
                        int totalCursos = getTotalCursosMatriculados(connection, idAluno);

                        if (totalCursos > 0) {
                            return ((double) cursosAprovados / totalCursos) * 100;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    private static void inserirAluno(Connection connection, Aluno aluno) {
        try {
            String sql = "INSERT INTO alunos (id, nome, email) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, aluno.getId());
                preparedStatement.setString(2, aluno.getNome());
                preparedStatement.setString(3, aluno.getEmail());

                preparedStatement.executeUpdate();
                System.out.println("Aluno cadastrado com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void matricularAlunoNoCurso(Connection connection, long idAluno, long idCurso) {
        try {
            String sql = "INSERT INTO matriculas (id_aluno, id_curso) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);
                preparedStatement.setLong(2, idCurso);

                preparedStatement.executeUpdate();
                System.out.println("Aluno matriculado com sucesso no curso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void desmatricularAlunoDoCurso(Connection connection, long idAluno, long idCurso) {
        try {
            String sql = "DELETE FROM matriculas WHERE id_aluno = ? AND id_curso = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);
                preparedStatement.setLong(2, idCurso);

                preparedStatement.executeUpdate();
                System.out.println("Aluno desmatriculado com sucesso do curso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void atribuirNotas(long idCurso) {
        try (Connection connection = getConnection()) {
            List<Aluno> alunos = getCursosAlunos(connection, idCurso);

            for (Aluno aluno : alunos) {
                System.out.println("Informe a nota para o aluno " + aluno.getNome() + ":");
                double nota = scanner.nextDouble();

                atualizarNota(connection, aluno.getId(), idCurso, nota);
            }

            System.out.println("Notas atribuídas com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Aluno> getCursosAlunos(Connection connection, long idCurso) {
        List<Aluno> alunos = new ArrayList<>();

        try {
            String sql = "SELECT a.id, a.nome, a.email FROM alunos a " +
                         "JOIN matriculas m ON a.id = m.id_aluno " +
                         "WHERE m.id_curso = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idCurso);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        long idAluno = resultSet.getLong("id");
                        String nome = resultSet.getString("nome");
                        String email = resultSet.getString("email");

                        Aluno aluno = new Aluno(idAluno, nome, email);
                        alunos.add(aluno);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunos;
    }

    private static void atualizarNota(Connection connection, long idAluno, long idCurso, double nota) {
        try {
            String sql = "INSERT INTO notas (id_aluno, id_curso, nota) VALUES (?, ?, ?) " +
                         "ON CONFLICT (id_aluno, id_curso) DO UPDATE SET nota = EXCLUDED.nota";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);
                preparedStatement.setLong(2, idCurso);
                preparedStatement.setDouble(3, nota);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getTotalCursosMatriculados(Connection connection, long idAluno) {
        try {
            String sql = "SELECT COUNT(*) FROM matriculas WHERE id_aluno = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
