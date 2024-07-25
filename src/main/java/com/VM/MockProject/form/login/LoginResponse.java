package com.VM.MockProject.form.login;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Integer id;

    private String fullName;

    public LoginResponse(String accessToken, String fullName, Integer id) {
        this.accessToken = accessToken;
        this.fullName = fullName;
        this.id = id;
    }
}
