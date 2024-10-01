package com.example.TaskService.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long taskId) {
        super("Tarefa com ID " + taskId + " não encontrada.");
    }
}
