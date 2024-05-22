package com.project.volunpeer.peer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("Entities")
public class PeerEntity {
    @Id
    private Key id;
    private String peerId;
    private String name;
    private String bio;
    private List<String> interests;
    private String location;
    private List<String> connections;
    private List<String> traits;

    @Data
    @AllArgsConstructor
    public static class Key {
        private String peerId;
    }
}
