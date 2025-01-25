package com.project.volunpeer_be.common.enums;

import lombok.Getter;

@Getter
public enum KeyType {
    PEER("PEER"),
    ORG("ORG"),
    GUILD("GUILD"),
    QUEST("QUEST");

    private final String name;

    KeyType(String name) {
        this.name = name;
    }
}
