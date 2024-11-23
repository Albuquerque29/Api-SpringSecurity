package dto;

import model.UserRole;
public record RegisterDTO(String login, String password, UserRole role) {
}
