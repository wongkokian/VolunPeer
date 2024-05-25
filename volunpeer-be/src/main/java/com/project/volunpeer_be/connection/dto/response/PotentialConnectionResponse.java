package com.project.volunpeer_be.connection.dto.response;

import com.project.volunpeer_be.common.dto.response.BaseResponse;
import com.project.volunpeer_be.connection.dto.PotentialConnectionShift;
import lombok.Data;

import java.util.List;

@Data
public class PotentialConnectionResponse extends BaseResponse {
    private List<PotentialConnectionShift> shifts;
}
