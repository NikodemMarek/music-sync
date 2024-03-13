package com.example.server;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
  @Id @GeneratedValue private Long id;

  public UserEntity(@NotBlank String username, @NotBlank String password) {
    this.username = username;
    this.password = password;
  }

  @Column(unique = true)
  @NotBlank
  @Getter
  @Setter
  private String username;

  @Getter @Setter @NotBlank private String password;
}
