package com.project.volunpeer_be.quest.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestShift {
    private Integer shiftNum;
    private String date;
    private String startDateTime;
    private String endDateTime;
    private Integer totalSlots;
    private Integer filledSlots;
    private Integer isCompleted;

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
        LocalDateTime localDateTime = LocalDateTime.parse(startDateTime);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy");
        this.date = localDateTime.format(outputFormatter);
        outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        this.startDateTime = localDateTime.format(outputFormatter);
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
        LocalDateTime localDateTime = LocalDateTime.parse(endDateTime);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        this.endDateTime = localDateTime.format(outputFormatter);
    }

}
