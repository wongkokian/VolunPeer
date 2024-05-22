package com.project.volunpeer.peer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.volunpeer.common.enums.StatusCode;
import com.project.volunpeer.peer.entity.PeerEntity;
import com.project.volunpeer.peer.dto.request.CreatePeerRequest;
import com.project.volunpeer.peer.dto.request.GetPeerRequest;
import com.project.volunpeer.peer.dto.response.GetPeerResponse;
import com.project.volunpeer.peer.repository.PeerRepository;
import com.project.volunpeer.peer.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeerServiceImpl implements PeerService {
    @Autowired
    PeerRepository peerRepository;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void createPeer(CreatePeerRequest request) {
        PeerEntity peer = mapper.convertValue(request, PeerEntity.class);
        PeerEntity.Key key = new PeerEntity.Key(request.getPeerId());
        peer.setId(key);
        peerRepository.save(peer);
    }

    @Override
    public GetPeerResponse getPeer(GetPeerRequest request) {
        GetPeerResponse response = new GetPeerResponse();
        Optional<PeerEntity> entity = peerRepository.findById(new PeerEntity.Key(request.getPeerId()));
        if(entity.isPresent()) {
            response = mapper.convertValue(entity.get(), GetPeerResponse.class);
            response.setStatusCode(StatusCode.SUCCESS);
        } else {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }
}
