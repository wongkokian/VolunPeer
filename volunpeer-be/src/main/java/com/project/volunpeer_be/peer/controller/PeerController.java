package com.project.volunpeer_be.peer.controller;

import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.peer.dto.request.PeerAddPersonalityInterestRequest;
import com.project.volunpeer_be.peer.dto.request.PeerCreateRequest;
import com.project.volunpeer_be.peer.dto.response.PeerAddPersonalityInterestResponse;
import com.project.volunpeer_be.peer.dto.response.PeerCreateResponse;
import com.project.volunpeer_be.peer.dto.response.PeerDetailsResponse;
import com.project.volunpeer_be.peer.service.PeerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/peer")
public class PeerController {
    @Autowired
    PeerService peerService;

    @PostMapping("/create")
    public PeerCreateResponse createPeer(@RequestBody PeerCreateRequest request) {
        PeerCreateResponse response = new PeerCreateResponse();
        try {
            response = peerService.createPeer(request);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @GetMapping("/details")
    public PeerDetailsResponse getPeerDetails(HttpServletRequest httpRequest) {
        PeerDetailsResponse response = new PeerDetailsResponse();
        try {
            response = peerService.getPeerDetails(httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @PostMapping("/add-personality-interest")
    public PeerAddPersonalityInterestResponse addPeerPersonality(@RequestBody PeerAddPersonalityInterestRequest request, HttpServletRequest httpRequest) {
        PeerAddPersonalityInterestResponse response = new PeerAddPersonalityInterestResponse();
        try {
            response = peerService.addPeerPersonalityInterest(request, httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }
}
