package com.project.volunpeer_be.peer.dto.request;

import lombok.Data;

@Data
public class PeerCreateRequest {
    private String username;
    private String password;
    private String name;
    private String location;
}
