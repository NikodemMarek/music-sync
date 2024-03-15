package com.example.server.service;

import com.example.server.dto.UserRequestDTO;
import com.example.server.dto.UserResponseDTO;

public interface UserService {
  UserResponseDTO createUser(final UserRequestDTO rq);

  UserResponseDTO getUser(final Long id);

  void updateUser(final Long id, final UserRequestDTO rq);

  void deleteUser(final Long id);
}
