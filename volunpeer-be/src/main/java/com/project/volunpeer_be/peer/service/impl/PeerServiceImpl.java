package com.project.volunpeer_be.peer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.volunpeer_be.common.enums.KeyType;
import com.project.volunpeer_be.common.enums.Role;
import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.common.util.KeyGeneratorUtil;
import com.project.volunpeer_be.common.util.CommonUtil;
import com.project.volunpeer_be.db.entity.LoginEntity;
import com.project.volunpeer_be.db.entity.PeerEntity;
import com.project.volunpeer_be.db.entity.PeerLoginEntity;
import com.project.volunpeer_be.db.repository.PeerLoginRepository;
import com.project.volunpeer_be.db.repository.PeerRepository;
import com.project.volunpeer_be.peer.dto.request.PeerAddPersonalityInterestRequest;
import com.project.volunpeer_be.peer.dto.request.PeerCreateRequest;
import com.project.volunpeer_be.peer.dto.response.PeerAddPersonalityInterestResponse;
import com.project.volunpeer_be.peer.dto.response.PeerCreateResponse;
import com.project.volunpeer_be.peer.dto.response.PeerDetailsResponse;
import com.project.volunpeer_be.peer.enums.Interest;
import com.project.volunpeer_be.peer.enums.Personality;
import com.project.volunpeer_be.peer.service.PeerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class PeerServiceImpl implements PeerService {
    @Autowired
    PeerRepository peerRepository;

    @Autowired
    PeerLoginRepository peerLoginRepository;

    @Autowired
    KeyGeneratorUtil keyGen;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CommonUtil commonUtil;

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
        peerEntity.setConnections(new HashSet<>());
        peerEntity.setSentConnectionRequests(new HashSet<>());
        peerEntity.setReceivedConnectionRequests(new HashSet<>());

        // Initialize points arraylist with with 23 zeros
        Integer[] pointsArray = new Integer[23];
        Arrays.fill(pointsArray, 0);
        peerEntity.setPoints(Arrays.asList(pointsArray));

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

        PeerEntity peerEntity = commonUtil.getPeerFromHttpRequest(httpRequest);
        if (peerEntity == null) {
            response.setStatusCode(StatusCode.USER_DOES_NOT_EXIST);
            return response;
        }

        response = mapper.convertValue(peerEntity, PeerDetailsResponse.class);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public PeerAddPersonalityInterestResponse addPeerPersonalityInterest(PeerAddPersonalityInterestRequest request, HttpServletRequest httpRequest) {
        PeerAddPersonalityInterestResponse response = new PeerAddPersonalityInterestResponse();
        if (!Personality.isValidPersonality(request.getPersonality())) {
            response.setStatusCode(StatusCode.INVALID_PERSONALITY);
            return response;
        }

        for (String interest : request.getInterests()) {
            if (!Interest.isValidInterest(interest)) {
                response.setStatusCode(StatusCode.INVALID_INTEREST);
                return response;
            }
        }

        PeerEntity peerEntity = commonUtil.getPeerFromHttpRequest(httpRequest);
        if (peerEntity == null) {
            response.setStatusCode(StatusCode.USER_DOES_NOT_EXIST);
            return response;
        }

        peerEntity.setPersonality(request.getPersonality());
        peerEntity.setInterests(request.getInterests());
        peerRepository.save(peerEntity);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }
}
