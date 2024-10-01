package com.example.UserService.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("Usuário com ID " + userId + " não encontrado.");
    }
}
