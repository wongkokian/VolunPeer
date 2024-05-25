package com.project.volunpeer_be.connection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionUpcomingQuest {
    private String organisationName;
    private String questTitle;
    private List<ConnectionUpcomingQuestShift> shifts;
}