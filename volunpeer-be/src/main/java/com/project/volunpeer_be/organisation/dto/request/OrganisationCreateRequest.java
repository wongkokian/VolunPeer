package com.project.volunpeer_be.organisation.dto.request;

import lombok.Data;

@Data
public class OrganisationCreateRequest {
    private String username;
    private String password;
    private String name;
    private String description;
}
