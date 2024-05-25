package com.project.volunpeer_be.connection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Connection {
    private String peerId;
    private String name;
}
