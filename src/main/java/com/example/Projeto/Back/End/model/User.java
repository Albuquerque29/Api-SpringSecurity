package com.example.Projeto.Back.End.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)  // Usar a string para armazenar o valor do enum no banco
    private UserRole role;

    // Construtores, Getters e Setters

    public User() {}

    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    // Implementando os métodos necessários para UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna as permissões/roles do usuário. Aqui, você pode adicionar suas roles de maneira personalizada.
        return List.of(() -> role.name());  // Se você tiver múltiplos roles, adapte conforme necessário
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Define a validade da conta. Retorne true se não houver expiração.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Define se a conta está bloqueada.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Define se as credenciais expiraram.
    }

    @Override
    public boolean isEnabled() {
        return true;  // Define se o usuário está habilitado.
    }
}
