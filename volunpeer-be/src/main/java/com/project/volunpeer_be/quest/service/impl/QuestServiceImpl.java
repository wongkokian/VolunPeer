package com.project.volunpeer_be.quest.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.volunpeer_be.common.enums.KeyType;
import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.common.util.CommonUtil;
import com.project.volunpeer_be.common.util.KeyGeneratorUtil;
import com.project.volunpeer_be.db.entity.QuestEntity;
import com.project.volunpeer_be.db.entity.QuestShiftEntity;
import com.project.volunpeer_be.db.repository.QuestRepository;
import com.project.volunpeer_be.db.repository.QuestShiftRepository;
import com.project.volunpeer_be.quest.dto.QuestShift;
import com.project.volunpeer_be.quest.dto.request.QuestCreateRequest;
import com.project.volunpeer_be.quest.dto.request.QuestDetailsRequest;
import com.project.volunpeer_be.quest.dto.response.PeerQuestShiftResponse;
import com.project.volunpeer_be.quest.dto.response.QuestCreateResponse;
import com.project.volunpeer_be.quest.dto.response.QuestDetailsResponse;
import com.project.volunpeer_be.quest.service.QuestService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestServiceImpl implements QuestService {
    @Autowired
    QuestRepository questRepository;

    @Autowired
    QuestShiftRepository questShiftRepository;

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
        questRepository.save(questEntity);

        // Save each quest shift into database
        for (QuestShift questShift : request.getQuestShifts()) {
            QuestShiftEntity questShiftEntity = mapper.convertValue(questShift, QuestShiftEntity.class);
            questShiftEntity.setQuestId(questId);
            questShiftEntity.setId(new QuestShiftEntity.Key(questId, questShift.getShiftNum()));
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

        // Get all quest shifts' details for the particular quest
        List<QuestShiftEntity> questShiftEntities = questShiftRepository.findByQuestId(request.getQuestId());
        for (QuestShiftEntity questShiftEntity : questShiftEntities) {
            QuestShift questShift = mapper.convertValue(questShiftEntity, QuestShift.class);
            response.getQuestShifts().add(questShift);
        }

        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public PeerQuestShiftResponse assignQuestShift(QuestDetailsRequest request, HttpServletRequest httpServletRequest) {
        String peerId = commonUtil.getPeerFromHttpRequest(httpServletRequest).getPeerId();
        PeerQuestShiftResponse response = new PeerQuestShiftResponse();

        // Get Quest details
        Optional<QuestEntity> questEntity = questRepository.findById(new QuestEntity.Key(request.getQuestId()));
        if (questEntity.isEmpty()) {
            response.setStatusCode(StatusCode.QUEST_DOES_NOT_EXIST);
            return response;
        }

        // Get Quest Shift details
        Optional<QuestShiftEntity> questShiftEntity = questShiftRepository.findById(new QuestShiftEntity.Key(request.getQuestId(), request.getShiftNum()));
        if (questShiftEntity.isEmpty()) {
            response.setStatusCode(StatusCode.QUEST_SHIFT_DOES_NOT_EXIST);
            return response;
        }

        // Assign Quest Shift to Peer
        QuestShiftEntity questShiftEntity1 = questShiftEntity.get();
        questShiftEntity1.setPeerId(request.getPeerId());
        questShiftRepository.save(questShiftEntity1);

        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

}
