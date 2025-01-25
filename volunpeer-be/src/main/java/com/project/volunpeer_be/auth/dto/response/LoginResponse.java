package com.project.volunpeer_be.auth.dto.response;

import com.project.volunpeer_be.common.dto.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse extends BaseResponse {
    private String role;
    private String token;
}
