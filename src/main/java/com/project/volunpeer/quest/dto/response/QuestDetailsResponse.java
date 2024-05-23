package com.project.volunpeer.quest.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.volunpeer.common.dto.response.BaseResponse;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class QuestDetailsResponse extends BaseResponse {
    private String orgId;
    private String title;
    private String description;
    private List<String> relevantInterests;
    private String contactName;
    private String contactNum;
    private String contactEmail;
    private List<Integer> mbtiTypes;
    private int numRegistered;
}
