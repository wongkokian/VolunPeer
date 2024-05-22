package com.project.volunpeer.peer.repository;

import com.project.volunpeer.peer.entity.PeerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PeerRepository extends MongoRepository<PeerEntity, PeerEntity.Key> {

}