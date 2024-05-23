package com.project.volunpeer.quest.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class QuestCreateRequest {
    private String orgId;
    private String title;
    private String description;
    private List<String> relevantInterests;
    private String contactName;
    private String contactNum;
    private String contactEmail;
}
