package com.project.volunpeer_be.db.repository;

import com.project.volunpeer_be.db.entity.QuestShiftEntity;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestShiftRepository extends MongoRepository<QuestShiftEntity, QuestShiftEntity.Key> {
    // Find all quest shifts based on questId
    List<QuestShiftEntity> findByQuestId(String questId);
}