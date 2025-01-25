package com.project.volunpeer_be.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("QuestShifts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestShiftEntity {
    @Id
    private Key id;
    private String questId;
    private Integer shiftNum;
    private String startDateTime;
    private String endDateTime;
    private Integer totalSlots;
    private Integer filledSlots;
    private Integer isCompleted;

    @Data
    @AllArgsConstructor
    public static class Key {
        private String questId;
        private Integer shiftNum;
    }
}
