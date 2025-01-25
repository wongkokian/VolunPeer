package com.project.volunpeer_be.organisation.controller;

import com.project.volunpeer_be.common.enums.StatusCode;
import com.project.volunpeer_be.organisation.dto.request.OrganisationCreateRequest;
import com.project.volunpeer_be.organisation.dto.response.OrganisationCreateResponse;
import com.project.volunpeer_be.organisation.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organisation")
public class OrganisationController {
    @Autowired
    OrganisationService organisationService;

    @PostMapping("/create")
    public OrganisationCreateResponse createPeer(@RequestBody OrganisationCreateRequest request) {
        OrganisationCreateResponse response = new OrganisationCreateResponse();
        try {
            response = organisationService.createOrganisation(request);
        } catch (Exception e) {
            response.setStatusCode(StatusCode.FAILURE);
        }
        return response;
    }
}
