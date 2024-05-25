package com.project.volunpeer_be.quest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quest {
    private String questId;
    private String orgName;
    private String title;
    private String relevantInterest;
    private String distance;
    private String locationName;
    private String imageUrl;
    private String numRegistered;
    private String startDateTime;
    private String endDateTime;
}
