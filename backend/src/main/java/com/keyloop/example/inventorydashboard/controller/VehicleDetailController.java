package com.keyloop.example.inventorydashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keyloop.example.inventorydashboard.dto.AddVehicleRequest;
import com.keyloop.example.inventorydashboard.dto.ChangeVehicleStatusRequest;
import com.keyloop.example.inventorydashboard.dto.GetListOfVehiclesResponse;
import com.keyloop.example.inventorydashboard.dto.GetVehicleResponse;
import com.keyloop.example.inventorydashboard.dto.VehicleDto;
import com.keyloop.example.inventorydashboard.dto.VehicleFilterRequest;
import com.keyloop.example.inventorydashboard.service.VehicleDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Validated
@RequestMapping("/inventorydashboard/inventories/{inventoryId}/vehicles")
@Tag(name = "Vehicle Detail Controller", description = "Endpoints for adding new vehicle, retrieving vehicle(s), or change vehicle's status ")
public class VehicleDetailController {

    private final VehicleDetailService vehicleDetailService;

    public VehicleDetailController(VehicleDetailService vehicleDetailService) {
        this.vehicleDetailService = vehicleDetailService;
    }

    @PostMapping("/new")
    @Operation(summary = "Add a new vehicle to inventory", description = "Returns the new vehicle's information")
    public ResponseEntity<VehicleDto> addVehicleToInventory(
        @PathVariable @NotBlank(message = "Inventory ID cannot be blank") String inventoryId, 
        @Valid @RequestBody AddVehicleRequest request
    ) {
        VehicleDto vehicleDto = vehicleDetailService.addVehicleToInventory(inventoryId, request);
        return ResponseEntity.ok(vehicleDto);
    }

    @GetMapping
    @Operation(summary = "Get and filter a paginated list of vehicles from inventory", description = "Returns the list of vehicles")
    public ResponseEntity<GetListOfVehiclesResponse> getListOfVehiclesFromInventoryId(
        @PathVariable @NotBlank(message = "Inventory ID cannot be blank") String inventoryId,
        @Valid @RequestBody VehicleFilterRequest request
    ) {
        GetListOfVehiclesResponse response = vehicleDetailService.getListOfVehiclesFromInventoryId(inventoryId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{vehicleId}")
    @Operation(summary = "Get one vehicle from inventory ID and vehicle ID", description = "Returns the vehicle")
    public ResponseEntity<GetVehicleResponse> getVehicleFromInventoryIdAndVehicleId(
        @PathVariable @NotBlank(message = "Inventory ID cannot be blank") String inventoryId,
        @PathVariable @NotBlank(message = "Vehicle ID cannot be blank") String vehicleId
    ) {
        GetVehicleResponse response = vehicleDetailService.getVehicleFromInventoryIdAndVehicleId(inventoryId, vehicleId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{vehicleId}/status")
    @Operation(summary = "Change the status of an aging vehicle in inventory", description = "Returns the updated vehicle's information")
    public ResponseEntity<VehicleDto> changeVehicleStatus(
        @PathVariable @NotBlank(message = "Inventory ID cannot be blank") String inventoryId,
        @PathVariable @NotBlank(message = "Vehicle ID cannot be blank") String vehicleId,
        @Valid @RequestBody ChangeVehicleStatusRequest request
    ) {
        VehicleDto vehicleDto = vehicleDetailService.changeVehicleStatus(inventoryId, vehicleId, request);
        return ResponseEntity.ok(vehicleDto);
    }
    
}
