package com.project.volunpeer.peer.controller;

import com.project.volunpeer.common.dto.response.BaseResponse;
import com.project.volunpeer.common.enums.StatusCode;
import com.project.volunpeer.peer.dto.request.CreatePeerRequest;
import com.project.volunpeer.peer.dto.request.GetPeerRequest;
import com.project.volunpeer.peer.dto.response.GetPeerResponse;
import com.project.volunpeer.peer.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/peer")
public class PeerController {
    @Autowired
    PeerService peerService;

    @PostMapping("/create")
    public BaseResponse create(@RequestBody CreatePeerRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            peerService.createPeer(request);
            response.setStatusCode(StatusCode.SUCCESS);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @PostMapping("/get")
    public GetPeerResponse getPeer(@RequestBody GetPeerRequest request) {
        return peerService.getPeer(request);
    }
}
