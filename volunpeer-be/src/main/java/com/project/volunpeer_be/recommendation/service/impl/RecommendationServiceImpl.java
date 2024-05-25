package com.project.volunpeer_be.recommendation.service.impl;

import com.project.volunpeer_be.common.util.CommonUtil;
import com.project.volunpeer_be.db.entity.PeerEntity;
import com.project.volunpeer_be.db.entity.QuestEntity;
import com.project.volunpeer_be.db.repository.QuestRepository;
import com.project.volunpeer_be.db.repository.QuestShiftRepository;
import com.project.volunpeer_be.recommendation.dto.Recommendation;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationAllResponse;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationInterestResponse;
import com.project.volunpeer_be.recommendation.dto.response.RecommendationPersonalityResponse;
import com.project.volunpeer_be.recommendation.service.RecommendationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service

public class RecommendationServiceImpl implements RecommendationService {
    @Autowired
    QuestRepository questRepository;
    @Autowired
    QuestShiftRepository questShiftRepository;
    @Autowired
    CommonUtil commonUtil;

    @Override
    public RecommendationAllResponse getAllRecommendation(HttpServletRequest httpRequest) {
        RecommendationAllResponse response = new RecommendationAllResponse();
        PeerEntity peerEntity = commonUtil.getPeerFromHttpRequest(httpRequest);
        List<Recommendation> list_recs = new ArrayList<>();

        // All Quests, need to change to quests that are not completed, maybe a filter
        List<QuestEntity> questEntity = questRepository.findAll();
        questEntity.forEach(quest -> {
            double score = getDistanceScore(peerEntity.getLocationCoordinates(), quest.getLocationCoordinates())
                    + getInterestScore(peerEntity.getInterests(), quest.getRelevantInterest())
                    + getPersonalityScore(peerEntity.getPersonality(), quest.getMbtiTypes());
            score = (score / 3) * 10;
            String string_score = String.valueOf(Math.round(score)) + "%";
            Recommendation recommendation = new Recommendation();
            recommendation.setTitle(quest.getTitle());
            recommendation.setOrgName(commonUtil.getOrganisationFromOrganisationId(quest.getOrgId()).getName());
            recommendation.setLocationName(quest.getLocationName());
            recommendation.setImageUrl(quest.getImageUrl());
            recommendation.setNumRegistered(String.valueOf(
                    Math.round(getNumberOfParticipants(quest.getMbtiTypes()))));
            recommendation.setScore(string_score);
            recommendation.setQuestId(quest.getQuestId());
            List<String> shifts = new ArrayList<>();
            questShiftRepository.findByQuestId(quest.getQuestId()).forEach(shift -> {
                shifts.add(shift.getStartDateTime());
            });
            recommendation.setStartDateTime(formatDateTimeResponse(shifts.get(0)));
            recommendation.setEndDateTime(formatDateTimeResponse(shifts.get(shifts.size() - 1)));
            recommendation.setDouble_score(score);
            recommendation.setDescription(quest.getDescription());
            recommendation.setRelevantInterest(quest.getRelevantInterest());
            list_recs.add(recommendation);
        });
        list_recs.sort(Comparator.comparingDouble(a -> a.getDouble_score()));
        response.setRecommendations(list_recs);
        return response;
    }

    @Override
    public RecommendationInterestResponse getInterestRecommendation(HttpServletRequest httpRequest) {
        RecommendationInterestResponse response = new RecommendationInterestResponse();
        PeerEntity peerEntity = commonUtil.getPeerFromHttpRequest(httpRequest);
        List<Recommendation> list_recs = new ArrayList<>();

        // All Quests, need to change to quests that are not completed, maybe a filter
        List<QuestEntity> questEntity = questRepository.findAll();
        questEntity.forEach(quest -> {
            double score = getInterestScore(peerEntity.getInterests(), quest.getRelevantInterest()) * 10;
            String string_score = String.valueOf(Math.round(score)) + "%";
            Recommendation recommendation = new Recommendation();
            recommendation.setTitle(quest.getTitle());
            recommendation.setOrgName(commonUtil.getOrganisationFromOrganisationId(quest.getOrgId()).getName());
            recommendation.setLocationName(quest.getLocationName());
            List<String> shifts = new ArrayList<>();
            questShiftRepository.findByQuestId(quest.getQuestId()).forEach(shift -> {
                shifts.add(shift.getStartDateTime());
            });
            recommendation.setStartDateTime(formatDateTimeResponse(shifts.get(0)));
            recommendation.setEndDateTime(formatDateTimeResponse(shifts.get(shifts.size() - 1)));
            recommendation.setImageUrl(quest.getImageUrl());
            recommendation.setNumRegistered(String.valueOf(Math.round(getNumberOfParticipants(quest.getMbtiTypes()))));
            recommendation.setScore(string_score);
            recommendation.setQuestId(quest.getQuestId());

            recommendation.setDouble_score(score);
            recommendation.setDescription(quest.getDescription());
            recommendation.setRelevantInterest(quest.getRelevantInterest());
            list_recs.add(recommendation);
        });
        list_recs.sort(Comparator.comparingDouble(a -> a.getDouble_score()));
        response.setRecommendations(list_recs);
        return response;
    }

    @Override
    public RecommendationPersonalityResponse getPersonalityRecommendation(HttpServletRequest httpRequest) {
        RecommendationPersonalityResponse response = new RecommendationPersonalityResponse();
        PeerEntity peerEntity = commonUtil.getPeerFromHttpRequest(httpRequest);
        List<Recommendation> list_recs = new ArrayList<>();

        // All Quests, need to change to quests that are not completed, maybe a filter
        List<QuestEntity> questEntity = questRepository.findAll();
        questEntity.forEach(quest -> {
            double score = getPersonalityScore(peerEntity.getPersonality(), quest.getMbtiTypes()) * 10;
            String string_score = String.valueOf(Math.round(score)) + "%";
            Recommendation recommendation = new Recommendation();
            recommendation.setTitle(quest.getTitle());
            recommendation.setOrgName(commonUtil.getOrganisationFromOrganisationId(quest.getOrgId()).getName());
            recommendation.setLocationName(quest.getLocationName());
            List<String> shifts = new ArrayList<>();
            questShiftRepository.findByQuestId(quest.getQuestId()).forEach(shift -> {
                shifts.add(shift.getStartDateTime());
            });
            recommendation.setStartDateTime(formatDateTimeResponse(shifts.get(0)));
            recommendation.setEndDateTime(formatDateTimeResponse(shifts.get(shifts.size() - 1)));
            recommendation.setImageUrl(quest.getImageUrl());
            recommendation.setNumRegistered(String.valueOf(Math.round(getNumberOfParticipants(quest.getMbtiTypes()))));
            recommendation.setScore(string_score);
            recommendation.setQuestId(quest.getQuestId());

            recommendation.setDouble_score(score);
            recommendation.setDescription(quest.getDescription());
            recommendation.setRelevantInterest(quest.getRelevantInterest());
            list_recs.add(recommendation);
        });
        list_recs.sort(Comparator.comparingDouble(a -> a.getDouble_score()));
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
        double total_participants = getNumberOfParticipants(questPersonality);
        if (total_participants == 0.0) {
            System.out.println("No participants");
            return 5.0;
        }
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

    private String formatDateTimeResponse(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy h.mma");
        return localDateTime.format(outputFormatter);
    }

    private double getNumberOfParticipants(List<Integer> questPersonality) {
        return questPersonality.get(0) + questPersonality.get(1);
    }
}
