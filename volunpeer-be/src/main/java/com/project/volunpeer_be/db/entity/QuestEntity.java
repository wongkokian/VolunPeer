package com.project.volunpeer_be.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.volunpeer_be.quest.dto.QuestShift;
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
    private String relevantInterest;
    private String contactName;
    private Integer contactNum;
    private String contactEmail;
    private List<Integer> mbtiTypes;
    private String imageUrl;
    private String locationCoordinates;
    private String locationName;
    private List<QuestShift> questShifts;

    @Data
    @AllArgsConstructor
    public static class Key {
        private String questId;
    }
}
