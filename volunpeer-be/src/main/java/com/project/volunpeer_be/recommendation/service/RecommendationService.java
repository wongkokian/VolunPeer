package com.project.volunpeer_be.recommendation.service;

import com.project.volunpeer_be.recommendation.dto.response.RecommendationAllResponse;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationInterestResponse;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationPersonalityResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface RecommendationService {
    RecommendationAllResponse getAllRecommendation(HttpServletRequest httpRequest);

    RecommendationInterestResponse getInterestRecommendation(HttpServletRequest httpRequest);

    RecommendationPersonalityResponse getPersonalityRecommendation(HttpServletRequest httpRequest);
}
