package com.keyloop.example.inventorydashboard.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import com.keyloop.example.inventorydashboard.dto.GetInventoryResponse;
import com.keyloop.example.inventorydashboard.dto.InventoryDto;

@WebMvcTest(InventoryDetailController.class)
public class InventoryDetailControllerTest extends BaseControllerTest {

    @Test
    @DisplayName("Should return 200 OK and inventory details when given valid manager and inventory IDs")
    public void shouldReturnInventory_WhenManagerIdAndInventoryIdAreValid() throws Exception {
        // Arrange
        InventoryDto mockInventoryDto = InventoryDto.builder()
            .id(INVENTORY_ID)
            .dealershipName(DEALERSHIP_NAME)
            .location(LOCATION)
            .managerId(MANAGER_ID)
            .managerName(MANAGER_NAME)
            .build();

        GetInventoryResponse mockResponse = new GetInventoryResponse(mockInventoryDto);

        when(inventoryDetailService.getInventoryFromManagerIdAndInventoryId(eq(MANAGER_ID), eq(INVENTORY_ID)))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/inventorydashboard/managers/{managerId}/inventories/{inventoryId}", MANAGER_ID, INVENTORY_ID)
                        .with(user(USERNAME).roles(USER_ROLE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryDto.id").value(INVENTORY_ID))
                .andExpect(jsonPath("$.inventoryDto.dealershipName").value(DEALERSHIP_NAME))
                .andExpect(jsonPath("$.inventoryDto.location").value(LOCATION))
                .andExpect(jsonPath("$.inventoryDto.managerId").value(MANAGER_ID))
                .andExpect(jsonPath("$.inventoryDto.managerName").value(MANAGER_NAME));
    }
}