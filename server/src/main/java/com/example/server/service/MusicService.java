package com.example.server.service;

import com.example.server.entity.TrackEntity;
import java.util.List;

public interface MusicService {
  List<TrackEntity> getAllTracks();

  TrackEntity getTrackById(Long id);

  void addTrack(TrackEntity track);

  TrackEntity updateTrack(Long id, TrackEntity track);

  void deleteTrack(Long id);
}
