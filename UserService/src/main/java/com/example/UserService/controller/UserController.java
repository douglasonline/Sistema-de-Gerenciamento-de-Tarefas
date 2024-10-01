package com.example.UserService.controller;

import com.example.UserService.exception.UserAlreadyExistsException;
import com.example.UserService.exception.UserNotFoundException;
import com.example.UserService.model.User;
import com.example.UserService.payload.LoginRequest;
import com.example.UserService.payload.ResponsePayload;
import com.example.UserService.service.AuthService;
import com.example.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class UserController {

   private final UserService userService;
   private final AuthService authService;

   @PostMapping("/auth")
   public ResponseEntity<?> auth(@RequestBody LoginRequest loginRequest) {
      try {
         return ResponseEntity.ok(new ResponsePayload(authService.authenticate(loginRequest)));
      } catch (UsernameNotFoundException | BadCredentialsException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body(e.getMessage());
      }
   }

   @PostMapping("/create")
   public ResponseEntity<?> create(@RequestBody User user) {
      try {
         return ResponseEntity.ok(authService.register(user));
      } catch (UserAlreadyExistsException ex) {
         return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
      } catch (Exception ex) {
         return new ResponseEntity<>("Ocorreu um erro ao criar o usuário.", HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }

   // Endpoint para buscar um usuário por ID
   @GetMapping("byid/{userId}")
   public ResponseEntity<?> getUserById(@PathVariable Long userId) {
      try {
         User user = userService.getUserById(userId);
         return new ResponseEntity<>(user, HttpStatus.OK);
      } catch (UserNotFoundException ex) {
         return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
      } catch (Exception ex) {
         return new ResponseEntity<>("Ocorreu um erro ao buscar o usuário.", HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }


}

