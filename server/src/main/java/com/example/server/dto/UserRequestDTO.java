package com.example.server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
  @Email(message = "username has to be an email")
  private String username;

  @NotEmpty(message = "password cannot be empty")
  @Size(min = 3, max = 10, message = "password must be 3 to 10 characters long")
  private String password = "";

  private Boolean confirmed = false;
}
