package br.edu.ifpi.DAO;

// Classe responsável por interações com o banco de dados relacionadas a alunos

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
    // Scanner para leitura de entrada do usuário
    private static Scanner scanner = new Scanner(System.in);
    private static List<Curso> cursosConcluídos;

    // Método para obter conexão com o banco de dados PostgreSQL
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/trabalho";
        String user = "postgres";
        String password = "postgres";
        return DriverManager.getConnection(url, user, password);
    }

    // Método para cadastrar um novo aluno no banco de dados
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

    // Método para matricular um aluno em um curso
    public static void matricularAlunoNoCurso(Connection connection) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Informe o ID do aluno:");
            long idAluno = scanner.nextLong();
    
            System.out.println("Informe o ID do curso:");
            long idCurso = scanner.nextLong();
    
            // SQL para inserir a matrícula do aluno no curso
            String sql = "INSERT INTO alunos_cursos (id_aluno, id_curso) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);
                preparedStatement.setLong(2, idCurso);
    
                preparedStatement.executeUpdate();
                System.out.println("Aluno matriculado com sucesso no curso!");
            }
            // Chama o método para atribuir notas ao aluno no curso
            atribuirNotas(connection, idCurso);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para desmatricular um aluno de um curso
    public static void desmatricularAlunoDeCurso() {
        try (Connection connection = getConnection(); Scanner scanner = new Scanner(System.in)) {
            System.out.println("Informe o ID do aluno:");
            long idAluno = scanner.nextLong();
    
            System.out.println("Informe o ID do curso:");
            long idCurso = scanner.nextLong();
    
            // Chama o método para desmatricular o aluno do curso
            desmatricularAlunoDoCurso(connection, idAluno, idCurso);
            System.out.println("Aluno desmatriculado com sucesso do curso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obter a lista de cursos concluídos por um aluno
    public static List<Curso> getCursosConcluidos(long idAluno) {
        List<Curso> cursosConcluidos = new ArrayList<>();

        try (Connection connection = getConnection()) {
            // SQL para obter cursos concluídos por um aluno
            String sql = "SELECT c.id, c.nome, c.status, c.carga_horaria FROM cursos c " +
                         "JOIN alunos_cursos m ON c.id = m.id_curso " +
                         "WHERE m.id_aluno = ? AND EXISTS(SELECT 1 FROM notas n " +
                         "WHERE n.id_aluno = m.id_aluno AND n.id_curso = m.id_curso AND n.nota >= 7)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Itera sobre os resultados e cria objetos Curso para adicionar à lista
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

        return cursosConcluídos;
    }

    // Método para obter a lista de cursos matriculados por um aluno
    public static List<Curso> getCursosMatriculados(long idAluno) {
        List<Curso> cursosMatriculados = new ArrayList<>();

        try (Connection connection = getConnection()) {
            // SQL para obter cursos matriculados por um aluno
            String sql = "SELECT c.id, c.nome, c.status, c.carga_horaria FROM cursos c " +
                         "JOIN alunos_cursos m ON c.id = m.id_curso " +
                         "WHERE m.id_aluno = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Itera sobre os resultados e cria objetos Curso para adicionar à lista
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

    // Método para obter o aproveitamento de um aluno (percentual de cursos aprovados)
    public static double getAproveitamento(long idAluno) {
        try (Connection connection = getConnection()) {
            // SQL para contar o número de cursos aprovados por um aluno
            String sql = "SELECT COUNT(*) FROM alunos_cursos m " +
                         "JOIN notas n ON m.id_aluno = n.id_aluno AND m.id_curso = n.id_curso " +
                         "WHERE m.id_aluno = ? AND n.nota >= 7";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Calcula o percentual de cursos aprovados em relação ao total de cursos matriculados
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

    // Método privado para inserir um novo aluno no banco de dados
    private static void inserirAluno(Connection connection, Aluno aluno) {
        try {
            // SQL para inserir um novo aluno
            String sql = "INSERT INTO alunos (id, nome, email) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, aluno.getId());
                preparedStatement.setString(2, aluno.getNome());
                preparedStatement.setString(3, aluno.getEmail());

                // Executa a inserção no banco de dados
                preparedStatement.executeUpdate();
                System.out.println("Aluno cadastrado com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método privado para desmatricular um aluno de um curso
    private static void desmatricularAlunoDoCurso(Connection connection, long idAluno, long idCurso) {
        try {
            // SQL para excluir a matrícula do aluno no curso
            String sql = "DELETE FROM alunos_cursos WHERE id_aluno = ? AND id_curso = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, idAluno);
                preparedStatement.setLong(2, idCurso);

                // Executa a exclusão no banco de dados
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  public static void atribuirNotas(Connection connection, long idCurso) throws SQLException {
    // Obtém a lista de alunos matriculados no curso
    List<Aluno> alunos = getCursosAlunos(connection, idCurso);

    // Itera sobre os alunos para atribuir notas
    for (Aluno aluno : alunos) {
        // Solicita a nota ao usuário para cada aluno
        System.out.println("Informe a nota para o aluno " + aluno.getNome() + ":");
        double nota = scanner.nextDouble();

        // Chama o método para atualizar a nota do aluno no curso
        atualizarNota(connection, aluno.getId(), idCurso, nota);
        System.out.println("Notas atribuídas com sucesso!");
    }
}

// Método privado para obter a lista de alunos matriculados em um curso
private static List<Aluno> getCursosAlunos(Connection connection, long idCurso) {
    List<Aluno> alunos = new ArrayList<>();

    try {
        // Monta a query SQL para obter alunos matriculados no curso
        String sql = "SELECT a.id, a.nome, a.email FROM alunos a " +
                     "JOIN alunos_cursos m ON a.id = m.id_aluno " +
                     "WHERE m.id_curso = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, idCurso);

            // Executa a consulta no banco de dados
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Itera sobre os resultados e cria objetos Aluno para adicionar à lista
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

// Método privado para atualizar a nota de um aluno em um curso
private static void atualizarNota(Connection connection, long idAluno, long idCurso, double nota) {
    try {
        // Monta a query SQL para inserir ou atualizar a nota do aluno no curso
        String sql = "INSERT INTO notas (id_aluno, id_curso, nota) VALUES (?, ?, ?) " +
                     "ON CONFLICT (id_aluno, id_curso) DO UPDATE SET nota = EXCLUDED.nota";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Define os parâmetros do PreparedStatement com as informações da nota
            preparedStatement.setLong(1, idAluno);
            preparedStatement.setLong(2, idCurso);
            preparedStatement.setDouble(3, nota);

            // Executa a inserção ou atualização no banco de dados
            preparedStatement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Método privado para obter o total de cursos matriculados por um aluno
private static int getTotalCursosMatriculados(Connection connection, long idAluno) {
    try {
        // Monta a query SQL para obter o total de cursos matriculados por um aluno
        String sql = "SELECT COUNT(*) FROM alunos_cursos WHERE id_aluno = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, idAluno);

            // Executa a consulta no banco de dados
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Retorna o total de cursos matriculados pelo aluno
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