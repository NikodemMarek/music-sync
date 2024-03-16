package com.example.server.repository;

import com.example.server.entity.TrackEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends CrudRepository<TrackEntity, Long> {}
