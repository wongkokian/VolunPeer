package com.project.volunpeer_be.quest.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class PeerQuestShiftRequest {
    private String questId;
    private List<Integer> shiftNums;
}
