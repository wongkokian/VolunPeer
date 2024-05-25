package com.project.volunpeer_be.peer.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PeerAddPersonalityInterestRequest {
    private String personality;
    private List<String> interests;
}
