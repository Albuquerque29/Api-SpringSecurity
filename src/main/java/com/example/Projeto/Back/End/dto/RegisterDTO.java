package com.example.Projeto.Back.End.dto;

import com.example.Projeto.Back.End.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterDTO {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotNull
    private UserRole role;

    // Getters e Setters
    public String login() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String password() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole role() { // Método role() ao invés de roles()
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
