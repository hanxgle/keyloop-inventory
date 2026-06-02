package com.keyloop.example.inventorydashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keyloop.example.inventorydashboard.dto.GetInventoryResponse;
import com.keyloop.example.inventorydashboard.service.InventoryDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@Validated
@RequestMapping("/inventorydashboard/managers")
@Tag(name = "Inventory Detail Controller", description = "Endpoints for retrieving inventory's information")
public class InventoryDetailController {

    private final InventoryDetailService inventoryDetailService;

    public InventoryDetailController(InventoryDetailService inventoryDetailService) {
        this.inventoryDetailService = inventoryDetailService;
    }

    @GetMapping("/{managerId}/inventories/{inventoryId}")
    @Operation(summary = "Get inventory from manager ID and inventory ID", description = "Returns the signed-in manager's inventory in the database")
    public ResponseEntity<GetInventoryResponse> getInventoryFromManagerIdAndInventoryId(
        @PathVariable @NotBlank(message = "Manager ID cannot be blank") String managerId,
        @PathVariable @NotBlank(message = "Inventory ID cannot be blank") String inventoryId
    ) {
        GetInventoryResponse response = inventoryDetailService.getInventoryFromManagerIdAndInventoryId(managerId, inventoryId);
        return ResponseEntity.ok(response);
    }
}
