package com.example.experiment7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String username;
    private List<String> roles;
}
