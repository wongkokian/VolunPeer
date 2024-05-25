package com.project.volunpeer_be.db.repository;

import com.project.volunpeer_be.db.entity.OrganisationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisationRepository extends MongoRepository<OrganisationEntity, OrganisationEntity.Key> {

}