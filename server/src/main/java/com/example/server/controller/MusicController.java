package com.example.server.controller;

import com.example.server.entity.TrackEntity;
import com.example.server.service.MusicServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/music")
public class MusicController {
  private final MusicServiceImpl service;

  @GetMapping("")
  public ResponseEntity<?> getAllTracks() {
    return new ResponseEntity<>(service.getAllTracks(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getTrackById(@PathVariable Long id) {
    TrackEntity track = service.getTrackById(id);
    if (track == null) {
      return new ResponseEntity<>("track not found", HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(track, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> addTrack(@RequestBody TrackEntity track) {
    service.addTrack(track);
    return new ResponseEntity<>("track added", HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateTrack(@PathVariable Long id, @RequestBody TrackEntity track) {
    TrackEntity updatedTrack = service.updateTrack(id, track);
    if (updatedTrack == null) {
      return new ResponseEntity<>("track not found", HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(updatedTrack, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteTrack(@PathVariable Long id) {
    service.deleteTrack(id);
    return new ResponseEntity<>("track deleted", HttpStatus.OK);
  }
}
