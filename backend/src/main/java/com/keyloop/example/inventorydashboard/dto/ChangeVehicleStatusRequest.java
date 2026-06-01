package com.keyloop.example.inventorydashboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeVehicleStatusRequest {
    
    private String status;
}
