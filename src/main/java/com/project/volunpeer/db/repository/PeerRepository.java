package com.project.volunpeer.db.repository;

import com.project.volunpeer.db.entity.PeerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeerRepository extends MongoRepository<PeerEntity, PeerEntity.Key> {
}