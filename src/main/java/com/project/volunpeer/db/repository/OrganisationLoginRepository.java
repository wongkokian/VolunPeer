package com.project.volunpeer.db.repository;

import com.project.volunpeer.db.entity.LoginEntity;
import com.project.volunpeer.db.entity.OrganisationLoginEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisationLoginRepository extends MongoRepository<OrganisationLoginEntity, LoginEntity.Key> {
    Optional<OrganisationLoginEntity> findByUsername(String username);

    Boolean existsByUsername(String username);
}