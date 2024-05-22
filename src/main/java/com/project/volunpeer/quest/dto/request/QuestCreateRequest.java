package com.project.volunpeer.quest.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestCreateRequest {
    private String orgId;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<String> relevantInterests;
    private String location;
    private String contactName;
    private String contactNum;
    private String contactEmail;
}
