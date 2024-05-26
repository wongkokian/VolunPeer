package com.project.volunpeer_be.connection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionUpcomingQuest {
    private String peerName;
    private String questTitle;
    private String imageUrl;
    private String startDateTime;
    private String endDateTime;
}