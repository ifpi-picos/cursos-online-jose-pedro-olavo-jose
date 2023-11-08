package br.edu.ifpi;

import br.edu.ifpi.enums.StatusCurso;

public class App {
    public static void main(String[] args) {
        System.out.println("Sistema de cursos online!");

        // Exemplo de uso da enum StatusCurso
        StatusCurso status = StatusCurso.ABERTO;
        System.out.println("Status do curso: " + status);
    }
}
