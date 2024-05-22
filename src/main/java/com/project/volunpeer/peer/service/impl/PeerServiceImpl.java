package com.project.volunpeer.peer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.volunpeer.common.enums.KeyType;
import com.project.volunpeer.common.enums.Role;
import com.project.volunpeer.common.enums.StatusCode;
import com.project.volunpeer.common.util.KeyGeneratorUtil;
import com.project.volunpeer.db.entity.LoginEntity;
import com.project.volunpeer.db.entity.PeerEntity;
import com.project.volunpeer.db.entity.PeerLoginEntity;
import com.project.volunpeer.db.repository.PeerLoginRepository;
import com.project.volunpeer.db.repository.PeerRepository;
import com.project.volunpeer.peer.dto.request.PeerAddPersonalityRequest;
import com.project.volunpeer.peer.dto.request.PeerCreateRequest;
import com.project.volunpeer.peer.dto.response.PeerAddPersonalityResponse;
import com.project.volunpeer.peer.dto.response.PeerCreateResponse;
import com.project.volunpeer.peer.dto.response.PeerDetailsResponse;
import com.project.volunpeer.peer.enums.Personality;
import com.project.volunpeer.peer.service.PeerService;
import com.project.volunpeer.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeerServiceImpl implements PeerService {
    @Autowired
    PeerRepository peerRepository;

    @Autowired
    PeerLoginRepository peerLoginRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    KeyGeneratorUtil keyGen;

    @Autowired
    PasswordEncoder passwordEncoder;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public PeerCreateResponse createPeer(PeerCreateRequest request) {
        PeerCreateResponse response = new PeerCreateResponse();
        if (peerLoginRepository.existsByUsername(request.getUsername())) {
            response.setStatusCode(StatusCode.USERNAME_TAKEN);
            return response;
        }

        String peerId = keyGen.generateKey(KeyType.PEER);

        PeerEntity peerEntity = mapper.convertValue(request, PeerEntity.class);
        peerEntity.setId(new PeerEntity.Key(peerId));
        peerEntity.setPeerId(peerId);
        peerRepository.save(peerEntity);

        PeerLoginEntity loginEntity = mapper.convertValue(request, PeerLoginEntity.class);
        loginEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        loginEntity.setId(new LoginEntity.Key(request.getUsername()));
        loginEntity.setPeerId(peerId);
        loginEntity.setRole(Role.PEER.getName());
        peerLoginRepository.save(loginEntity);

        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public PeerDetailsResponse getPeerDetails(HttpServletRequest httpRequest) {
        PeerDetailsResponse response = new PeerDetailsResponse();

        PeerEntity peerEntity = getPeerFromHttpRequest(httpRequest);
        if (peerEntity == null) {
            response.setStatusCode(StatusCode.USER_DOES_NOT_EXIST);
            return response;
        }

        response = mapper.convertValue(peerEntity, PeerDetailsResponse.class);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public PeerAddPersonalityResponse addPeerPersonality(PeerAddPersonalityRequest request, HttpServletRequest httpRequest) {
        PeerAddPersonalityResponse response = new PeerAddPersonalityResponse();
        if (!Personality.isValidPersonality(request.getPersonality())) {
            response.setStatusCode(StatusCode.INVALID_PERSONALITY);
            return response;
        }

        PeerEntity peerEntity = getPeerFromHttpRequest(httpRequest);
        if (peerEntity == null) {
            response.setStatusCode(StatusCode.USER_DOES_NOT_EXIST);
            return response;
        }

        peerEntity.setPersonality(request.getPersonality());
        peerRepository.save(peerEntity);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    private PeerEntity getPeerFromHttpRequest(HttpServletRequest httpRequest) {
        String token = jwtUtil.getJwtFromCookies(httpRequest);
        String username = jwtUtil.getUserNameFromJwtToken(token);
        Optional<PeerLoginEntity> peerLoginEntity = peerLoginRepository.findByUsername(username);
        if (peerLoginEntity.isEmpty()) {
            return null;
        }
        Optional<PeerEntity> peerEntity = peerRepository.findById(
                new PeerEntity.Key(peerLoginEntity.get().getPeerId()));
        return peerEntity.orElse(null);
    }
}
