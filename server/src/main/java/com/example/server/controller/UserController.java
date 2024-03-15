package com.example.server.controller;

import com.example.server.dto.TokenResponseDTO;
import com.example.server.dto.UserRequestDTO;
import com.example.server.dto.UserResponseDTO;
import com.example.server.service.JwtService;
import com.example.server.service.UserServiceImpl;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private static final long CONFIRMATION_TOKEN_EXPIRATION_TIME = 30000;
  private static final long LOGIN_TOKEN_EXPIRATION_TIME = 86400000;

  private final UserServiceImpl userService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/api/register")
  public ResponseEntity<UserResponseDTO> registerUser(
      final @Valid @RequestBody UserRequestDTO userRequestDto) {
    Optional<UserRequestDTO> registeredUser =
        userService.getUserByUsername(userRequestDto.getUsername());

    if (registeredUser.isEmpty()) {
      String password = userRequestDto.getPassword();
      String encryptedPassword = passwordEncoder.encode(password);

      userRequestDto.setPassword(encryptedPassword);
      userService.createUser(userRequestDto);

      String token =
          JwtService.generateToken(
              userRequestDto.getUsername(), CONFIRMATION_TOKEN_EXPIRATION_TIME);

      return new ResponseEntity<>(new UserResponseDTO(token), HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(
          new UserResponseDTO("username already exists"), HttpStatus.CONFLICT);
    }
  }

  @PostMapping("/api/login")
  public ResponseEntity<?> loginUser(final @Valid @RequestBody UserRequestDTO userRequestDto) {
    Optional<UserRequestDTO> registeredUser =
        userService.getUserByUsername(userRequestDto.getUsername());
    if (registeredUser.isEmpty()) {
      return new ResponseEntity<>(new UserResponseDTO(), HttpStatus.FORBIDDEN);
    } else {
      String token =
          JwtService.generateToken(userRequestDto.getUsername(), LOGIN_TOKEN_EXPIRATION_TIME);
      return new ResponseEntity<>(new TokenResponseDTO(token), HttpStatus.OK);
    }
  }

  @GetMapping("/api/logout/{token}")
  public ResponseEntity<?> logoutUser(@PathVariable String token) {
    if (!userService.expiredTokensContains(token)) {
      userService.addExpiredToken(token);
    }
    return new ResponseEntity<>(new UserResponseDTO("wylogowano"), HttpStatus.OK);
  }

  @GetMapping("/api/confirm/{token}")
  public ResponseEntity<?> confirmUser(@PathVariable String token) {
    String username = JwtService.getUsernameFromToken(token);
    Optional<UserRequestDTO> user = userService.getUserByUsername(username);

    if (user.isPresent()) {
      if (user.get().getConfirmed()) {
        return new ResponseEntity<>(
            new UserResponseDTO("konto wcześniej potwierdzone, zaloguj się"), HttpStatus.OK);
      } else {
        user.get().setConfirmed(true);
        return new ResponseEntity<>(
            new UserResponseDTO("konto potwierdzone, zaloguj się"), HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(new UserResponseDTO("konto nie istnieje"), HttpStatus.NOT_FOUND);
  }

  @GetMapping("/api/users/{id}")
  public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
    return new ResponseEntity<>(this.userService.getUser(id), HttpStatus.OK);
  }

  @PutMapping("/api/users/{id}")
  public ResponseEntity<UserResponseDTO> updateUser(
      @PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDto) {
    this.userService.updateUser(id, userRequestDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/api/users/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    this.userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
