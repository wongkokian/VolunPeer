package com.project.volunpeer.auth.controller;

import com.project.volunpeer.auth.dto.request.LoginRequest;
import com.project.volunpeer.auth.dto.response.LoginResponse;
import com.project.volunpeer.auth.service.AuthService;
import com.project.volunpeer.common.enums.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        ResponseEntity<LoginResponse> response;
        try {
            response = authService.login(request);
        } catch (Exception e) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setStatusCode(StatusCode.FAILURE);
            response = ResponseEntity.badRequest().body(loginResponse);
        }
        return response;
    }
}
