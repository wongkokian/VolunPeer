package com.project.volunpeer_be.recommendation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Recommendation {
    // CORE FOR FE
    private String title;
    private String orgName;
    private String locationName;
    private String imageUrl;
    private String numRegistered;
    private String score;
    private String questId;
    private String startDateTime;
    private String endDateTime;

    // NON CORE
    private double double_score;
    private String description;
    private String relevantInterest;
}