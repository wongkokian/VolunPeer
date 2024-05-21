package com.project.volunpeer.common.dto.response;

import com.project.volunpeer.common.enums.StatusCode;
import lombok.Data;

@Data
public class BaseResponse {
    private String returnStatus;
    private Integer returnCode;

    public void setStatusCode(StatusCode statusCode) {
        this.returnStatus = statusCode.getStatus();
        this.returnCode = statusCode.getCode();
    }
}
