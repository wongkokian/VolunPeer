package com.project.volunpeer_be.recommendation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.volunpeer_be.common.dto.response.BaseResponse;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RecommendationResponse extends BaseResponse {
    public String orgId;
    public String title;
    public String description;
    public List<String> relevantInterests;
    public String contactName;
    public String contactNum;
    public String contactEmail;
    public List<Integer> mbtiTypes;
    public int numRegistered;
    public double score;
}