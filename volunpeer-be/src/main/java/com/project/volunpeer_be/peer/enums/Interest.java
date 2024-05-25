package com.project.volunpeer_be.peer.enums;

import lombok.Getter;

@Getter
public enum Interest {
    ANIMAL("Animal"),
    ARTS("Arts"),
    CHILDREN("Children"),
    COMMUNITY("Community"),
    DIGITAL("Digital"),
    DRUG_AWARENESS("Drug Awareness"),
    EDUCATION("Education"),
    ENVIRONMENT("Environment"),
    ELDERCARE("Eldercare"),
    FAMILIES("Families"),
    HEALTH("Health"),
    HERITAGE("Heritage"),
    HUMANITARIAN("Humanitarian"),
    MENTAL_HEALTH("Mental Health"),
    MIGRANT_WORKERS("Migrant Workers"),
    REHABILITATION_AND_REINTEGRATION("Rehabilitation and Reintegration"),
    SOCIAL_SERVICES("Social Services"),
    SPECIAL_NEEDS_DISABILITIES("Special Needs/Disabilities"),
    SPORTS("Sports"),
    YOUTH("Youth");

    private final String name;

    Interest(String name) {
        this.name = name;
    }

    public static Boolean isValidInterest(String name) {
        for (Interest interest : Interest.values()) {
            if (name.equals(interest.getName())) {
                return true;
            }
        }
        return false;
    }
}
