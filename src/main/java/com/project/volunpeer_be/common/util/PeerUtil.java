package com.project.volunpeer_be.common.util;

import com.project.volunpeer_be.db.entity.PeerEntity;
import com.project.volunpeer_be.db.entity.PeerLoginEntity;
import com.project.volunpeer_be.db.repository.PeerLoginRepository;
import com.project.volunpeer_be.db.repository.PeerRepository;
import com.project.volunpeer_be.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PeerUtil {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PeerLoginRepository peerLoginRepository;

    @Autowired
    PeerRepository peerRepository;

    public PeerEntity getPeerFromHttpRequest(HttpServletRequest httpRequest) {
        String token = jwtUtil.getJwtFromCookies(httpRequest);
        String username = jwtUtil.getUserNameFromJwtToken(token);
        Optional<PeerLoginEntity> peerLoginEntity = peerLoginRepository.findByUsername(username);
        if (peerLoginEntity.isEmpty()) {
            return null;
        }
        Optional<PeerEntity> peerEntity = peerRepository.findById(
                new PeerEntity.Key(peerLoginEntity.get().getPeerId()));
        return peerEntity.orElse(null);
    }

    public PeerEntity getPeerFromPeerId(String peerId) {
        Optional<PeerEntity> peerEntity = peerRepository.findById(
                new PeerEntity.Key(peerId));
        return peerEntity.orElse(null);
    }
}

