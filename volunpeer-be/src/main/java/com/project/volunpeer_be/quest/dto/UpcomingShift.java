package com.project.volunpeer_be.quest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpcomingShift {
    private String date;
    private String startDateTime;
    private String endDateTime;
}
