package com.project.volunpeer_be.quest.dto;

import lombok.Data;

@Data
public class QuestShift {
    private String shiftNum;
    private String startDateTime;
    private String endDateTime;
    private Integer totalSlots;
    private Integer filledSlots;
    private Integer isCompleted;
}
