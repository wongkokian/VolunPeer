package com.project.volunpeer_be.peer.enums;

import lombok.Getter;

@Getter
public enum Personality {
    ISTJ("ISTJ"),
    ISFJ("ISFJ"),
    ESTJ("ESTJ"),
    ESFJ("ESFJ"),
    ISTP("ISTP"),
    ESTP("ESTP"),
    ISFP("ISFP"),
    ESFP("ESFP"),
    ENTJ("ENTJ"),
    ENTP("ENTP"),
    INFP("INFP"),
    INFJ("INFJ"),
    ENFP("ENFP"),
    ENFJ("ENFJ"),
    INTP("INTP"),
    INTJ("INTJ");

    private final String name;

    Personality(String name) {
        this.name = name;
    }

    public static Boolean isValidPersonality(String name) {
        for (Personality personality : Personality.values()) {
            if (name.equals(personality.getName())) {
                return true;
            }
        }
        return false;
    }
}
