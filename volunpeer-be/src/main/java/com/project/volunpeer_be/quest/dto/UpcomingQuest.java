package com.project.volunpeer_be.quest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpcomingQuest {
    private String orgName;
    private String title;
    private String relevantInterest;
    private String distance;
    private String locationName;
    private String imageUrl;
    private String numRegistered;
    private List<UpcomingShift> shifts;
}
