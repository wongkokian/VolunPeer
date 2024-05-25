package com.project.volunpeer_be.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Entities")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganisationEntity {
    @Id
    private Key id;
    private String organisationId;
    private String name;
    private String description;

    @Data
    @AllArgsConstructor
    public static class Key {
        private String organisationId;
    }
}
