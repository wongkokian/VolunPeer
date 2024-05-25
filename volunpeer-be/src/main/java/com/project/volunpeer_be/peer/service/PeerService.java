package com.project.volunpeer_be.peer.service;

import com.project.volunpeer_be.peer.dto.request.PeerAddPersonalityInterestRequest;
import com.project.volunpeer_be.peer.dto.request.PeerCreateRequest;
import com.project.volunpeer_be.peer.dto.response.PeerAddPersonalityInterestResponse;
import com.project.volunpeer_be.peer.dto.response.PeerCreateResponse;
import com.project.volunpeer_be.peer.dto.response.PeerDetailsResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PeerService {
    PeerCreateResponse createPeer(PeerCreateRequest request);

    PeerDetailsResponse getPeerDetails(HttpServletRequest httpRequest);

    PeerAddPersonalityInterestResponse addPeerPersonalityInterest(PeerAddPersonalityInterestRequest request, HttpServletRequest httpRequest);
}
