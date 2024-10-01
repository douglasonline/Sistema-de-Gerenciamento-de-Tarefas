package com.example.TaskService.service.impl;

import com.example.TaskService.exception.ProjectNotFoundException;
import com.example.TaskService.exception.TaskNotFoundException;
import com.example.TaskService.exception.TasksNotFoundException;
import com.example.TaskService.exception.UserNotFoundException;
import com.example.TaskService.model.Task;
import com.example.TaskService.model.TaskStatus;
import com.example.TaskService.repository.TaskRepository;
import com.example.TaskService.service.ProjectService;
import com.example.TaskService.service.TaskService;
import com.example.TaskService.service.UserService;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserService userService;

    private final ProjectService projectService;

    @Autowired
    TaskRepository taskRepository;

    // Injeção do AmqpTemplate
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            throw new TasksNotFoundException();
        }
        return tasks;
    }

    @Override
    public Optional<Task> getTaskById(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new TaskNotFoundException(taskId);
        }
        return task;
    }

    @Override
    public Task createTask(Task task, HttpServletRequest request) {

        String token = extractTokenFromRequest(request);

        try {
            userService.getUserById(task.getUserId(), token);
        } catch (FeignException.Unauthorized e) {
            String errorMessage = e.getMessage();
            String responseBody = e.contentUTF8();
            if (!responseBody.isEmpty()) {
                errorMessage = responseBody;
            }
            throw new UserNotFoundException(errorMessage);
        } catch (Exception e) {
            throw new UserNotFoundException("Usuário com ID " + task.getUserId() + " não encontrado.");
        }

        try {
            projectService.getProjectById(task.getProjectId());
        } catch (Exception e) {
            throw new ProjectNotFoundException("Projeto com ID " + task.getProjectId() + " não encontrado.");
        }

        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, Task updatedTask, HttpServletRequest request) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    // Verifica se o usuário associado à tarefa existe
                    try {
                        userService.getUserById(updatedTask.getUserId(), extractTokenFromRequest(request));
                    } catch (FeignException.Unauthorized e) {
                        String errorMessage = e.getMessage();
                        String responseBody = e.contentUTF8();
                        if (!responseBody.isEmpty()) {
                            errorMessage = responseBody;
                        }
                        throw new UserNotFoundException(errorMessage);
                    } catch (Exception e) {
                        throw new UserNotFoundException("Usuário com ID " + updatedTask.getUserId() + " não encontrado.");
                    }

                    // Verifica se o projeto associado à tarefa existe
                    try {
                        projectService.getProjectById(updatedTask.getProjectId());
                    } catch (Exception e) {
                        throw new ProjectNotFoundException("Projeto com ID " + updatedTask.getProjectId() + " não encontrado.");
                    }

                    // Atualiza as informações da tarefa
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setStatus(updatedTask.getStatus());
                    task.setPriority(updatedTask.getPriority());
                    task.setUpdatedAt(LocalDateTime.now());

                    // Verifica se a tarefa foi marcada como concluída (status == TaskStatus.COMPLETED)
                    if (updatedTask.getStatus() == TaskStatus.COMPLETED) {
                        // Envio da mensagem para a fila do RabbitMQ
                        rabbitTemplate.convertAndSend("task-updates", "Tarefa com ID " + taskId + " foi concluída.");
                    }

                    // Salva a tarefa atualizada no banco de dados
                    return taskRepository.save(task);
                }).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }


    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
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
