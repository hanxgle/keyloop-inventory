package com.keyloop.example.inventorydashboard.dto;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetListOfVehiclesResponse {

    private Page<VehicleDto> vehicles;
}
