package com.example.TaskService.service;

import com.example.TaskService.model.Project;
import com.example.TaskService.service.clients.ProjectClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectClient projectClient;
    public Project getProjectById(Long id){
        return projectClient.getProjectById(id);
    }

}
