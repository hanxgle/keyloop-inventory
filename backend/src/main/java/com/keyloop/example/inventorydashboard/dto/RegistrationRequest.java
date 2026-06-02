package com.keyloop.example.inventorydashboard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    @NotBlank(message = "Manager's username cannot be blank")
    private String username;

    @NotBlank(message = "Manager's password cannot be blank")
    private String password;

    @NotBlank(message = "Manager's name cannot be blank")
    private String name;

    @NotBlank(message = "Manager's dealership name cannot be blank")
    private String dealershipName;

    @NotBlank(message = "Dealership's location cannot be blank")
    private String location;
}
