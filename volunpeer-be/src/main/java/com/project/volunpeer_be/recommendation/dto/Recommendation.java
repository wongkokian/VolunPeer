package com.project.volunpeer_be.recommendation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Recommendation {
    private String questId;
    private String orgName;
    private String title;
    private String description;
    private String relevantInterest;
    private Double score;
}