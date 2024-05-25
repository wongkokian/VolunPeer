package com.project.volunpeer_be.connection.dto.response;

import com.project.volunpeer_be.common.dto.response.BaseResponse;
import com.project.volunpeer_be.connection.dto.ConnectionUpcomingQuest;
import lombok.Data;

import java.util.List;

@Data
public class ConnectionUpcomingQuestResponse extends BaseResponse {
    private List<ConnectionUpcomingQuest> quests;
}
