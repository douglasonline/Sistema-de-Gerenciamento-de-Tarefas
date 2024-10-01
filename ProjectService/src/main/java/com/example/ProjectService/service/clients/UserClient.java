package com.example.ProjectService.service.clients;


import com.example.ProjectService.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "UserService")
public interface UserClient {
    @GetMapping("/byid/{userId}")
    User getUserById(@PathVariable("userId") Long userId, @RequestHeader("Authorization") String token);
}
