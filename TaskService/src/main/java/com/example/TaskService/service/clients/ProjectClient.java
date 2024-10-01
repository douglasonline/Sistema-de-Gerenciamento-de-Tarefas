package com.example.TaskService.service.clients;

import com.example.TaskService.model.Project;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ProjectService")
public interface ProjectClient {
    @GetMapping("/byid/{projectId}")
    Project getProjectById(@PathVariable("projectId") Long id);
}
