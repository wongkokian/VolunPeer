package com.project.volunpeer_be.peer.enums;

import lombok.Getter;

@Getter
public enum Interest {
    ANIMAL_WELFARE(0, "Animal Welfare"),
    ARTS(1, "Arts"),
    CHILDREN(2, "Children"),
    COMMUNITY(3, "Community"),
    CONSERVATION_AND_NATURE(4, "Conservation and Nature"),
    DIGITAL(5, "Digital"),
    DRUG_AWARENESS(6, "Drug Awareness"),
    EDUCATION(7, "Education"),
    ELDERCARE(8, "Eldercare"),
    ENVIRONMENT_AND_WATER(9, "Environment and Water"),
    FAMILIES(10, "Families"),
    HEALTH(11, "Health"),
    HERITAGE(12, "Heritage"),
    HUMANITARIAN(13, "Humanitarian"),
    MENTAL_HEALTH(14, "Mental Health"),
    MIGRANT_WORKERS(15, "Migrant Workers"),
    REHABILITATION_AND_REINTEGRATION(16, "Rehabilitation and Reintegration"),
    SAFETY_AND_SECURITY(17, "Safety & Security"),
    SOCIAL_SERVICES(18, "Social Services"),
    SPECIAL_NEEDS_DISABILITIES(19, "Special Needs/Disabilities"),
    SPORTS(20, "Sports"),
    WOMEN_AND_GIRLS(21, "Women & Girls"),
    YOUTH(22, "Youth");

    private final int index;
    private final String label;

    Interest(int index, String label) {
        this.index = index;
        this.label = label;
    }

    public static Boolean isValidInterest(String name) {
        for (Interest interest : Interest.values()) {
            if (name.equals(interest.getLabel())) {
                return true;
            }
        }
        return false;
    }
}
