package com.example.beveragemanager.DTO;

import lombok.Data;

@Data
public class LoginDTO {
    private String token = "";
    private String role = "";
    private String username = "";
}
