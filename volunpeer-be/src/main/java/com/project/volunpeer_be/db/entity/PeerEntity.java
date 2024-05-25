package com.project.volunpeer_be.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
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
    private HashSet<String> connections;
    private HashSet<String> sentConnectionRequests;
    private HashSet<String> receivedConnectionRequests;
    private String personality;
    private List<Integer> points;

    @Data
    @AllArgsConstructor
    public static class Key {
        private String peerId;
    }
}
