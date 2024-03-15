package com.example.server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
  @Email(message = "nazwa usera to poprawny email")
  private String username;

  @Pattern(
      regexp = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$",
      message = "hasło to 8 znaków oraz cyfra i wielka litera")
  private String password;
}
