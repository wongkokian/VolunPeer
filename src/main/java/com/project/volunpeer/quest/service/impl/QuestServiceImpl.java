package com.project.volunpeer.quest.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.volunpeer.common.enums.KeyType;
import com.project.volunpeer.common.enums.StatusCode;
import com.project.volunpeer.common.util.KeyGeneratorUtil;
import com.project.volunpeer.db.entity.QuestEntity;
import com.project.volunpeer.db.repository.QuestRepository;
import com.project.volunpeer.quest.dto.request.QuestCreateRequest;
import com.project.volunpeer.quest.dto.request.QuestDetailsRequest;
import com.project.volunpeer.quest.dto.response.QuestCreateResponse;
import com.project.volunpeer.quest.dto.response.QuestDetailsResponse;
import com.project.volunpeer.quest.service.QuestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestServiceImpl implements QuestService {
    @Autowired
    QuestRepository questRepository;

    @Autowired
    KeyGeneratorUtil keyGen;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public QuestCreateResponse createQuest(QuestCreateRequest request) {
        QuestCreateResponse response = new QuestCreateResponse();

        String questId = keyGen.generateKey(KeyType.QUEST);

        QuestEntity questEntity = mapper.convertValue(request, QuestEntity.class);
        questEntity.setId(new QuestEntity.Key(questId));
        questEntity.setQuestId(questId);
        questRepository.save(questEntity);

        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public QuestDetailsResponse getQuestDetails(QuestDetailsRequest request) {
        QuestDetailsResponse response = new QuestDetailsResponse();

        Optional<QuestEntity> questEntity = questRepository.findById(new QuestEntity.Key(request.getQuestId()));
        if (questEntity.isPresent()) {
            response = mapper.convertValue(questEntity.get(), QuestDetailsResponse.class);
            response.setStatusCode(StatusCode.SUCCESS);
        } else {
            response.setStatusCode(StatusCode.QUEST_DOES_NOT_EXIST);
        }

        return response;
    }
}
