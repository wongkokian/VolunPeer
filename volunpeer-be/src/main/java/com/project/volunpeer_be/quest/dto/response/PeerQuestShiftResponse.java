package com.project.volunpeer_be.quest.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.volunpeer_be.common.dto.response.BaseResponse;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PeerQuestShiftResponse extends BaseResponse {
}
