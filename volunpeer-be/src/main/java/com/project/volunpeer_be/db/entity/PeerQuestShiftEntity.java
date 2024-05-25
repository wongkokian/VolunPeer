package com.project.volunpeer_be.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Peer-Quest-Shifts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeerQuestShiftEntity {
    @Id
    private Key id;
    private String peerId;
    private String questId;
    private Integer shiftNum;

    @Data
    @AllArgsConstructor
    public static class Key {
        private String peerId;
        private String questId;
        private Integer shiftNum;
    }
}
