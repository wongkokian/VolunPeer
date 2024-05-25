package com.project.volunpeer_be.quest.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.volunpeer_be.common.dto.response.BaseResponse;
import com.project.volunpeer_be.quest.dto.QuestShift;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class QuestDetailsResponse extends BaseResponse {
    private String orgName;
    private String title;
    private String description;
    private String relevantInterest;
    private String locationCoordinates;
    private String locationName;
    private String contactName;
    private Integer contactNum;
    private String contactEmail;
    private Integer numRegistered;
    private String imageUrl;

    private List<QuestShift> questShifts;
}
