package com.project.volunpeer.peer.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.volunpeer.common.dto.response.BaseResponse;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PeerCreateResponse extends BaseResponse {
    private String username;
    private String role;
}
