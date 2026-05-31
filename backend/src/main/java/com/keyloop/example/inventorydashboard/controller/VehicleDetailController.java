package com.keyloop.example.inventorydashboard.controller;

import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/inventorydashboard/inventories/{inventoryId}/vehicles")
public class VehicleDetailController {

    private final VehicleDetailService vehicleDetailService;

    public VehicleDetailController(VehicleDetailService vehicleDetailService) {
        this.vehicleDetailService = vehicleDetailService;
    }

    @PostMapping("/new")
    public ResponseEntity<VehicleDto> addVehicleToInventory(
        @PathVariable String inventoryId, 
        @RequestBody AddVehicleRequest request
    ) {
        VehicleDto vehicleDto = vehicleDetailService.addVehicleToInventory(inventoryId, request);
        return ResponseEntity.ok(vehicleDto);
    }

    @GetMapping
    public ResponseEntity<GetListOfVehiclesResponse> getListOfVehiclesFromInventoryId(
        @PathVariable String inventoryId,
        @RequestBody VehicleFilterRequest request
    ) {
        GetListOfVehiclesResponse response = vehicleDetailService.getListOfVehiclesFromInventoryId(inventoryId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<GetVehicleResponse> getVehicleFromInventoryIdAndVehicleId(
        @PathVariable String inventoryId,
        @PathVariable String vehicleId
    ) {
        GetVehicleResponse response = vehicleDetailService.getVehicleFromInventoryIdAndVehicleId(inventoryId, vehicleId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{vehicleId}/status")
    public ResponseEntity<Void> changeVehicleStatus(
        @PathVariable String inventoryId,
        @PathVariable String vehicleId,
        @RequestBody ChangeVehicleStatusRequest request
    ) {
        vehicleDetailService.changeVehicleStatus(inventoryId, vehicleId, request);
        return ResponseEntity.ok().build();
    }
    
}
