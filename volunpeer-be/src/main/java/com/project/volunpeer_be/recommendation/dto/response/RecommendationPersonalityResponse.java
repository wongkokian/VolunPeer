package com.project.volunpeer_be.recommendation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.volunpeer_be.common.dto.response.BaseResponse;
import com.project.volunpeer_be.recommendation.dto.Recommendation;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RecommendationPersonalityResponse extends BaseResponse {
    private List<Recommendation> recommendations;
}
