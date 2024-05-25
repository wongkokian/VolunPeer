package com.project.volunpeer_be.connection.service.impl;

import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.common.util.CommonUtil;
import com.project.volunpeer_be.connection.dto.Connection;
import com.project.volunpeer_be.connection.dto.PotentialConnectionShift;
import com.project.volunpeer_be.connection.dto.request.*;
import com.project.volunpeer_be.connection.dto.response.*;
import com.project.volunpeer_be.connection.service.ConnectionService;
import com.project.volunpeer_be.db.entity.*;
import com.project.volunpeer_be.db.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    PeerRepository peerRepository;

    @Autowired
    PeerQuestShiftRepository peerQuestShiftRepository;

    @Autowired
    QuestRepository questRepository;

    @Autowired
    QuestShiftRepository questShiftRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    CommonUtil commonUtil;

    @Override
    public ConnectionListResponse getConnectionList(HttpServletRequest httpRequest) {
        PeerEntity peer = commonUtil.getPeerFromHttpRequest(httpRequest);
        HashSet<String> connectionIds = peer.getConnections();
        List<Connection> connectionList = new ArrayList<>();
        for (String connectionId : connectionIds) {
            PeerEntity connectionEntity = commonUtil.getPeerFromPeerId(connectionId);
            Connection connection = new Connection(connectionId, connectionEntity.getName(), connectionEntity.getPersonality());
            connectionList.add(connection);
        }

        ConnectionListResponse response = new ConnectionListResponse();
        response.setConnections(connectionList);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public SentConnectionListResponse getSentConnectionList(HttpServletRequest httpRequest) {
        PeerEntity peer = commonUtil.getPeerFromHttpRequest(httpRequest);
        HashSet<String> sentConnectionIds = peer.getSentConnectionRequests();
        List<Connection> sentConnectionList = new ArrayList<>();
        for (String sentConnectionId : sentConnectionIds) {
            PeerEntity sentConnectionEntity = commonUtil.getPeerFromPeerId(sentConnectionId);
            Connection sentConnection = new Connection(sentConnectionId, sentConnectionEntity.getName(), null);
            sentConnectionList.add(sentConnection);
        }

        SentConnectionListResponse response = new SentConnectionListResponse();
        response.setSentConnections(sentConnectionList);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public ReceivedConnectionListResponse getReceivedConnectionList(HttpServletRequest httpRequest) {
        PeerEntity peer = commonUtil.getPeerFromHttpRequest(httpRequest);
        HashSet<String> receivedConnectionIds = peer.getReceivedConnectionRequests();
        List<Connection> receivedConnectionList = new ArrayList<>();
        for (String receivedConnectionId : receivedConnectionIds) {
            PeerEntity receivedConnectionEntity = commonUtil.getPeerFromPeerId(receivedConnectionId);
            Connection receivedConnection = new Connection(receivedConnectionId, receivedConnectionEntity.getName(), null);
            receivedConnectionList.add(receivedConnection);
        }

        ReceivedConnectionListResponse response = new ReceivedConnectionListResponse();
        response.setReceivedConnections(receivedConnectionList);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public SendConnectionResponse sendConnection(SendConnectionRequest request, HttpServletRequest httpRequest) {
        PeerEntity sender = commonUtil.getPeerFromHttpRequest(httpRequest);
        HashSet<String> sentConnectionRequests = sender.getSentConnectionRequests();
        sentConnectionRequests.add(request.getPeerId());
        sender.setSentConnectionRequests(sentConnectionRequests);
        peerRepository.save(sender);

        PeerEntity receiver = commonUtil.getPeerFromPeerId(request.getPeerId());
        HashSet<String> receivedConnectionRequests = receiver.getReceivedConnectionRequests();
        receivedConnectionRequests.add(sender.getPeerId());
        receiver.setReceivedConnectionRequests(receivedConnectionRequests);
        peerRepository.save(receiver);

        SendConnectionResponse response = new SendConnectionResponse();
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public CancelConnectionResponse cancelConnection(CancelConnectionRequest request, HttpServletRequest httpRequest) {
        PeerEntity sender = commonUtil.getPeerFromHttpRequest(httpRequest);
        HashSet<String> sentConnectionRequests = sender.getSentConnectionRequests();
        sentConnectionRequests.remove(request.getPeerId());
        sender.setSentConnectionRequests(sentConnectionRequests);
        peerRepository.save(sender);

        PeerEntity receiver = commonUtil.getPeerFromPeerId(request.getPeerId());
        HashSet<String> receivedConnectionRequests = receiver.getReceivedConnectionRequests();
        receivedConnectionRequests.remove(sender.getPeerId());
        receiver.setReceivedConnectionRequests(receivedConnectionRequests);
        peerRepository.save(receiver);

        CancelConnectionResponse response = new CancelConnectionResponse();
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public AcceptConnectionResponse acceptConnection(AcceptConnectionRequest request, HttpServletRequest httpRequest) {
        PeerEntity receiver = commonUtil.getPeerFromHttpRequest(httpRequest);
        HashSet<String> receivedConnectionRequests = receiver.getReceivedConnectionRequests();
        receivedConnectionRequests.remove(request.getPeerId());
        receiver.setReceivedConnectionRequests(receivedConnectionRequests);
        HashSet<String> receiverConnections = receiver.getConnections();
        receiverConnections.add(request.getPeerId());
        receiver.setConnections(receiverConnections);
        peerRepository.save(receiver);

        PeerEntity sender = commonUtil.getPeerFromPeerId(request.getPeerId());
        HashSet<String> sentConnectionRequests = sender.getSentConnectionRequests();
        sentConnectionRequests.remove(receiver.getPeerId());
        sender.setSentConnectionRequests(sentConnectionRequests);
        HashSet<String> senderConnections = sender.getConnections();
        senderConnections.add(receiver.getPeerId());
        sender.setConnections(senderConnections);
        peerRepository.save(sender);

        AcceptConnectionResponse response = new AcceptConnectionResponse();
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public RejectConnectionResponse rejectConnection(RejectConnectionRequest request, HttpServletRequest httpRequest) {
        PeerEntity receiver = commonUtil.getPeerFromHttpRequest(httpRequest);
        HashSet<String> receivedConnectionRequests = receiver.getReceivedConnectionRequests();
        receivedConnectionRequests.remove(request.getPeerId());
        receiver.setReceivedConnectionRequests(receivedConnectionRequests);
        peerRepository.save(receiver);

        PeerEntity sender = commonUtil.getPeerFromPeerId(request.getPeerId());
        HashSet<String> sentConnectionRequests = sender.getSentConnectionRequests();
        sentConnectionRequests.remove(receiver.getPeerId());
        sender.setSentConnectionRequests(sentConnectionRequests);
        peerRepository.save(sender);

        RejectConnectionResponse response = new RejectConnectionResponse();
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public DeleteConnectionResponse deleteConnection(DeleteConnectionRequest request, HttpServletRequest httpRequest) {
        PeerEntity deleter = commonUtil.getPeerFromHttpRequest(httpRequest);
        HashSet<String> deleterConnections = deleter.getConnections();
        deleterConnections.remove(request.getPeerId());
        deleter.setConnections(deleterConnections);
        peerRepository.save(deleter);

        PeerEntity otherParty = commonUtil.getPeerFromPeerId(request.getPeerId());
        HashSet<String> otherPartyConnections = otherParty.getConnections();
        otherPartyConnections.remove(deleter.getPeerId());
        otherParty.setConnections(otherPartyConnections);
        peerRepository.save(otherParty);

        DeleteConnectionResponse response = new DeleteConnectionResponse();
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public PotentialConnectionResponse getPotentialConnectionList(HttpServletRequest httpRequest) {
        PeerEntity peer = commonUtil.getPeerFromHttpRequest(httpRequest);
        List<PeerQuestShiftEntity> peerQuestShifts = peerQuestShiftRepository.findByPeerId(peer.getPeerId());
        PotentialConnectionResponse response = new PotentialConnectionResponse();
        List<PotentialConnectionShift> potentialConnectionShiftList = new ArrayList<>();
        for(PeerQuestShiftEntity peerQuestShift : peerQuestShifts) {
            List<PeerQuestShiftEntity> connectionQuestShifts = peerQuestShiftRepository.findByQuestIdAndShiftNum(peerQuestShift.getQuestId(), peerQuestShift.getShiftNum());
            PotentialConnectionShift potentialConnectionShift = new PotentialConnectionShift();

            Optional<QuestEntity> questEntity = questRepository.findById(new QuestEntity.Key(peerQuestShift.getQuestId()));
            if(questEntity.isEmpty()) {
                response.setStatusCode(StatusCode.QUEST_DOES_NOT_EXIST);
                return response;
            }
            potentialConnectionShift.setQuestTitle(questEntity.get().getTitle());

            Optional<OrganisationEntity> organisationEntity = organisationRepository.findById(new OrganisationEntity.Key(questEntity.get().getOrgId()));
            if(organisationEntity.isEmpty()) {
                response.setStatusCode(StatusCode.ORGANISATION_DOES_NOT_EXIST);
                return response;
            }
            potentialConnectionShift.setOrganisationName(organisationEntity.get().getName());

            Optional<QuestShiftEntity> questShiftEntity = questShiftRepository.findById(new QuestShiftEntity.Key(peerQuestShift.getQuestId(), peerQuestShift.getShiftNum()));
            if(questShiftEntity.isEmpty()) {
                response.setStatusCode(StatusCode.QUEST_SHIFT_DOES_NOT_EXIST);
                return response;
            }
            if(questShiftEntity.get().getIsCompleted().equals(0)) {
                continue;
            }
            potentialConnectionShift.setStartDateTime(questShiftEntity.get().getStartDateTime());
            potentialConnectionShift.setEndDateTime(questShiftEntity.get().getEndDateTime());
            potentialConnectionShift.setOrganisationName(organisationEntity.get().getName());

            List<Connection> connections = new ArrayList<>();
            for(PeerQuestShiftEntity connectionQuestShift : connectionQuestShifts) {
                if(connectionQuestShift.getPeerId().equals(peer.getPeerId())) {
                    continue;
                }
                PeerEntity connectionEntity = commonUtil.getPeerFromPeerId(connectionQuestShift.getPeerId());
                Connection connection = new Connection(connectionQuestShift.getPeerId(), connectionEntity.getName(), null);
                connections.add(connection);
            }
            if(connections.isEmpty()) {
                continue;
            }
            potentialConnectionShift.setPotentialConnections(connections);
            potentialConnectionShiftList.add(potentialConnectionShift);
        }
        response.setShifts(potentialConnectionShiftList);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public ConnectionUpcomingQuestResponse getConnectionUpcomingQuests(HttpServletRequest httpRequest) {
        PeerEntity peer = commonUtil.getPeerFromHttpRequest(httpRequest);
        //get a list of connections
        HashSet<String> connectionIds = peer.getConnections();

        // for each connection, find out peer quest-shift
        for(String connectionId : connectionIds) {
            List<PeerQuestShiftEntity> peerQuestShift = peerQuestShiftRepository.findByPeerId(connectionId);

        }
        // get quest info
        // get shift info
        // add connection of list of connections
        return null;
    }
}
