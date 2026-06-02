package com.keyloop.example.inventorydashboard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddVehicleRequest {

    @NotBlank(message = "Vehicle's make cannot be blank")
    private String make;

    @NotBlank(message = "Vehicle's model cannot be blank")
    private String model;

    @NotBlank(message = "Vehicle's model year cannot be blank")
    private String modelYear;
}
