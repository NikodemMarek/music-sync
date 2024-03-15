package com.example.server.service;

import com.example.server.entity.UserEntity;
import com.example.server.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    Optional<UserEntity> user = this.repository.findByUsername(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("Unknown user " + username);
    }
    return org.springframework.security.core.userdetails.User.builder()
        .username(user.get().getUsername())
        .password(user.get().getPassword())
        .build();
  }
}
