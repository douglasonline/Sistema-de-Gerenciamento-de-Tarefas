package com.example.ProjectService.service;


import com.example.ProjectService.model.User;
import com.example.ProjectService.service.clients.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserClient userClient;

    public User getUserById(Long id, String token) {
        return userClient.getUserById(id, token);
    }
}
