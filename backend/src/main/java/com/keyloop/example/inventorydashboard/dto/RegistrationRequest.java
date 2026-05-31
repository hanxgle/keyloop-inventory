package com.keyloop.example.inventorydashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    private String username;

    private String password;

    private String name;

    private String dealershipName;

    private String location;
}
