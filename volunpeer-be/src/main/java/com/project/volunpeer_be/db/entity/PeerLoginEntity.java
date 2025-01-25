package com.project.volunpeer_be.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Entities")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeerLoginEntity extends LoginEntity {
    private String peerId;
}
