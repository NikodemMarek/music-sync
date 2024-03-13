package com.example.server;

public interface UserService {
  UserResponseDTO createUser(final UserRequestDTO rq);

  UserResponseDTO getUser(final Long id);

  void updateUser(final Long id, final UserRequestDTO rq);

  void deleteUser(final Long id);
}
