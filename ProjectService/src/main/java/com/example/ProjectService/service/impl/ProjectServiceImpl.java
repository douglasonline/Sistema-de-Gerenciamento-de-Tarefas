package com.example.ProjectService.service.impl;

import com.example.ProjectService.exception.UserNotFoundException;
import com.example.ProjectService.model.Project;
import com.example.ProjectService.repository.ProjectRepository;
import com.example.ProjectService.service.ProjectService;
import com.example.ProjectService.service.UserService;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    private final UserService userService;

    // Lista todos os projetos
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Busca um projeto por ID e retorna uma mensagem se não existir
    @Override
    public Optional<Project> getProjectById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new RuntimeException("Project with ID " + projectId + " not found.");
        }
        return project;
    }

    // Cria um novo projeto
    @Override
    public Project createProject(Project project, HttpServletRequest request) {

        try {
            userService.getUserById(project.getUserId(), extractTokenFromRequest(request));
        } catch (FeignException.Unauthorized e) {
            String errorMessage = e.getMessage();
            String responseBody = e.contentUTF8();
            if (!responseBody.isEmpty()) {
                errorMessage = responseBody;
            }
            throw new UserNotFoundException(errorMessage);
        } catch (Exception e) {
            throw new UserNotFoundException("Usuário com ID " + project.getUserId() + " não encontrado.");
        }

        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        return projectRepository.save(project);
    }

    // Atualiza um projeto existente
    @Override
    public Project updateProject(Long projectId, Project updatedProject, HttpServletRequest request) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    try {
                        userService.getUserById(updatedProject.getUserId(), extractTokenFromRequest(request));
                    } catch (FeignException.Unauthorized e) {
                        String errorMessage = e.getMessage();
                        String responseBody = e.contentUTF8();
                        if (!responseBody.isEmpty()) {
                            errorMessage = responseBody;
                        }
                        throw new UserNotFoundException(errorMessage);
                    } catch (Exception e) {
                        throw new UserNotFoundException("Usuário com ID " + updatedProject.getUserId() + " não encontrado.");
                    }

                    project.setName(updatedProject.getName());
                    project.setDescription(updatedProject.getDescription());
                    project.setUpdatedAt(LocalDateTime.now());
                    return projectRepository.save(project);
                }).orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
    }

    // Deleta um projeto
    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    // Método auxiliar para extrair o token do cabeçalho
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken;
        }
        return null;
    }

}
