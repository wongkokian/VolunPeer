package com.project.volunpeer.peer.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PeerCreateRequest {
    private String username;
    private String password;
    private String name;
    private String bio;
    private List<String> interests;
    private String location;
}
