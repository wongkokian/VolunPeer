package com.project.volunpeer_be.quest.controller;

import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.quest.dto.request.PeerQuestShiftRequest;
import com.project.volunpeer_be.quest.dto.request.QuestCreateRequest;
import com.project.volunpeer_be.quest.dto.request.QuestDetailsRequest;
import com.project.volunpeer_be.quest.dto.response.QuestCreateResponse;
import com.project.volunpeer_be.quest.dto.response.QuestDetailsResponse;
import com.project.volunpeer_be.quest.dto.response.PeerQuestShiftResponse;
import com.project.volunpeer_be.quest.service.QuestService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quest")
public class QuestController {
    @Autowired
    QuestService questService;

    @PostMapping("/create")
    public QuestCreateResponse createQuest(@RequestBody QuestCreateRequest request) {
        QuestCreateResponse response = new QuestCreateResponse();
        try {
            response = questService.createQuest(request);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @PostMapping("/details")
    public QuestDetailsResponse getQuestDetails(@RequestBody QuestDetailsRequest request) {
        QuestDetailsResponse response = new QuestDetailsResponse();
        try {
            response = questService.getQuestDetails(request);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @PostMapping("/quest-shift-assign")
    public PeerQuestShiftResponse assignQuestShift(@RequestBody PeerQuestShiftRequest request, HttpServletRequest httpServletRequest) {
        PeerQuestShiftResponse response = new PeerQuestShiftResponse();
        try {
            response = questService.assignQuestShift(request, httpServletRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }
}
