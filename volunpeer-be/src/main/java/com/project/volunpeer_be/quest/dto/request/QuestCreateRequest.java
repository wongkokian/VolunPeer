package com.project.volunpeer_be.quest.dto.request;

import java.util.List;

import com.project.volunpeer_be.quest.dto.QuestShift;

import lombok.Data;

@Data
public class QuestCreateRequest {
    private String orgId;
    private String title;
    private String description;
    private String locationCoordinates;
    private String locationName;
    private String relevantInterest;
    private String contactName;
    private String contactNum;
    private String contactEmail;
    private String imageUrl;

    private List<QuestShift> questShifts;
}
