package com.example.TaskService.exception;

public class TasksNotFoundException extends RuntimeException {
    public TasksNotFoundException() {
        super("Nenhuma tarefa encontrada.");
    }
}
