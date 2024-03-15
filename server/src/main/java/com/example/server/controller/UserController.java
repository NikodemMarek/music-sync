package com.example.server.controller;

import com.example.server.dto.UserRequestDTO;
import com.example.server.dto.UserResponseDTO;
import com.example.server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class UserController {
  private final UserService service;

  @PostMapping("/api/users")
  public ResponseEntity<UserResponseDTO> createUser(
      final @Valid @RequestBody UserRequestDTO userRequestDto) {
    return new ResponseEntity<>(this.service.createUser(userRequestDto), HttpStatus.CREATED);
  }

  @GetMapping("/api/users/{id}")
  public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
    return new ResponseEntity<>(this.service.getUser(id), HttpStatus.OK);
  }

  @PutMapping("/api/users/{id}")
  public ResponseEntity<UserResponseDTO> updateUser(
      @PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDto) {
    this.service.updateUser(id, userRequestDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/api/users/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    this.service.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
