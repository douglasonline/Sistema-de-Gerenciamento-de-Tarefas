package com.example.ProjectService.controller;

import com.example.ProjectService.model.Project;
import com.example.ProjectService.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Endpoint para listar todos os projetos
    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Endpoint para buscar um projeto por ID
    @GetMapping("/byid/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable Long projectId) {
        try {
            Optional<Project> project = projectService.getProjectById(projectId);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para criar um novo projeto
    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody Project project, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(projectService.createProject(project, request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }

    // Endpoint para atualizar um projeto existente
    @PutMapping("/update/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable Long projectId, @RequestBody Project project, HttpServletRequest request) {
        try {
            Project updatedProject = projectService.updateProject(projectId, project, request);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Endpoint para deletar um projeto
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
