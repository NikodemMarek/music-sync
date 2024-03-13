package com.example.server;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository repository;
  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Override
  public UserResponseDTO createUser(final UserRequestDTO userRequestDTO) {
    final UserEntity userEntity =
        new UserEntity(userRequestDTO.getUsername(), encoder.encode(userRequestDTO.getPassword()));
    this.repository.save(userEntity);
    return new UserResponseDTO(userEntity.getUsername());
  }

  @Override
  public UserResponseDTO getUser(final Long id) {
    final UserEntity userEntity = this.repository.findById(id).orElseThrow();
    return new UserResponseDTO(userEntity.getUsername());
  }

  @Override
  public void updateUser(Long id, UserRequestDTO rq) {
    UserEntity userEntity = this.repository.findById(id).orElseThrow();
    userEntity.setUsername(rq.getUsername());
    userEntity.setPassword(encoder.encode(rq.getPassword()));
    this.repository.save(userEntity);
  }

  @Override
  public void deleteUser(final Long id) {
    this.repository.deleteById(id);
  }
}
