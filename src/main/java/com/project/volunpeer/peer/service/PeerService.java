package com.project.volunpeer.peer.service;

import com.project.volunpeer.peer.dto.request.PeerAddPersonalityRequest;
import com.project.volunpeer.peer.dto.request.PeerCreateRequest;
import com.project.volunpeer.peer.dto.response.PeerAddPersonalityResponse;
import com.project.volunpeer.peer.dto.response.PeerCreateResponse;
import com.project.volunpeer.peer.dto.response.PeerDetailsResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PeerService {
    PeerCreateResponse createPeer(PeerCreateRequest request);

    PeerDetailsResponse getPeerDetails(HttpServletRequest httpRequest);

    PeerAddPersonalityResponse addPeerPersonality(PeerAddPersonalityRequest request, HttpServletRequest httpRequest);
}
