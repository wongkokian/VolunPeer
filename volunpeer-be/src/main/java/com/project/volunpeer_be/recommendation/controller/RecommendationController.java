package com.project.volunpeer_be.recommendation.controller;

import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationAllResponse;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationInterestResponse;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationPersonalityResponse;
import com.project.volunpeer_be.recommendation.service.RecommendationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    @Autowired
    RecommendationService recommendationService;

    @GetMapping("/all")
    public RecommendationAllResponse getAllRecommendation(HttpServletRequest httpRequest) {
        RecommendationAllResponse response = new RecommendationAllResponse();
        try {
            response = recommendationService.getAllRecommendation(httpRequest);
        } catch (Exception e) {
            System.out.println(e);
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @GetMapping("/interest")
    public RecommendationInterestResponse getInterestRecommendation(HttpServletRequest httpRequest) {
        RecommendationInterestResponse response = new RecommendationInterestResponse();
        try {
            response = recommendationService.getInterestRecommendation(httpRequest);
        } catch (Exception e) {
            System.out.println(e);
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @GetMapping("/personality")
    public RecommendationPersonalityResponse getPersonalityRecommendation(
            HttpServletRequest httpRequest) {
        RecommendationPersonalityResponse response = new RecommendationPersonalityResponse();
        try {
            response = recommendationService.getPersonalityRecommendation(httpRequest);
        } catch (Exception e) {
            System.out.println(e);
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

}
