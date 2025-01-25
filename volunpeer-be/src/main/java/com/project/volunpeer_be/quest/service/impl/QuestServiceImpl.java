package com.project.volunpeer_be.quest.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.volunpeer_be.common.enums.KeyType;
import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.common.util.CommonUtil;
import com.project.volunpeer_be.common.util.KeyGeneratorUtil;
import com.project.volunpeer_be.db.entity.OrganisationEntity;
import com.project.volunpeer_be.db.entity.PeerEntity;
import com.project.volunpeer_be.db.entity.PeerQuestShiftEntity;
import com.project.volunpeer_be.db.entity.QuestEntity;
import com.project.volunpeer_be.db.repository.OrganisationRepository;
import com.project.volunpeer_be.db.repository.PeerQuestShiftRepository;
import com.project.volunpeer_be.db.repository.PeerRepository;
import com.project.volunpeer_be.db.entity.QuestShiftEntity;
import com.project.volunpeer_be.db.repository.QuestRepository;
import com.project.volunpeer_be.db.repository.QuestShiftRepository;
import com.project.volunpeer_be.quest.dto.Quest;
import com.project.volunpeer_be.quest.dto.QuestShift;
import com.project.volunpeer_be.quest.dto.UpcomingQuest;
import com.project.volunpeer_be.quest.dto.UpcomingShift;
import com.project.volunpeer_be.quest.dto.request.PeerQuestShiftRequest;
import com.project.volunpeer_be.quest.dto.request.QuestCreateRequest;
import com.project.volunpeer_be.quest.dto.request.QuestDetailsRequest;
import com.project.volunpeer_be.quest.dto.response.*;
import com.project.volunpeer_be.quest.service.QuestService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class QuestServiceImpl implements QuestService {
    @Autowired
    QuestRepository questRepository;

    @Autowired
    PeerQuestShiftRepository peerQuestShiftRepository;

    @Autowired
    PeerRepository peerRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    QuestShiftRepository questShiftRepository;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    KeyGeneratorUtil keyGen;

    ObjectMapper mapper = new ObjectMapper();

    private static final double EARTH_RADIUS_METERS = 6371000;

    @Override
    public QuestCreateResponse createQuest(QuestCreateRequest request) {
        QuestCreateResponse response = new QuestCreateResponse();

        String questId = keyGen.generateKey(KeyType.QUEST);

        // Save Quest into database
        QuestEntity questEntity = mapper.convertValue(request, QuestEntity.class);
        questEntity.setId(new QuestEntity.Key(questId));
        questEntity.setQuestId(questId);
        questEntity.setMbtiTypes(new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0)));
        questRepository.save(questEntity);

        // Save each quest shift into database
        for (QuestShift questShift : request.getQuestShifts()) {
            QuestShiftEntity questShiftEntity = mapper.convertValue(questShift, QuestShiftEntity.class);
            questShiftEntity.setQuestId(questId);
            questShiftEntity.setId(new QuestShiftEntity.Key(questId, questShift.getShiftNum()));
            questShiftEntity.setStartDateTime(questShift.getStartDateTime());
            questShiftEntity.setEndDateTime(questShift.getEndDateTime());
            questShiftRepository.save(questShiftEntity);
        }

        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public QuestDetailsResponse getQuestDetails(QuestDetailsRequest request) {
        QuestDetailsResponse response = new QuestDetailsResponse();

        // Get Quest details
        Optional<QuestEntity> questEntity = questRepository.findById(new QuestEntity.Key(request.getQuestId()));
        if (questEntity.isEmpty()) {
            response.setStatusCode(StatusCode.QUEST_DOES_NOT_EXIST);
            return response;
        }

        response = mapper.convertValue(questEntity.get(), QuestDetailsResponse.class);

        // Process org name
        Optional<OrganisationEntity> org = organisationRepository.findById(new OrganisationEntity.Key(questEntity.get().getOrgId()));
        if (org.isEmpty()) {
            response.setStatusCode(StatusCode.ORGANISATION_DOES_NOT_EXIST);
            return response;
        }
        response.setOrgName(org.get().getName());

        List<QuestShift> questShifts = new ArrayList<>();

        // Get all quest shifts' details for the particular quest
        List<QuestShiftEntity> questShiftEntities = questShiftRepository.findByQuestId(request.getQuestId());
        for (QuestShiftEntity questShiftEntity : questShiftEntities) {
            QuestShift questShift = mapper.convertValue(questShiftEntity, QuestShift.class);

            //set date
            DateTimeFormatter outputDateFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy");
            LocalDateTime dateTime = LocalDateTime.parse(questShiftEntity.getStartDateTime());
            questShift.setDate(dateTime.format(outputDateFormatter));

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");

            //set start time
            LocalDateTime localStartDateTime = LocalDateTime.parse(questShiftEntity.getStartDateTime());
            String startDateTime = localStartDateTime.format(outputFormatter);
            questShift.setStartDateTime(startDateTime);

            // set end time
            LocalDateTime localEndDateTime = LocalDateTime.parse(questShiftEntity.getEndDateTime());
            String endDateTime = localEndDateTime.format(outputFormatter);
            questShift.setEndDateTime(endDateTime);

            questShifts.add(questShift);
        }

        response.setQuestShifts(questShifts);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public QuestListResponse getAllQuests(HttpServletRequest httpServletRequest) {
        String peerId = commonUtil.getPeerFromHttpRequest(httpServletRequest).getPeerId();
        QuestListResponse response = new QuestListResponse();

        Optional<PeerEntity> peer = peerRepository.findById(new PeerEntity.Key(peerId));
        if (peer.isEmpty()) {
            response.setStatusCode(StatusCode.USER_DOES_NOT_EXIST);
            return response;
        }
        String peerLocation = peer.get().getLocationCoordinates();

        List<Quest> quests = new ArrayList<>();

        // Get all quests and process each fields we need in the response
        List<QuestEntity> questEntities = questRepository.findAll();
        for (QuestEntity questEntity : questEntities) {

            // Calculate distance from user
            Quest quest = mapper.convertValue(questEntity, Quest.class);
            double distance = getDistanceInKM(peerLocation, questEntity.getLocationCoordinates());
            String distanceStr = String.format("%.2f", distance);
            quest.setDistance(distanceStr);

            // Process org name
            Optional<OrganisationEntity> org = organisationRepository.findById(new OrganisationEntity.Key(questEntity.getOrgId()));
            if (org.isEmpty()) {
                response.setStatusCode(StatusCode.ORGANISATION_DOES_NOT_EXIST);
                return response;
            }
            quest.setOrgName(org.get().getName());

            // Process number of peers registered
            Integer numPeers = 0;
            numPeers += questEntity.getMbtiTypes().get(0) + questEntity.getMbtiTypes().get(1);
            quest.setNumRegistered(String.valueOf(numPeers));

            // Process overall start datetime and end datetime after getting all quest shifts
            List<QuestShiftEntity> questShiftEntities = questShiftRepository.findByQuestId(questEntity.getQuestId());
            String startDateTime = null;
            String endDateTime = null;

            for (QuestShiftEntity questShiftEntity : questShiftEntities) {

                LocalDateTime start = LocalDateTime.parse(questShiftEntity.getStartDateTime());
                LocalDateTime end = LocalDateTime.parse(questShiftEntity.getEndDateTime());
                if (startDateTime == null || start.isBefore(LocalDateTime.parse(startDateTime))) {
                    startDateTime = questShiftEntity.getStartDateTime();
                }
                if (endDateTime == null || end.isAfter(LocalDateTime.parse(endDateTime))) {
                    endDateTime = questShiftEntity.getEndDateTime();
                }
            }
            // Parse start and end datetime to "SAT, APR 25 2024 4.00PM" format
            startDateTime = formatDateTimeResponse(startDateTime);
            endDateTime = formatDateTimeResponse(endDateTime);

            quest.setStartDateTime(startDateTime);
            quest.setEndDateTime(endDateTime);


            quests.add(quest);
        }
        response.setQuests(quests);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    private double getDistanceInKM(String peerLocation, String questLocation) {
        String[] peerLatLong = peerLocation.split(",");
        String[] questLatLong = questLocation.split(",");

        double peerLat = Double.parseDouble(peerLatLong[0]);
        double peerLong = Double.parseDouble(peerLatLong[1]);
        double questLat = Double.parseDouble(questLatLong[0]);
        double questLong = Double.parseDouble(questLatLong[1]);

        return haversineDistance(peerLat, peerLong, questLat, questLong) / 1000;
    }

    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_METERS * c;
    }

    private String formatDateTimeResponse(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy h.mma");
        return localDateTime.format(outputFormatter);
    }

    @Override
    public PeerQuestShiftResponse assignQuestShift(PeerQuestShiftRequest request, HttpServletRequest httpServletRequest) {
        String peerId = commonUtil.getPeerFromHttpRequest(httpServletRequest).getPeerId();
        PeerQuestShiftResponse response = new PeerQuestShiftResponse();

        // Update PeerQuestShiftEntity and Quest-Shift collections
        for(Integer shiftNum : request.getShiftNums()) {
            // For each shifts the user selected, add one PeerQuestShift entry into the DB            
            PeerQuestShiftEntity peerQuestShiftEntity = new PeerQuestShiftEntity();
            peerQuestShiftEntity.setQuestId(request.getQuestId());
            peerQuestShiftEntity.setPeerId(peerId);
            peerQuestShiftEntity.setShiftNum(shiftNum);
            peerQuestShiftEntity.setId(new PeerQuestShiftEntity.Key(peerId, request.getQuestId(), shiftNum));
    
            peerQuestShiftRepository.save(peerQuestShiftEntity);

            // Also update the slots filled for the quest-shift entry involved
            Optional<QuestShiftEntity> questShiftEntity = questShiftRepository.findById(new QuestShiftEntity.Key(request.getQuestId(), shiftNum));
            if (questShiftEntity.isEmpty()) {
                response.setStatusCode(StatusCode.QUEST_SHIFT_DOES_NOT_EXIST);
                return response;
            }
            questShiftEntity.get().setFilledSlots(questShiftEntity.get().getFilledSlots() + 1);
            questShiftRepository.save(questShiftEntity.get());
        }

        // Update QuestEntity with a new set of MBTI scores, which will require the relevant quest and peer entities
        Optional<QuestEntity> quest = questRepository.findById(new QuestEntity.Key(request.getQuestId()));
        if (quest.isEmpty()) {
            response.setStatusCode(StatusCode.QUEST_DOES_NOT_EXIST);
            return response;
        }
        Optional<PeerEntity> peer = peerRepository.findById(new PeerEntity.Key(peerId));
        if (peer.isEmpty()) {
            response.setStatusCode(StatusCode.USER_DOES_NOT_EXIST);
            return response;
        }

        // Update the quest's MBTI scores
        List<Integer> currScores = quest.get().getMbtiTypes();
        String personality = peer.get().getPersonality();
        for (char c : personality.toCharArray()) {
            switch (c) {
                case 'E':
                    currScores.set(0, currScores.get(0) + 1);
                    break;
                case 'I':
                    currScores.set(1, currScores.get(1) + 1);
                    break;
                case 'S':
                    currScores.set(2, currScores.get(2) + 1);
                    break;
                case 'N':
                    currScores.set(3, currScores.get(3) + 1);
                    break;
                case 'T':
                    currScores.set(4, currScores.get(4) + 1);
                    break;
                case 'F':
                    currScores.set(5, currScores.get(5) + 1);
                    break;
                case 'J':
                    currScores.set(6, currScores.get(6) + 1);
                    break;
                case 'P':
                    currScores.set(7, currScores.get(7) + 1);
                    break;
            }
        }

        quest.get().setMbtiTypes(currScores);
        questRepository.save(quest.get());

        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public UpcomingQuestListResponse getUpcomingQuests(HttpServletRequest httpRequest) {
        UpcomingQuestListResponse response = new UpcomingQuestListResponse();
        String peerId = commonUtil.getPeerFromHttpRequest(httpRequest).getPeerId();
        PeerEntity peerEntity = commonUtil.getPeerFromPeerId(peerId);
        List<PeerQuestShiftEntity> peerQuestShifts = peerQuestShiftRepository.findByPeerId(peerId);
        HashSet<String> questIds = new HashSet<>();

        for(PeerQuestShiftEntity peerQuestShift : peerQuestShifts) {
            questIds.add(peerQuestShift.getQuestId());
        }

        List<UpcomingQuest> upcomingQuests = new ArrayList<>();
        for(String questId : questIds) {
            Optional<QuestEntity> questEntity = questRepository.findById(new QuestEntity.Key(questId));
            UpcomingQuest upcomingQuest = new UpcomingQuest();
            upcomingQuest.setTitle(questEntity.get().getTitle());
            upcomingQuest.setImageUrl(questEntity.get().getImageUrl());
            upcomingQuest.setLocationName(questEntity.get().getLocationName());
            upcomingQuest.setRelevantInterest(questEntity.get().getRelevantInterest());
            upcomingQuest.setNumRegistered(String.valueOf(questEntity.get().getMbtiTypes().get(0) + questEntity.get().getMbtiTypes().get(1)));

            Optional<OrganisationEntity> organisationEntity = organisationRepository.findById(new OrganisationEntity.Key(questEntity.get().getOrgId()));
            upcomingQuest.setOrgName(organisationEntity.get().getName());

            double distance = getDistanceInKM(peerEntity.getLocationCoordinates(), questEntity.get().getLocationCoordinates());
            String distanceStr = String.format("%.2f", distance);
            upcomingQuest.setDistance(distanceStr);
            
            List<Integer> shiftNums = new ArrayList<>();
            for(PeerQuestShiftEntity peerQuestShift : peerQuestShifts) {
                if(peerQuestShift.getQuestId().equals(questId)) {
                    shiftNums.add(peerQuestShift.getShiftNum());
                }
            }

            List<UpcomingShift> upcomingShifts = new ArrayList<>();
            for(Integer shiftNum : shiftNums) {
                Optional<QuestShiftEntity> questShiftEntity = questShiftRepository.findById(new QuestShiftEntity.Key(questId, shiftNum));
                UpcomingShift shift = new UpcomingShift();

                //set date
                DateTimeFormatter outputDateFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy");
                LocalDateTime dateTime = LocalDateTime.parse(questShiftEntity.get().getStartDateTime());
                shift.setDate(dateTime.format(outputDateFormatter));

                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");

                //set start time
                LocalDateTime localStartDateTime = LocalDateTime.parse(questShiftEntity.get().getStartDateTime());
                String startDateTime = localStartDateTime.format(outputFormatter);
                shift.setStartDateTime(startDateTime);

                // set end time
                LocalDateTime localEndDateTime = LocalDateTime.parse(questShiftEntity.get().getEndDateTime());
                String endDateTime = localEndDateTime.format(outputFormatter);
                shift.setEndDateTime(endDateTime);
                upcomingShifts.add(shift);
            }
            upcomingQuest.setShifts(upcomingShifts);
            upcomingQuests.add(upcomingQuest);
        }
        response.setUpcomingQuests(upcomingQuests);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }
}
