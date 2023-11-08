package br.edu.ifpi.auth;

import java.util.HashMap;
import java.util.Map;

public class SistemaDeAutenticacao {
    private Map<String, String> usuarios;  
    private Map<String, String> permissoes; 

    public SistemaDeAutenticacao() {
        this.usuarios = new HashMap<>();
        this.permissoes = new HashMap<>();
    }

    public boolean autenticar(String usuario, String senha) {
        if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(senha)) {
            return true;
        }
        return false;
    }

    public String obterPermissao(String usuario) {
        return permissoes.get(usuario);
    }
}
