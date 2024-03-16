package com.example.server.entity;

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
@Getter
@Setter
public class TrackEntity {
  @Id @GeneratedValue private Long id;

  @Getter @Setter @NotBlank private String title;
  @Getter @Setter @NotBlank private String artist;
  @Getter @Setter @NotBlank private String album;
}
