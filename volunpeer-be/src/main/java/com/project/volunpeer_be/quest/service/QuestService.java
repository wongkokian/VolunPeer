package com.project.volunpeer_be.quest.service;

import com.project.volunpeer_be.quest.dto.request.PeerQuestShiftRequest;
import com.project.volunpeer_be.quest.dto.request.QuestCreateRequest;
import com.project.volunpeer_be.quest.dto.request.QuestDetailsRequest;
import com.project.volunpeer_be.quest.dto.response.*;

import jakarta.servlet.http.HttpServletRequest;

public interface QuestService {
    QuestCreateResponse createQuest(QuestCreateRequest request);

    QuestDetailsResponse getQuestDetails(QuestDetailsRequest request);

    QuestListResponse getAllQuests(HttpServletRequest httpServletRequest);

    PeerQuestShiftResponse assignQuestShift(PeerQuestShiftRequest request, HttpServletRequest httpServletRequest);

    UpcomingQuestListResponse getUpcomingQuests(HttpServletRequest httpRequest);
}
