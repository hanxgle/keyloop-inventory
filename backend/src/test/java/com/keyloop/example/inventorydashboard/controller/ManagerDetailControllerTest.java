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

import com.keyloop.example.inventorydashboard.dto.GetManagerResponse;
import com.keyloop.example.inventorydashboard.dto.ManagerDto;

@WebMvcTest(ManagerDetailController.class)
public class ManagerDetailControllerTest extends BaseControllerTest {

    @Test
    @DisplayName("Should return 200 OK and manager details when given valid manager ID")
    public void shouldReturnManager_WhenManagerIdIsValid() throws Exception {
        // Arrange
        ManagerDto mockManagerDto = ManagerDto.builder()
            .id(MANAGER_ID)
            .username(USERNAME)
            .managerName(MANAGER_NAME)
            .inventoryId(INVENTORY_ID)
            .build();

        GetManagerResponse mockResponse = new GetManagerResponse(mockManagerDto);

        when(managerDetailService.getManagerFromManagerId(eq(MANAGER_ID)))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/inventorydashboard/managers/{managerId}", MANAGER_ID)
                        .with(user(USERNAME).roles(USER_ROLE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managerDto.id").value(MANAGER_ID))
                .andExpect(jsonPath("$.managerDto.username").value(USERNAME))
                .andExpect(jsonPath("$.managerDto.managerName").value(MANAGER_NAME))
                .andExpect(jsonPath("$.managerDto.inventoryId").value(INVENTORY_ID));
    }
}