package com.project.volunpeer.db.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.volunpeer.db.entity.QuestEntity;

@Repository
public interface QuestRepository extends MongoRepository<QuestEntity, QuestEntity.Key> {
}