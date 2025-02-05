package com.project.volunpeer.common.enums;

import lombok.Getter;

@Getter
public enum Role {
    PEER("peer"),
    ORGANISATION("organisation");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
