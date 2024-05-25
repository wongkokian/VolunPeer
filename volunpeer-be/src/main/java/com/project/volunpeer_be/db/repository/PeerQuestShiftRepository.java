package com.project.volunpeer_be.db.repository;

import com.project.volunpeer_be.db.entity.PeerQuestShiftEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeerQuestShiftRepository extends MongoRepository<PeerQuestShiftEntity, PeerQuestShiftEntity.Key> {
}