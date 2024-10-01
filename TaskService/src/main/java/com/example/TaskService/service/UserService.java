package com.example.TaskService.service;

import com.example.TaskService.model.User;
import com.example.TaskService.service.clients.UserClient;
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
