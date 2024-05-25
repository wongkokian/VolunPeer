package com.project.volunpeer_be.peer.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.volunpeer_be.common.dto.response.BaseResponse;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PeerDetailsResponse extends BaseResponse {
    private String name;
    private List<String> interests;
    private String location;
    private List<String> connections;
    private String personality;
}
