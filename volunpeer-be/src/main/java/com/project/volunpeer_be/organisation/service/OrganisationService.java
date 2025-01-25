package com.project.volunpeer_be.organisation.service;

import com.project.volunpeer_be.organisation.dto.request.OrganisationCreateRequest;
import com.project.volunpeer_be.organisation.dto.response.OrganisationCreateResponse;

public interface OrganisationService {
    OrganisationCreateResponse createOrganisation(OrganisationCreateRequest request);
}
