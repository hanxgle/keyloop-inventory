package com.keyloop.example.inventorydashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ManagerDto {
    
    private String id;

    private String username;

    private String managerName;

    private String inventoryId;
}
