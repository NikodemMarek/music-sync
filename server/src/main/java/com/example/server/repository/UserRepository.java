package com.example.server.repository;

import com.example.server.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsername(final String username);
}
