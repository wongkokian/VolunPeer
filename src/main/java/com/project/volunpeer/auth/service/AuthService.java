package com.project.volunpeer.auth.service;

import com.project.volunpeer.auth.dto.request.LoginRequest;
import com.project.volunpeer.auth.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<LoginResponse> login(LoginRequest request);
}
