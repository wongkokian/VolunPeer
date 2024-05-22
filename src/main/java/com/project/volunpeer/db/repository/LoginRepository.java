package com.project.volunpeer.db.repository;

import com.project.volunpeer.db.entity.LoginEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends MongoRepository<LoginEntity, LoginEntity.Key> {
    Optional<LoginEntity> findByUsername(String username);

    Boolean existsByUsername(String username);
}