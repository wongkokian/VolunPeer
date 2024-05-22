package com.project.volunpeer.auth.controller;

import com.project.volunpeer.auth.dto.request.LoginRequest;
import com.project.volunpeer.auth.dto.response.LoginResponse;
import com.project.volunpeer.auth.dto.response.LogoutResponse;
import com.project.volunpeer.auth.service.AuthService;
import com.project.volunpeer.common.enums.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/logout")
    public ResponseEntity<LogoutResponse> login() {
        ResponseEntity<LogoutResponse> response;
        try {
            response = authService.logout();
        } catch (Exception e) {
            LogoutResponse logoutResponse = new LogoutResponse();
            logoutResponse.setStatusCode(StatusCode.FAILURE);
            response = ResponseEntity.badRequest().body(logoutResponse);
        }
        return response;
    }
}
