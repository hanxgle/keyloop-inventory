package com.keyloop.example.inventorydashboard.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.keyloop.example.inventorydashboard.dto.GetManagerResponse;
import com.keyloop.example.inventorydashboard.dto.ManagerDto;
import com.keyloop.example.inventorydashboard.dto.RegistrationRequest;
import com.keyloop.example.inventorydashboard.entity.Inventory;
import com.keyloop.example.inventorydashboard.entity.Manager;

import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerDetailServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ManagerDetailService managerDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void getManagerFromManagerId_Success() {
        // Act
        GetManagerResponse response = managerDetailService.getManagerFromManagerId(savedManager.getId());

        // Assert
        assertNotNull(response);
        ManagerDto dto = response.getManagerDto();
        
        assertEquals(savedManager.getId(), dto.getId());
        assertEquals(USERNAME, dto.getUsername());
        assertEquals(MANAGER_NAME, dto.getManagerName());
        assertEquals(savedInventory.getId(), dto.getInventoryId());
    }

    @Test
    void getManagerFromManagerId_ThrowsException_WhenNotFound() {
        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            managerDetailService.getManagerFromManagerId(TEST_MANAGER_ID);
        });

        assertEquals(String.format("Manager ID %s does not exist.", TEST_MANAGER_ID), exception.getMessage());
    }

    @Test
    void registerNewManager_Success_PersistsToDatabase() {
        // Arrange
        String username = "newusername";
        String password = "newpassword";
        String name = "New Manager";
        String dealershipName = "New Dealership";
        String location = "123 New Street, New Ward, New City, Viet Nam";

        RegistrationRequest request = new RegistrationRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setName(name);
        request.setDealershipName(dealershipName);
        request.setLocation(location);

        // Act
        ManagerDto managerDto = managerDetailService.registerNewManager(request);

        // Assert
        assertNotNull(managerDto);
        assertNotNull(managerDto.getId());
        assertNotNull(managerDto.getInventoryId());
        assertEquals(username, managerDto.getUsername());
        assertEquals(name, managerDto.getManagerName());

        Manager dbManager = managerRepository.findById(managerDto.getId()).orElse(null);
        assertNotNull(dbManager);
        assertEquals(name, dbManager.getName());
        
        assertTrue(passwordEncoder.matches(password, dbManager.getPassword()));

        Inventory dbInventory = dbManager.getInventory();
        assertNotNull(dbInventory);
        assertEquals(dealershipName, dbInventory.getDealershipName());
        assertEquals(location, dbInventory.getLocation());
    }

    @Test
    void registerNewManager_ThrowsException_WhenUsernameAlreadyExists() {
        // Arrange
        String password = "newpassword";
        String name = "New Manager";
        String dealershipName = "New Dealership";
        String location = "123 New Street, New Ward, New City, Viet Nam";

        RegistrationRequest request = new RegistrationRequest();
        request.setUsername(USERNAME); 
        request.setPassword(password);
        request.setName(name);
        request.setDealershipName(dealershipName);
        request.setLocation(location);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            managerDetailService.registerNewManager(request);
        });

        assertEquals(String.format("Username %s is already taken.", USERNAME), exception.getMessage());
    }
}