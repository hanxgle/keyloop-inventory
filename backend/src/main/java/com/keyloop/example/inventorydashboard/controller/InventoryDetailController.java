package com.keyloop.example.inventorydashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keyloop.example.inventorydashboard.dto.GetInventoryResponse;
import com.keyloop.example.inventorydashboard.service.InventoryDetailService;

@RestController
@RequestMapping("/inventorydashboard/managers")
public class InventoryDetailController {

    private final InventoryDetailService inventoryDetailService;

    public InventoryDetailController(InventoryDetailService inventoryDetailService) {
        this.inventoryDetailService = inventoryDetailService;
    }

    @GetMapping("/{managerId}/inventories/{inventoryId}")
    public ResponseEntity<GetInventoryResponse> getInventoryFromManagerIdAndInventoryId(
        @PathVariable String managerId,
        @PathVariable String inventoryId
    ) {
        GetInventoryResponse response = inventoryDetailService.getInventoryFromManagerIdAndInventoryId(managerId, inventoryId);
        return ResponseEntity.ok(response);
    }
}
