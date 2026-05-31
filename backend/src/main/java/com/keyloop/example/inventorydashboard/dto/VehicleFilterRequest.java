package com.keyloop.example.inventorydashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleFilterRequest {

    private int page = 0;

    private int size = 20;

    private String make;
    
    private String model;
    
    private Integer maxAgeInDays;
}
