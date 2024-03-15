package com.example.server.service;

import com.example.server.dto.UserRequestDTO;
import com.example.server.dto.UserResponseDTO;
import java.util.Optional;

public interface UserService {
  UserResponseDTO createUser(final UserRequestDTO rq);

  void addExpiredToken(String token);

  Boolean expiredTokensContains(String token);

  Optional<UserRequestDTO> getUserByUsername(String username);

  UserResponseDTO getUser(final Long id);

  void updateUser(final Long id, final UserRequestDTO rq);

  void deleteUser(final Long id);
}
