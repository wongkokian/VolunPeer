package com.project.volunpeer.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Entities")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginEntity {
    @Id
    private Key id;
    private String username;
    private String password;
    private String role;

    @Data
    @AllArgsConstructor
    public static class Key {
        private String username;
    }
}
