package com.project.volunpeer_be.connection.service.impl;

import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.common.util.CommonUtil;
import com.project.volunpeer_be.connection.dto.Connection;
import com.project.volunpeer_be.connection.dto.ConnectionUpcomingQuest;
import com.project.volunpeer_be.connection.dto.PotentialConnection;
import com.project.volunpeer_be.connection.dto.PotentialConnectionShift;
import com.project.volunpeer_be.connection.dto.request.*;
import com.project.volunpeer_be.connection.dto.response.*;
import com.project.volunpeer_be.connection.service.ConnectionService;
import com.project.volunpeer_be.db.entity.*;
import com.project.volunpeer_be.db.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
        HashMap<PotentialConnection, HashSet<String>> potentialConnections = new HashMap<>();
        for (PeerQuestShiftEntity peerQuestShift : peerQuestShifts) {
            List<PeerQuestShiftEntity> connectionQuestShifts = peerQuestShiftRepository.findByQuestIdAndShiftNum(peerQuestShift.getQuestId(), peerQuestShift.getShiftNum());
        
            for (PeerQuestShiftEntity connectionQuestShift : connectionQuestShifts) {
                if (!connectionQuestShift.getPeerId().equals(peer.getPeerId())) {
                    PotentialConnection potentialConnection = new PotentialConnection();
                    PeerEntity potentialConnectionEntity = commonUtil.getPeerFromPeerId(connectionQuestShift.getPeerId());
                    potentialConnection.setPeerId(potentialConnectionEntity.getPeerId());
                    potentialConnection.setName(potentialConnectionEntity.getName());
                    
                    // Initialize the list if this is a new potential connection
                    potentialConnections.putIfAbsent(potentialConnection, new HashSet<>());
                }
            }
        
            for (PotentialConnection potentialConnection : potentialConnections.keySet()) {
                for (PeerQuestShiftEntity peerQuestShiftEntity : connectionQuestShifts) {
                    if (peerQuestShiftEntity.getPeerId().equals(potentialConnection.getPeerId())) {
                        Optional<QuestEntity> questEntity = questRepository.findById(new QuestEntity.Key(peerQuestShiftEntity.getQuestId()));
                        HashSet<String> list = potentialConnections.get(potentialConnection);
                        
                        // Only add the quest title if the quest entity is present
                        questEntity.ifPresent(quest -> list.add(quest.getTitle()));
                    }
                }
            }
        }

        List<PotentialConnection> resultList = new ArrayList<>();
        for (PotentialConnection potentialConnection : potentialConnections.keySet()) {
            potentialConnection.setQuests(potentialConnections.get(potentialConnection).stream().toList());
            resultList.add(potentialConnection);
        }
        response.setPotentialConnections(resultList);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }

    @Override
    public ConnectionUpcomingQuestResponse getConnectionUpcomingQuests(HttpServletRequest httpRequest) {
        PeerEntity peer = commonUtil.getPeerFromHttpRequest(httpRequest);
        ConnectionUpcomingQuestResponse response = new ConnectionUpcomingQuestResponse();
        List<ConnectionUpcomingQuest> connectionUpcomingQuests = new ArrayList<>();

        //get a list of connections
        HashSet<String> connectionIds = peer.getConnections();

        for(String connectionId : connectionIds) {
            List<PeerQuestShiftEntity> peerQuestShift = new ArrayList<>();
            HashSet<QuestShiftEntity> activeQuestShifts = new HashSet<>();

            // Process each quest shift for each connection to get just the active quest shifts
            peerQuestShift = peerQuestShiftRepository.findByPeerId(connectionId);
            for(PeerQuestShiftEntity peerQuestShiftEntity : peerQuestShift) {
                Optional<QuestShiftEntity> questShiftEntity = questShiftRepository.findById(new QuestShiftEntity.Key(peerQuestShiftEntity.getQuestId(), peerQuestShiftEntity.getShiftNum()));
                
                // Only add the quest shift if it is active
                questShiftEntity.ifPresent(questShift -> {
                    if (LocalDateTime.parse(questShift.getStartDateTime()).isAfter(LocalDateTime.now())) {
                        activeQuestShifts.add(questShift);
                    }
                });
            }

            for(QuestShiftEntity questShiftEntity : activeQuestShifts) {
                ConnectionUpcomingQuest connectionUpcomingQuest = new ConnectionUpcomingQuest();

                String questId = questShiftEntity.getQuestId();
                Optional<QuestEntity> questEntity = questRepository.findById(new QuestEntity.Key(questId));
                
                // Process peer 
                Optional<PeerEntity> peerEntity = peerRepository.findById(new PeerEntity.Key(connectionId));
                if (peerEntity.isEmpty()) {
                    response.setStatusCode(StatusCode.USER_DOES_NOT_EXIST);
                    return response;
                }
                connectionUpcomingQuest.setPeerName(peerEntity.get().getName());

                // Process quest shifts
                connectionUpcomingQuest.setQuestTitle(questEntity.get().getTitle());
                connectionUpcomingQuest.setImageUrl(questEntity.get().getImageUrl());
                connectionUpcomingQuest.setStartDateTime(questShiftEntity.getStartDateTime());
                connectionUpcomingQuest.setEndDateTime(questShiftEntity.getEndDateTime());
                connectionUpcomingQuests.add(connectionUpcomingQuest);
            }
                
        }
        response.setQuests(connectionUpcomingQuests);
        response.setStatusCode(StatusCode.SUCCESS);

        return response;
    }
}
