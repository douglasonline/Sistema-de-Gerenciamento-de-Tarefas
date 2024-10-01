package com.example.TaskService.controller;

import com.example.TaskService.exception.TaskNotFoundException;
import com.example.TaskService.exception.TasksNotFoundException;
import com.example.TaskService.model.Task;
import com.example.TaskService.service.impl.TaskServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        try {
            List<Task> tasks = taskServiceImpl.getAllTasks();
            return ResponseEntity.ok(tasks);
        } catch (TasksNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocorreu um erro ao fazer a busca.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byid/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable Long taskId) {
        try {
            Optional<Task> task = taskServiceImpl.getTaskById(taskId);
            return ResponseEntity.ok(task);
        } catch (TaskNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocorreu um erro ao fazer a busca.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody Task task, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(taskServiceImpl.createTask(task, request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody Task taskDetails, HttpServletRequest request) {
        try {
           return ResponseEntity.ok(taskServiceImpl.updateTask(taskId, taskDetails, request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskServiceImpl.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
