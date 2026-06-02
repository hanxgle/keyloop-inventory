package com.keyloop.example.inventorydashboard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeVehicleStatusRequest {
    
    @NotBlank(message = "Vehicle's status cannot be blank")
    private String status;
}
