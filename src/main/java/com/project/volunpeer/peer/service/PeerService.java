package com.project.volunpeer.peer.service;

import com.project.volunpeer.peer.dto.request.CreatePeerRequest;
import com.project.volunpeer.peer.dto.request.GetPeerRequest;
import com.project.volunpeer.peer.dto.response.GetPeerResponse;

public interface PeerService {
    void createPeer(CreatePeerRequest request);
    GetPeerResponse getPeer(GetPeerRequest request);
}
