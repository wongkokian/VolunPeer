package com.project.volunpeer_be.connection.dto.response;

import com.project.volunpeer_be.common.dto.response.BaseResponse;
import com.project.volunpeer_be.connection.dto.Connection;
import lombok.Data;

import java.util.List;

@Data
public class ReceivedConnectionListResponse extends BaseResponse {
    private List<Connection> receivedConnections;
}
