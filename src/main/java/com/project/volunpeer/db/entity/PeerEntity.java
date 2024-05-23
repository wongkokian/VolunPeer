package com.project.volunpeer.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("Entities")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeerEntity {
    @Id
    private Key id;
    private String peerId;
    private String name;
    private List<String> interests;
    private String location;
    private List<String> connections;
    private String personality;

    @Data
    @AllArgsConstructor
    public static class Key {
        private String peerId;
    }
}
