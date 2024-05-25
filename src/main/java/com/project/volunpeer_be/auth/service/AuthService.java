package com.project.volunpeer_be.auth.service;

import com.project.volunpeer_be.auth.dto.request.LoginRequest;
import com.project.volunpeer_be.auth.dto.response.LoginResponse;
import com.project.volunpeer_be.auth.dto.response.LogoutResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<LoginResponse> login(LoginRequest request);

    ResponseEntity<LogoutResponse> logout();
}
