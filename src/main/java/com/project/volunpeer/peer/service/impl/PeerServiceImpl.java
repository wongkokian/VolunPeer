package com.project.volunpeer.peer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.volunpeer.common.enums.KeyType;
import com.project.volunpeer.common.enums.StatusCode;
import com.project.volunpeer.common.util.KeyGeneratorUtil;
import com.project.volunpeer.db.entity.LoginEntity;
import com.project.volunpeer.db.entity.PeerLoginEntity;
import com.project.volunpeer.db.entity.PeerEntity;
import com.project.volunpeer.db.repository.LoginRepository;
import com.project.volunpeer.db.repository.PeerRepository;
import com.project.volunpeer.peer.dto.request.PeerCreateRequest;
import com.project.volunpeer.peer.dto.request.PeerDetailsRequest;
import com.project.volunpeer.peer.dto.response.PeerCreateResponse;
import com.project.volunpeer.peer.dto.response.PeerDetailsResponse;
import com.project.volunpeer.peer.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeerServiceImpl implements PeerService {
    @Autowired
    PeerRepository peerRepository;

    @Autowired
    LoginRepository loginRepository;

    KeyGeneratorUtil keyGen;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public PeerCreateResponse createPeer(PeerCreateRequest request) {
        PeerCreateResponse response = new PeerCreateResponse();
        if (loginRepository.existsByUsername(request.getUsername())) {
            response.setStatusCode(StatusCode.USERNAME_TAKEN);
            return response;
        }

        String peerId = keyGen.generateKey(KeyType.PEER);

        PeerEntity peerEntity = mapper.convertValue(request, PeerEntity.class);
        peerEntity.setId(new PeerEntity.Key(peerId));
        peerEntity.setPeerId(peerId);
        peerRepository.save(peerEntity);

        PeerLoginEntity loginEntity = mapper.convertValue(request, PeerLoginEntity.class);
        loginEntity.setId(new LoginEntity.Key(request.getUsername(), request.getPassword()));
        loginEntity.setPeerId(peerId);
        loginRepository.save(loginEntity);

        response.setUsername(request.getUsername());
        response.setRole(request.getRole());
        return response;
    }

    @Override
    public PeerDetailsResponse getPeerDetails(PeerDetailsRequest request) {
        PeerDetailsResponse response = new PeerDetailsResponse();
        Optional<PeerEntity> entity = peerRepository.findById(new PeerEntity.Key(request.getPeerId()));
        if (entity.isPresent()) {
            response = mapper.convertValue(entity.get(), PeerDetailsResponse.class);
            response.setStatusCode(StatusCode.SUCCESS);
        } else {
            response.setStatusCode(StatusCode.USER_DOES_NOT_EXIST);
        }
        return response;
    }
}
