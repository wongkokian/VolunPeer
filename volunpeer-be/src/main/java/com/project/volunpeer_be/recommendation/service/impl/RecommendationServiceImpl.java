package com.project.volunpeer_be.recommendation.service.impl;

import com.project.volunpeer_be.db.entity.PeerEntity;
import com.project.volunpeer_be.db.entity.PeerLoginEntity;
import com.project.volunpeer_be.db.entity.QuestEntity;
import com.project.volunpeer_be.db.repository.PeerLoginRepository;
import com.project.volunpeer_be.db.repository.PeerRepository;
import com.project.volunpeer_be.db.repository.QuestRepository;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationAllResponse;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationInterestResponse;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationPersonalityResponse;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationResponse;
import com.project.volunpeer_be.recommendation.service.RecommendationService;
import com.project.volunpeer_be.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service

public class RecommendationServiceImpl implements RecommendationService {
    @Autowired
    QuestRepository questRepository;
    @Autowired
    PeerRepository peerRepository;
    @Autowired
    PeerLoginRepository peerLoginRepository;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public RecommendationAllResponse getAllRecommendation(HttpServletRequest httpRequest) {
        RecommendationAllResponse response = new RecommendationAllResponse();
        PeerEntity peerEntity = getPeerFromHttpRequest(httpRequest);
        List<RecommendationResponse> list_recs = new ArrayList<>();

        // All Quests, need to change to quests that are not completed, maybe a filter
        List<QuestEntity> questEntity = questRepository.findAll();
        questEntity.forEach(quest -> {
            double score = getDistanceScore(peerEntity.getLocation(), "1.355245,104.031772245") // NEED TO CHANGE HERE I CANT
                    // FIND LOCATION
                    + getInterestScore(peerEntity.getInterests(), "art") // NEED TO CHANGE HERE I CANT FIND INTERESTS
                    + getPersonalityScore("INTP", Arrays.asList(5, 5, 5, 5, 5, 5, 5, 5));
            score = score / 3;
            RecommendationResponse recommendation = new RecommendationResponse();
            recommendation.orgId = quest.getOrgId();
            recommendation.title = quest.getTitle();
            recommendation.description = quest.getDescription();
            recommendation.relevantInterests = quest.getRelevantInterests();
            recommendation.contactName = quest.getContactName();
            recommendation.contactNum = quest.getContactNum();
            recommendation.contactEmail = quest.getContactEmail();
            recommendation.mbtiTypes = quest.getMbtiTypes();
            recommendation.numRegistered = quest.getNumRegistered();
            recommendation.score = score;
            list_recs.add(recommendation);
        });
        list_recs.sort((a, b) -> a.score < b.score ? -1 : a.score == b.score ? 0 : 1);
        response.setRecommendations(list_recs);
        return response;
    }

    @Override
    public RecommendationInterestResponse getInterestRecommendation(HttpServletRequest httpRequest) {
        RecommendationInterestResponse response = new RecommendationInterestResponse();
        PeerEntity peerEntity = getPeerFromHttpRequest(httpRequest);
        List<RecommendationResponse> list_recs = new ArrayList<>();

        // All Quests, need to change to quests that are not completed, maybe a filter
        List<QuestEntity> questEntity = questRepository.findAll();
        questEntity.forEach(quest -> {
            double score = getDistanceScore(peerEntity.getLocation(), "1.355245,104.031772245") // NEED TO CHANGE HERE I CANT
                    // FIND LOCATION
                    + getInterestScore(peerEntity.getInterests(), "art"); // NEED TO CHANGE HERE I CANT FIND INTERESTS
            score = score / 2;
            RecommendationResponse recommendation = new RecommendationResponse();
            recommendation.orgId = quest.getOrgId();
            recommendation.title = quest.getTitle();
            recommendation.description = quest.getDescription();
            recommendation.relevantInterests = quest.getRelevantInterests();
            recommendation.contactName = quest.getContactName();
            recommendation.contactNum = quest.getContactNum();
            recommendation.contactEmail = quest.getContactEmail();
            recommendation.mbtiTypes = quest.getMbtiTypes();
            recommendation.numRegistered = quest.getNumRegistered();
            recommendation.score = score;
            list_recs.add(recommendation);
        });
        list_recs.sort((a, b) -> a.score < b.score ? -1 : a.score == b.score ? 0 : 1);
        response.setRecommendations(list_recs);
        return response;
    }

    @Override
    public RecommendationPersonalityResponse getPersonalityRecommendation(HttpServletRequest httpRequest) {
        RecommendationPersonalityResponse response = new RecommendationPersonalityResponse();
        PeerEntity peerEntity = getPeerFromHttpRequest(httpRequest);
        List<RecommendationResponse> list_recs = new ArrayList<>();

        // All Quests, need to change to quests that are not completed, maybe a filter
        List<QuestEntity> questEntity = questRepository.findAll();
        questEntity.forEach(quest -> {
            double score = getDistanceScore(peerEntity.getLocation(), "1.355245,104.031772245") // NEED TO CHANGE HERE I CANT
                    // FIND LOCATION
                    + getPersonalityScore("INTP", Arrays.asList(5, 5, 5, 5, 5, 5, 5, 5));// NEED TO CHANGE HERE I CANT FIND

            RecommendationResponse recommendation = new RecommendationResponse();
            recommendation.orgId = quest.getOrgId();
            recommendation.title = quest.getTitle();
            recommendation.description = quest.getDescription();
            recommendation.relevantInterests = quest.getRelevantInterests();
            recommendation.contactName = quest.getContactName();
            recommendation.contactNum = quest.getContactNum();
            recommendation.contactEmail = quest.getContactEmail();
            recommendation.mbtiTypes = quest.getMbtiTypes();
            recommendation.numRegistered = quest.getNumRegistered();
            recommendation.score = score;
            list_recs.add(recommendation);
        });
        list_recs.sort((a, b) -> a.score < b.score ? -1 : a.score == b.score ? 0 : 1);
        response.setRecommendations(list_recs);
        return response;
    }

    private double getDistanceScore(String peerLocation, String questLocation) {
        String[] peerLatLong = peerLocation.split(",");
        String[] questLatLong = questLocation.split(",");
        double maxDistance = 0.39771935315429213;
        double score_1 = Math.sqrt(Math.pow(Double.parseDouble(peerLatLong[0]) -
                Double.parseDouble(questLatLong[0]), 2)
                + Math.pow(Double.parseDouble(peerLatLong[1]) -
                Double.parseDouble(questLatLong[1]), 2))
                * 10 / maxDistance;
        double score = 10 * Math.exp(-1 * score_1);
        System.out.println("Distance Score: " + score);
        return score;
    }

    private double getInterestScore(List<String> userInterest, String eventInterest) {
        // sort by distance as well
        if (userInterest.contains(eventInterest)) {
            System.out.println("Interest Score: 9");
            return 9;
        } else {
            System.out.println("Interest Score: 3");
            return 3; // might need to change this
        }
    }

    private double getPersonalityScore(String userPersonality, List<Integer> questPersonality) {
        // 0E 1I 2S 3N 4T 5F 6J 7P
        double score = 0;
        double total_participants = questPersonality.get(0) +
                questPersonality.get(1);
        for (int i = 0; i < userPersonality.length(); i++) {
            char c = userPersonality.charAt(i);
            switch (c) {
                case 'E':
                    score += ((double) questPersonality.get(0)) / total_participants;
                    break;
                case 'I':
                    score += ((double) questPersonality.get(1)) / total_participants;
                    break;
                case 'S':
                    score += ((double) questPersonality.get(2)) / total_participants;
                    break;
                case 'N':
                    score += ((double) questPersonality.get(3)) / total_participants;
                    break;
                case 'T':
                    score += ((double) questPersonality.get(4)) / total_participants;
                    break;
                case 'F':
                    score += ((double) questPersonality.get(5)) / total_participants;
                    break;
                case 'J':
                    score += ((double) questPersonality.get(6)) / total_participants;
                    break;
                case 'P':
                    score += ((double) questPersonality.get(7)) / total_participants;
                    break;
                default:
                    System.out.println("Error in personality scoring");
            }

        }
        score = score * 10 / 4;
        System.out.println("Personality Score: " + score);
        return score;
    }

    private PeerEntity getPeerFromHttpRequest(HttpServletRequest httpRequest) {
        String token = jwtUtil.getJwtFromCookies(httpRequest);
        String username = jwtUtil.getUserNameFromJwtToken(token);
        Optional<PeerLoginEntity> peerLoginEntity = peerLoginRepository.findByUsername(username);
        if (peerLoginEntity.isEmpty()) {
            return null;
        }
        Optional<PeerEntity> peerEntity = peerRepository.findById(
                new PeerEntity.Key(peerLoginEntity.get().getPeerId()));
        return peerEntity.orElse(null);
    }
}
