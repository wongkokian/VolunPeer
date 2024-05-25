package com.project.volunpeer_be.connection.service;

import com.project.volunpeer_be.connection.dto.request.*;
import com.project.volunpeer_be.connection.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;

public interface ConnectionService {
    ConnectionListResponse getConnectionList(HttpServletRequest httpRequest);

    SentConnectionListResponse getSentConnectionList(HttpServletRequest httpRequest);

    ReceivedConnectionListResponse getReceivedConnectionList(HttpServletRequest httpRequest);

    SendConnectionResponse sendConnection(SendConnectionRequest request, HttpServletRequest httpRequest);

    CancelConnectionResponse cancelConnection(CancelConnectionRequest request, HttpServletRequest httpRequest);

    AcceptConnectionResponse acceptConnection(AcceptConnectionRequest request, HttpServletRequest httpRequest);

    RejectConnectionResponse rejectConnection(RejectConnectionRequest request, HttpServletRequest httpRequest);

    DeleteConnectionResponse deleteConnection(DeleteConnectionRequest request, HttpServletRequest httpRequest);
}
