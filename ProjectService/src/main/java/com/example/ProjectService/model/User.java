package com.example.ProjectService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Role role = Role.USER;
    private String username;
}
