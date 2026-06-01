package com.keyloop.example.inventorydashboard.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.keyloop.example.inventorydashboard.dto.AddVehicleRequest;
import com.keyloop.example.inventorydashboard.dto.ChangeVehicleStatusRequest;
import com.keyloop.example.inventorydashboard.dto.GetListOfVehiclesResponse;
import com.keyloop.example.inventorydashboard.dto.GetVehicleResponse;
import com.keyloop.example.inventorydashboard.dto.VehicleDto;
import com.keyloop.example.inventorydashboard.dto.VehicleFilterRequest;
import com.keyloop.example.inventorydashboard.entity.Vehicle;
import com.keyloop.example.inventorydashboard.enums.VehicleStatus;

import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleDetailServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private VehicleDetailService vehicleDetailService;

    @Test
    void addVehicleToInventory_Success() {
        // Arrange
        AddVehicleRequest request = new AddVehicleRequest();
        request.setMake(MAKE_1);
        request.setModel(MODEL_1);
        request.setModelYear(MODEL_YEAR_1);

        // Act
        VehicleDto vehicleDto = vehicleDetailService.addVehicleToInventory(savedInventory.getId(), request);

        // Assert
        assertNotNull(vehicleDto);
        assertNotNull(vehicleDto.getId());
        assertEquals(MAKE_1, vehicleDto.getMake());
        assertEquals(MODEL_1, vehicleDto.getModel());
        assertEquals(savedInventory.getId(), vehicleDto.getInventoryId());
        assertTrue(vehicleRepository.findById(vehicleDto.getId()).isPresent());
    }

    @Test
    void getListOfVehiclesFromInventoryId_BasicVerification() {
        // Arrange
        VehicleFilterRequest request = new VehicleFilterRequest();
        request.setPage(0);
        request.setSize(10);

        // Act
        GetListOfVehiclesResponse response = vehicleDetailService.getListOfVehiclesFromInventoryId(
                savedInventory.getId(), 
                request
        );

        // Assert
        assertNotNull(response);
        Page<VehicleDto> pageResult = response.getVehicles();
        assertNotNull(pageResult);
        assertEquals(2, pageResult.getTotalElements());
        assertEquals(MODEL_1, pageResult.getContent().get(0).getModel());
        assertEquals(MODEL_2, pageResult.getContent().get(1).getModel());
    }

    @Test
    void getVehicleFromInventoryIdAndVehicleId_Success() {
        // Act
        GetVehicleResponse response = vehicleDetailService.getVehicleFromInventoryIdAndVehicleId(
                savedInventory.getId(), 
                savedVehicle1.getId()
        );

        // Assert
        assertNotNull(response);
        VehicleDto vehicleDto = response.getVehicle();
        assertEquals(savedVehicle1.getId(), vehicleDto.getId());
        assertEquals(MAKE_1, vehicleDto.getMake());
        assertEquals(MODEL_1, vehicleDto.getModel());
    }

    @Test
    void getVehicleFromInventoryIdAndVehicleId_ThrowsException_WhenNotFound() {
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            vehicleDetailService.getVehicleFromInventoryIdAndVehicleId(savedInventory.getId(), TEST_VEHICLE_ID);
        });
    }

    @Test
    void changeVehicleStatus_Success_UpdatesDatabase() {
        // Arrange
        String status = "SOLD";
        ChangeVehicleStatusRequest request = new ChangeVehicleStatusRequest();
        request.setStatus(status);

        // Act
        VehicleDto updatedVehicleDto = vehicleDetailService.changeVehicleStatus(
                savedInventory.getId(), 
                savedVehicle1.getId(), 
                request
        );

        // Assert
        assertNotNull(updatedVehicleDto);
        assertEquals(status, updatedVehicleDto.getStatus());

        // Assert
        Vehicle dbVehicle = vehicleRepository.findById(savedVehicle1.getId()).orElseThrow();
        assertEquals(VehicleStatus.SOLD, dbVehicle.getStatus());
    }
}
