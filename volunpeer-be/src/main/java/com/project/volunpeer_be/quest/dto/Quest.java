package com.project.volunpeer_be.quest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quest {
    private String orgName;
    private String title;
    private String relevantInterest;
    private double distance;
    private String locationName;
    private String imageUrl;
}
