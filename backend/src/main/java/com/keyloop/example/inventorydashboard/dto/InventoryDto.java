package com.keyloop.example.inventorydashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class InventoryDto {

    private String id;

    private String dealershipName;

    private String location;

    private String managerId;

    private String managerName;
}
