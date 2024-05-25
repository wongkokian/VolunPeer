package com.project.volunpeer_be.common.dto.response;

import com.project.volunpeer_be.common.enums.StatusCode;
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
