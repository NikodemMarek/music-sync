package com.example.server.service;

import com.example.server.dto.UserRequestDTO;
import com.example.server.dto.UserResponseDTO;
import com.example.server.entity.UserEntity;
import com.example.server.repository.UserRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  private final ArrayList<String> expiredTokens = new ArrayList<>();

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserResponseDTO createUser(final UserRequestDTO userRequestDTO) {
    final UserEntity userEntity =
        new UserEntity(userRequestDTO.getUsername(), encoder.encode(userRequestDTO.getPassword()));
    this.userRepository.save(userEntity);
    return new UserResponseDTO(userEntity.getUsername());
  }

  @Override
  public UserResponseDTO getUser(final Long id) {
    final UserEntity userEntity = this.userRepository.findById(id).orElseThrow();
    return new UserResponseDTO(userEntity.getUsername());
  }

  @Override
  public void updateUser(Long id, UserRequestDTO rq) {
    UserEntity userEntity = this.userRepository.findById(id).orElseThrow();
    userEntity.setUsername(rq.getUsername());
    userEntity.setPassword(encoder.encode(rq.getPassword()));
    this.userRepository.save(userEntity);
  }

  @Override
  public void deleteUser(final Long id) {
    this.userRepository.deleteById(id);
  }

  @Override
  public void addExpiredToken(String token) {
    expiredTokens.add(token);
  }

  @Override
  public Boolean expiredTokensContains(String token) {
    return expiredTokens.contains(token);
  }

  @Override
  public Optional<UserRequestDTO> getUserByUsername(String username) {
    Optional<UserEntity> user = this.userRepository.findByUsername(username);
    if (user.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(
        new UserRequestDTO(user.get().getUsername(), user.get().getPassword(), true));
  }
}
