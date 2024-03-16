package com.example.server.service;

import com.example.server.entity.TrackEntity;
import com.example.server.repository.MusicRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MusicServiceImpl implements MusicService {
  private final MusicRepository musicRepository;

  public MusicServiceImpl(MusicRepository musicRepository) {
    this.musicRepository = musicRepository;
  }

  @Override
  public List<TrackEntity> getAllTracks() {
    ArrayList<TrackEntity> tracks = new ArrayList<>();
    musicRepository.findAll().forEach(tracks::add);
    return tracks;
  }

  @Override
  public TrackEntity getTrackById(Long id) {
    return musicRepository.findById(id).orElse(null);
  }

  @Override
  public TrackEntity updateTrack(Long id, TrackEntity track) {
    TrackEntity trackEntity = musicRepository.findById(id).orElse(null);
    if (trackEntity != null) {
      trackEntity.setTitle(track.getTitle());
      trackEntity.setArtist(track.getArtist());
      trackEntity.setAlbum(track.getAlbum());
      musicRepository.save(trackEntity);
    }

    return trackEntity;
  }

  @Override
  public void addTrack(TrackEntity track) {
    musicRepository.save(track);
  }

  @Override
  public void deleteTrack(Long id) {
    musicRepository.deleteById(id);
  }
}
