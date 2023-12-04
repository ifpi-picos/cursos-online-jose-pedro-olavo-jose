CREATE TABLE professores (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE professores (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE alunos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE cursos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    carga_horaria INTEGER NOT NULL
);

CREATE TABLE professores_cursos (
    id SERIAL PRIMARY KEY,
    id_professor BIGINT REFERENCES professores(id),
    id_curso BIGINT REFERENCES cursos(id),
    CONSTRAINT pk_professores_cursos UNIQUE (id_professor, id_curso)
);

CREATE TABLE alunos_cursos (
    id SERIAL PRIMARY KEY,
    id_aluno BIGINT REFERENCES alunos(id),
    id_curso BIGINT REFERENCES cursos(id),
    CONSTRAINT pk_alunos_cursos UNIQUE (id_aluno, id_curso)
);

CREATE TABLE notas (
    id SERIAL PRIMARY KEY,
    id_aluno INTEGER,
    id_curso INTEGER,
    nota DOUBLE PRECISION,
    FOREIGN KEY (id_aluno) REFERENCES alunos(id),
    FOREIGN KEY (id_curso) REFERENCES cursos(id)
);