package com.meuapp.api.model;

public class LoginRequest {
    private String login;
    private String senha;

    // Construtor, getters e setters
    public LoginRequest(String login, String senha) {
        this.login = login;
        this.senha = senha; 
    }
    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

}
