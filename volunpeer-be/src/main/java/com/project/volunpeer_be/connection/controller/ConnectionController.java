package com.project.volunpeer_be.connection.controller;

import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.connection.dto.request.*;
import com.project.volunpeer_be.connection.dto.response.*;
import com.project.volunpeer_be.connection.service.ConnectionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connection")
public class ConnectionController {
    @Autowired
    ConnectionService connectionService;

    @GetMapping("/list")
    public ConnectionListResponse getConnectionList(HttpServletRequest httpRequest) {
        ConnectionListResponse response = new ConnectionListResponse();
        try {
            response = connectionService.getConnectionList(httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @GetMapping("/sent-list")
    public SentConnectionListResponse getSentConnectionList(HttpServletRequest httpRequest) {
        SentConnectionListResponse response = new SentConnectionListResponse();
        try {
            response = connectionService.getSentConnectionList(httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @GetMapping("/received-list")
    public ReceivedConnectionListResponse getReceivedConnectionList(HttpServletRequest httpRequest) {
        ReceivedConnectionListResponse response = new ReceivedConnectionListResponse();
        try {
            response = connectionService.getReceivedConnectionList(httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @PostMapping("/send")
    public SendConnectionResponse sendConnection(@RequestBody SendConnectionRequest request, HttpServletRequest httpRequest) {
        SendConnectionResponse response = new SendConnectionResponse();
        try {
            response = connectionService.sendConnection(request, httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @PostMapping("/cancel")
    public CancelConnectionResponse cancelConnection(@RequestBody CancelConnectionRequest request, HttpServletRequest httpRequest) {
        CancelConnectionResponse response = new CancelConnectionResponse();
        try {
            response = connectionService.cancelConnection(request, httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @PostMapping("/accept")
    public AcceptConnectionResponse acceptConnection(@RequestBody AcceptConnectionRequest request, HttpServletRequest httpRequest) {
        AcceptConnectionResponse response = new AcceptConnectionResponse();
        try {
            response = connectionService.acceptConnection(request, httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @PostMapping("/reject")
    public RejectConnectionResponse rejectConnection(@RequestBody RejectConnectionRequest request, HttpServletRequest httpRequest) {
        RejectConnectionResponse response = new RejectConnectionResponse();
        try {
            response = connectionService.rejectConnection(request, httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @PostMapping("/delete")
    public DeleteConnectionResponse deleteConnection(@RequestBody DeleteConnectionRequest request, HttpServletRequest httpRequest) {
        DeleteConnectionResponse response = new DeleteConnectionResponse();
        try {
            response = connectionService.deleteConnection(request, httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @GetMapping("/potential")
    public PotentialConnectionResponse getPotentialConnectionList(HttpServletRequest httpRequest) {
        PotentialConnectionResponse response = new PotentialConnectionResponse();
        try {
            response = connectionService.getPotentialConnectionList(httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }

    @GetMapping("/upcoming-quest")
    public ConnectionUpcomingQuestResponse getConnectionUpcomingQuests(HttpServletRequest httpRequest) {
        ConnectionUpcomingQuestResponse response = new ConnectionUpcomingQuestResponse();
        try {
            response = connectionService.getConnectionUpcomingQuests(httpRequest);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }
}
