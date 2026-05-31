package com.keyloop.example.inventorydashboard.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class VehicleDto {

    private String id;

    private String inventoryId;

    private String make;

    private String model;

    private String modelYear;

    private LocalDate dateAdded;

    private String status;

    private boolean agingStock;
}
