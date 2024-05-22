package com.project.volunpeer.peer.controller;

import com.project.volunpeer.common.enums.StatusCode;
import com.project.volunpeer.peer.dto.request.PeerCreateRequest;
import com.project.volunpeer.peer.dto.request.PeerDetailsRequest;
import com.project.volunpeer.peer.dto.response.PeerCreateResponse;
import com.project.volunpeer.peer.dto.response.PeerDetailsResponse;
import com.project.volunpeer.peer.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            response.setStatusCode(StatusCode.SUCCESS);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @PostMapping("/details")
    public PeerDetailsResponse getPeerDetails(@RequestBody PeerDetailsRequest request) {
        PeerDetailsResponse response = new PeerDetailsResponse();
        try {
            response = peerService.getPeerDetails(request);
            response.setStatusCode(StatusCode.SUCCESS);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }
}
