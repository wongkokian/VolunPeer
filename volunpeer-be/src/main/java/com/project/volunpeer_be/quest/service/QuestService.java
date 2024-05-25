package com.project.volunpeer_be.quest.service;

import com.project.volunpeer_be.quest.dto.request.QuestCreateRequest;
import com.project.volunpeer_be.quest.dto.request.QuestDetailsRequest;
import com.project.volunpeer_be.quest.dto.response.PeerQuestShiftResponse;
import com.project.volunpeer_be.quest.dto.response.QuestCreateResponse;
import com.project.volunpeer_be.quest.dto.response.QuestDetailsResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface QuestService {
    QuestCreateResponse createQuest(QuestCreateRequest request);

    QuestDetailsResponse getQuestDetails(QuestDetailsRequest request);

    PeerQuestShiftResponse assignQuestShift(QuestDetailsRequest request, HttpServletRequest httpServletRequest);
}
