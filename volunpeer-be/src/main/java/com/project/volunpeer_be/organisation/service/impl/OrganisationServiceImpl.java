package com.project.volunpeer_be.organisation.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.volunpeer_be.common.enums.KeyType;
import com.project.volunpeer_be.common.enums.Role;
import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.common.util.CommonUtil;
import com.project.volunpeer_be.common.util.KeyGeneratorUtil;
import com.project.volunpeer_be.db.entity.*;
import com.project.volunpeer_be.db.repository.OrganisationLoginRepository;
import com.project.volunpeer_be.db.repository.OrganisationRepository;
import com.project.volunpeer_be.db.repository.PeerLoginRepository;
import com.project.volunpeer_be.db.repository.PeerRepository;
import com.project.volunpeer_be.organisation.dto.request.OrganisationCreateRequest;
import com.project.volunpeer_be.organisation.dto.response.OrganisationCreateResponse;
import com.project.volunpeer_be.organisation.service.OrganisationService;
import com.project.volunpeer_be.peer.dto.request.PeerAddPersonalityInterestRequest;
import com.project.volunpeer_be.peer.dto.request.PeerCreateRequest;
import com.project.volunpeer_be.peer.dto.response.PeerAddPersonalityInterestResponse;
import com.project.volunpeer_be.peer.dto.response.PeerCreateResponse;
import com.project.volunpeer_be.peer.dto.response.PeerDetailsResponse;
import com.project.volunpeer_be.peer.enums.Interest;
import com.project.volunpeer_be.peer.enums.Personality;
import com.project.volunpeer_be.peer.service.PeerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class OrganisationServiceImpl implements OrganisationService {
    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    OrganisationLoginRepository organisationLoginRepository;

    @Autowired
    KeyGeneratorUtil keyGen;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CommonUtil commonUtil;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public OrganisationCreateResponse createOrganisation(OrganisationCreateRequest request) {
        OrganisationCreateResponse response = new OrganisationCreateResponse();
        if (organisationLoginRepository.existsByUsername(request.getUsername())) {
            response.setStatusCode(StatusCode.USERNAME_TAKEN);
            return response;
        }

        String organisationId = keyGen.generateKey(KeyType.ORG);

        OrganisationEntity organisationEntity = mapper.convertValue(request, OrganisationEntity.class);
        organisationEntity.setId(new OrganisationEntity.Key(organisationId));
        organisationEntity.setOrganisationId(organisationId);
        organisationEntity.setDescription(request.getDescription());
        organisationEntity.setName(request.getName());
        organisationRepository.save(organisationEntity);

        OrganisationLoginEntity loginEntity = mapper.convertValue(request, OrganisationLoginEntity.class);
        loginEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        loginEntity.setId(new LoginEntity.Key(request.getUsername()));
        loginEntity.setOrganisationId(organisationId);
        loginEntity.setRole(Role.ORGANISATION.getName());
        organisationLoginRepository.save(loginEntity);

        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }
}
