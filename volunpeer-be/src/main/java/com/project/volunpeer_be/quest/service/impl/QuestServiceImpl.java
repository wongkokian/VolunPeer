package com.project.volunpeer_be.quest.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.volunpeer_be.common.enums.KeyType;
import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.common.util.CommonUtil;
import com.project.volunpeer_be.common.util.KeyGeneratorUtil;
import com.project.volunpeer_be.db.entity.PeerEntity;
import com.project.volunpeer_be.db.entity.PeerQuestShiftEntity;
import com.project.volunpeer_be.db.entity.QuestEntity;
import com.project.volunpeer_be.db.repository.PeerQuestShiftRepository;
import com.project.volunpeer_be.db.repository.PeerRepository;
import com.project.volunpeer_be.db.repository.QuestRepository;
import com.project.volunpeer_be.quest.dto.request.PeerQuestShiftRequest;
import com.project.volunpeer_be.quest.dto.request.QuestCreateRequest;
import com.project.volunpeer_be.quest.dto.request.QuestDetailsRequest;
import com.project.volunpeer_be.quest.dto.response.PeerQuestShiftResponse;
import com.project.volunpeer_be.quest.dto.response.QuestCreateResponse;
import com.project.volunpeer_be.quest.dto.response.QuestDetailsResponse;
import com.project.volunpeer_be.quest.service.QuestService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    CommonUtil commonUtil;

    @Autowired
    KeyGeneratorUtil keyGen;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public QuestCreateResponse createQuest(QuestCreateRequest request) {
        QuestCreateResponse response = new QuestCreateResponse();

        String questId = keyGen.generateKey(KeyType.QUEST);

        // Save Quest into database
        QuestEntity questEntity = mapper.convertValue(request, QuestEntity.class);
        questEntity.setId(new QuestEntity.Key(questId));
        questEntity.setQuestId(questId);
        questEntity.setQuestShifts(request.getQuestShifts());
        questRepository.save(questEntity);

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
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public PeerQuestShiftResponse assignQuestShift(PeerQuestShiftRequest request, HttpServletRequest httpServletRequest) {
        String peerId = commonUtil.getPeerFromHttpRequest(httpServletRequest).getPeerId();
        PeerQuestShiftResponse response = new PeerQuestShiftResponse();

        Optional<QuestEntity> quest = questRepository.findById(new QuestEntity.Key(request.getQuestId()));
        if (quest.isEmpty()) {
            response.setStatusCode(StatusCode.QUEST_DOES_NOT_EXIST);
            return response;
        }

        // For each shifts the user selected, add one PeerQuestShift entry into the DB, and update the 
        for(Integer shiftNum : request.getShiftNums()) {
            PeerQuestShiftEntity peerQuestShiftEntity = new PeerQuestShiftEntity();
            peerQuestShiftEntity.setQuestId(request.getQuestId());
            peerQuestShiftEntity.setPeerId(peerId);
            peerQuestShiftEntity.setShiftNum(shiftNum);
    
            peerQuestShiftRepository.save(peerQuestShiftEntity);
        }

        // Update QuestEntity with a new set of MBTI scores and update slots filled for involved quest shifts in the Quest entry
        Optional<PeerEntity> peer = peerRepository.findById(new PeerEntity.Key(peerId));
        if (peer.isEmpty()) {
            response.setStatusCode(StatusCode.USER_DOES_NOT_EXIST);
            return response;
        }

        String personality = peer.get().getPersonality();
        Integer[] scores = new Integer[8];
        for (char c : personality.toCharArray()) {
            switch (c) {
                case 'I':
                    scores[0] = scores[0] + 1;
                    break;
                case 'E':
                    scores[1] = scores[1] + 1;
                    break;
                case 'N':
                    scores[2] = scores[2] + 1;
                    break;
                case 'S':
                    scores[3] = scores[3] + 1;
                    break;
                case 'F':
                    scores[4] = scores[4] + 1;
                    break;
                case 'T':
                    scores[5] = scores[5] + 1;
                    break;
                case 'P':
                    scores[6] = scores[6] + 1;
                    break;
                case 'J':
                    scores[7] = scores[7] + 1;
                    break;
                default:
                    break;
            }
        }


        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

}
