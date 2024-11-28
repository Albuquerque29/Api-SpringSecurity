package com.example.Projeto.Back.End.repository;

import com.example.Projeto.Back.End.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByLogin(String login);  // Busca o usuário pelo login
}
