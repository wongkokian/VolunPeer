package com.project.volunpeer.db.repository;

import com.project.volunpeer.db.entity.QuestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends MongoRepository<QuestEntity, QuestEntity.Key> {
}