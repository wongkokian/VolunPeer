package com.project.volunpeer.peer.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PeerAddPersonalityRequest {
    private String personality;
    private List<String> interests;
}
