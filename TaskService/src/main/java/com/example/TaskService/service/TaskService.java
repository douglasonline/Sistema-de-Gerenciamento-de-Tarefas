package com.example.TaskService.service;


import com.example.TaskService.model.Task;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {

    public abstract List<Task> getAllTasks();

    public abstract Optional<Task> getTaskById(Long taskId);

    public abstract Task createTask(Task task, HttpServletRequest request);

    public abstract Task updateTask(Long taskId, Task updatedTask, HttpServletRequest request);

    public abstract void deleteTask(Long taskId);

}