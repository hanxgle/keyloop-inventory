package com.keyloop.example.inventorydashboard.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityNotFoundException;

import com.keyloop.example.inventorydashboard.dto.GetInventoryResponse;
import com.keyloop.example.inventorydashboard.dto.InventoryDto;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryDetailServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private InventoryDetailService inventoryDetailService;

    @Test
    void getInventoryFromManagerIdAndInventoryId_Success() {
        // Act
        GetInventoryResponse response = inventoryDetailService.getInventoryFromManagerIdAndInventoryId(
                savedManager.getId(), 
                savedInventory.getId()
        );

        // Assert
        assertNotNull(response);
        assertNotNull(response.getInventoryDto());
        
        InventoryDto inventoryDto = response.getInventoryDto();
        assertEquals(MANAGER_NAME, inventoryDto.getManagerName());
        assertEquals(DEALERSHIP_NAME, inventoryDto.getDealershipName());
        assertEquals(LOCATION, inventoryDto.getLocation());
    }

    @Test
    void getInventoryFromManagerIdAndInventoryId_ThrowsException_WhenManagerNotFound() {
        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            inventoryDetailService.getInventoryFromManagerIdAndInventoryId(TEST_MANAGER_ID, savedInventory.getId());
        });

        assertEquals(String.format("Manager ID %s does not exist.", TEST_MANAGER_ID), exception.getMessage());
    }

    @Test
    void getInventoryFromManagerIdAndInventoryId_ThrowsException_WhenInventoryNotFound() {
        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            inventoryDetailService.getInventoryFromManagerIdAndInventoryId(savedManager.getId(), TEST_INVENTORY_ID);
        });

        assertEquals(String.format("Inventory for ID %s and manager ID %s does not exist.", TEST_INVENTORY_ID, savedManager.getId()), exception.getMessage());
    }
}