package com.example.experiment7.dto;

import java.util.List;

public class LoginResponse {
    private String message;
    private String username;
    private List<String> roles;

    public LoginResponse() {
    }

    public LoginResponse(String message, String username, List<String> roles) {
        this.message = message;
        this.username = username;
        this.roles = roles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
