package com.project.volunpeer_be.connection.dto.response;

import com.project.volunpeer_be.common.dto.response.BaseResponse;
import com.project.volunpeer_be.connection.dto.PotentialConnection;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class PotentialConnectionResponse extends BaseResponse {
    HashMap<PotentialConnection, List<String>> potentialConnections;
}
