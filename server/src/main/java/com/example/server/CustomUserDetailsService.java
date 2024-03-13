package com.example.server;

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
    final UserEntity userEntity = this.repository.findByUsername(username);
    if (userEntity == null) {
      throw new UsernameNotFoundException("Unknown user " + username);
    }
    return org.springframework.security.core.userdetails.User.withUsername(userEntity.getUsername())
        .password(userEntity.getPassword())
        .authorities("ROLE_USER")
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }
}
