package com.project.volunpeer.common.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    SUCCESS("Success", 200),
    FAILURE("Failure", 500);

    private final String status;
    private final Integer code;

    StatusCode(String status, Integer code) {
        this.status = status;
        this.code = code;
    }
}
