package com.project.volunpeer_be.common.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    SUCCESS("Success", 200),
    FAILURE("Failure", 500),
    USERNAME_TAKEN("Username taken", 501),
    USER_DOES_NOT_EXIST("User does not exist", 502),
    QUEST_DOES_NOT_EXIST("Quest does not exist", 503),
    INVALID_PERSONALITY("Invalid personality", 504),
    INVALID_INTEREST("Invalid interest", 505),
    ORGANISATION_DOES_NOT_EXIST("Organisation does not exist", 506);

    private final String status;
    private final Integer code;

    StatusCode(String status, Integer code) {
        this.status = status;
        this.code = code;
    }
}
