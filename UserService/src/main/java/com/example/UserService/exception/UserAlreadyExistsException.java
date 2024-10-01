package com.example.UserService.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super("Usuário com o e-mail " + email + " já existe.");
    }
}
