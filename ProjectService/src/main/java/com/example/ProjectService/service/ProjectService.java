package com.example.ProjectService.service;

import com.example.ProjectService.model.Project;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProjectService {

    public abstract List<Project> getAllProjects();

    public abstract Optional<Project> getProjectById(Long projectId);

    public abstract Project createProject(Project project, HttpServletRequest request);

    public abstract Project updateProject(Long projectId, Project updatedProject, HttpServletRequest request);

    public abstract void deleteProject(Long projectId);

}
