package com.project.volunpeer_be.auth.service.impl;

import com.project.volunpeer_be.auth.dto.request.LoginRequest;
import com.project.volunpeer_be.auth.dto.response.LoginResponse;
import com.project.volunpeer_be.auth.dto.response.LogoutResponse;
import com.project.volunpeer_be.auth.service.AuthService;
import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.security.jwt.JwtUtil;
import com.project.volunpeer_be.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(userDetails);

        String role = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()).getFirst();

        LoginResponse response = new LoginResponse(role);
        response.setStatusCode(StatusCode.SUCCESS);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }

    @Override
    public ResponseEntity<LogoutResponse> logout() {
        ResponseCookie jwtCookie = jwtUtil.getCleanJwtCookie();
        LogoutResponse response = new LogoutResponse();
        response.setStatusCode(StatusCode.SUCCESS);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }
}
