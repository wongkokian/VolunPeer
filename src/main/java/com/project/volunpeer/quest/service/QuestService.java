package com.project.volunpeer.quest.service;

import com.project.volunpeer.quest.dto.request.QuestCreateRequest;
import com.project.volunpeer.quest.dto.request.QuestDetailsRequest;
import com.project.volunpeer.quest.dto.response.QuestCreateResponse;
import com.project.volunpeer.quest.dto.response.QuestDetailsResponse;

public interface QuestService {
    QuestCreateResponse createQuest(QuestCreateRequest request);

    QuestDetailsResponse getQuestDetails(QuestDetailsRequest request);
}
