package com.keyloop.example.inventorydashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddVehicleRequest {

    private String make;

    private String model;

    private String modelYear;
}
