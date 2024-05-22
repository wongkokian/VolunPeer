package com.project.volunpeer.auth.dto.response;

import com.project.volunpeer.common.dto.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse extends BaseResponse {
    private String username;
    private String role;
}
