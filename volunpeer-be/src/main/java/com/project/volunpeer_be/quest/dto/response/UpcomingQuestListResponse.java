package com.project.volunpeer_be.quest.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.volunpeer_be.common.dto.response.BaseResponse;
import com.project.volunpeer_be.quest.dto.Quest;
import com.project.volunpeer_be.quest.dto.UpcomingQuest;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UpcomingQuestListResponse extends BaseResponse {
    private List<UpcomingQuest> upcomingQuests;
}
