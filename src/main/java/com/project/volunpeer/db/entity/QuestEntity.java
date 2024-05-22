package com.project.volunpeer.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("Quests")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestEntity {
    @Id
    private Key id;
    private String questId;
    private String orgId;
    private String title;
    private String description;
    private String startDateTime;
    private String endDateTime;
    private List<String> relevantInterests;
    private String location;
    private String contactName;
    private String contactNum;
    private String contactEmail;

    @Data
    @AllArgsConstructor
    public static class Key {
        private String questId;
    }
}
