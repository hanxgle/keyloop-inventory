package com.keyloop.example.inventorydashboard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import com.keyloop.example.inventorydashboard.dto.AddVehicleRequest;
import com.keyloop.example.inventorydashboard.dto.ChangeVehicleStatusRequest;
import com.keyloop.example.inventorydashboard.dto.GetListOfVehiclesResponse;
import com.keyloop.example.inventorydashboard.dto.GetVehicleResponse;
import com.keyloop.example.inventorydashboard.dto.VehicleDto;
import com.keyloop.example.inventorydashboard.dto.VehicleFilterRequest;

@WebMvcTest(VehicleDetailController.class)
public class VehicleDetailControllerTest extends BaseControllerTest {

    private VehicleDto mockVehicleDto = VehicleDto.builder()
            .id(VEHICLE_ID)
            .inventoryId(INVENTORY_ID)
            .make(MAKE)
            .model(MODEL)
            .modelYear(MODEL_YEAR)
            .dateAdded(DATE_ADDED)
            .status(STATUS)
            .agingStock(AGING_STOCK)
            .build();

    @Test
    @DisplayName("Should return 200 OK and vehicle details when given valid inventory ID and AddVehicleRequest")
    public void shouldReturnVehicle_WhenInventoryIdAndRequestAreValid() throws Exception {
        // Arrange
        String rawJsonBody = 
        """
            {
                "make": "%s",
                "model": "%s",
                "modelYear": "%s"
            }
        """.formatted(MAKE, MODEL, MODEL_YEAR);

        when(vehicleDetailService.addVehicleToInventory(eq(INVENTORY_ID), any(AddVehicleRequest.class)))
                .thenReturn(mockVehicleDto);

        // Act & Assert
        mockMvc.perform(post("/inventorydashboard/inventories/{inventoryId}/vehicles/new", INVENTORY_ID)
                        .with(user(USERNAME).roles(USER_ROLE))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rawJsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VEHICLE_ID))
                .andExpect(jsonPath("$.inventoryId").value(INVENTORY_ID))
                .andExpect(jsonPath("$.make").value(MAKE))
                .andExpect(jsonPath("$.model").value(MODEL))
                .andExpect(jsonPath("$.modelYear").value(MODEL_YEAR))
                .andExpect(jsonPath("$.dateAdded").value(DATE_ADDED.toString()))
                .andExpect(jsonPath("$.status").value(STATUS))
                .andExpect(jsonPath("$.agingStock").value(AGING_STOCK));
    }

    @Test
    @DisplayName("Should return 200 OK and a filtered and paginated list of vehicle details when given valid inventory ID and VehicleFilterRequest")
    public void shouldReturnFilteredAndPaginatedListOfVehicles_WhenInventoryIdAndRequestAreValid() throws Exception {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        Page<VehicleDto> mockPageOfVehicleDtos = new PageImpl<>(
            Collections.singletonList(mockVehicleDto),
            pageable,
            1
        );

        String rawJsonBody = 
        """
            {
                "page": 0,
                "size": 20,
                "make": "%s",
                "model": "%s",
                "maxAgeInDays": 10
            }
        """.formatted(MAKE, MODEL);

        GetListOfVehiclesResponse mockResponse = new GetListOfVehiclesResponse(mockPageOfVehicleDtos);

        when(vehicleDetailService.getListOfVehiclesFromInventoryId(eq(INVENTORY_ID), any(VehicleFilterRequest.class)))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/inventorydashboard/inventories/{inventoryId}/vehicles", INVENTORY_ID)
                        .with(user(USERNAME).roles(USER_ROLE))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rawJsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicles.content[0].id").value(VEHICLE_ID))
                .andExpect(jsonPath("$.vehicles.content[0].inventoryId").value(INVENTORY_ID))
                .andExpect(jsonPath("$.vehicles.content[0].make").value(MAKE))
                .andExpect(jsonPath("$.vehicles.content[0].model").value(MODEL))
                .andExpect(jsonPath("$.vehicles.content[0].modelYear").value(MODEL_YEAR))
                .andExpect(jsonPath("$.vehicles.content[0].dateAdded").value(DATE_ADDED.toString()))
                .andExpect(jsonPath("$.vehicles.content[0].status").value(STATUS))
                .andExpect(jsonPath("$.vehicles.content[0].agingStock").value(AGING_STOCK));
    }

    @Test
    @DisplayName("Should return 200 OK and vehicle details when given valid inventory and vehicle IDs")
    public void shouldReturnVehicle_WhenInventoryIdAndVehicleIdAreValid() throws Exception {
        // Arrange
        GetVehicleResponse mockResponse = new GetVehicleResponse(mockVehicleDto);

        when(vehicleDetailService.getVehicleFromInventoryIdAndVehicleId(eq(INVENTORY_ID), eq(VEHICLE_ID)))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/inventorydashboard/inventories/{inventoryId}/vehicles/{vehicleId}", INVENTORY_ID, VEHICLE_ID)
                        .with(user(USERNAME).roles(USER_ROLE))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicle.id").value(VEHICLE_ID))
                .andExpect(jsonPath("$.vehicle.inventoryId").value(INVENTORY_ID))
                .andExpect(jsonPath("$.vehicle.make").value(MAKE))
                .andExpect(jsonPath("$.vehicle.model").value(MODEL))
                .andExpect(jsonPath("$.vehicle.modelYear").value(MODEL_YEAR))
                .andExpect(jsonPath("$.vehicle.dateAdded").value(DATE_ADDED.toString()))
                .andExpect(jsonPath("$.vehicle.status").value(STATUS))
                .andExpect(jsonPath("$.vehicle.agingStock").value(AGING_STOCK));
    }

    @Test
    @DisplayName("Should return 200 OK and vehicle details when given valid inventory ID, vehicle ID, and ChangeVehicleStatusRequest")
    public void shouldReturnVehicle_WhenInventoryIdVehicleIdAndRequestAreValid() throws Exception {
        // Arrange
        String rawJsonBody = 
        """
            {
                "status": "%s"
            }
        """.formatted(STATUS);

        when(vehicleDetailService.changeVehicleStatus(eq(INVENTORY_ID), eq(VEHICLE_ID), any(ChangeVehicleStatusRequest.class)))
                .thenReturn(mockVehicleDto);

        // Act & Assert
        mockMvc.perform(post("/inventorydashboard/inventories/{inventoryId}/vehicles/{vehicleId}/status", INVENTORY_ID, VEHICLE_ID)
                        .with(user(USERNAME).roles(USER_ROLE))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rawJsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VEHICLE_ID))
                .andExpect(jsonPath("$.inventoryId").value(INVENTORY_ID))
                .andExpect(jsonPath("$.make").value(MAKE))
                .andExpect(jsonPath("$.model").value(MODEL))
                .andExpect(jsonPath("$.modelYear").value(MODEL_YEAR))
                .andExpect(jsonPath("$.dateAdded").value(DATE_ADDED.toString()))
                .andExpect(jsonPath("$.status").value(STATUS))
                .andExpect(jsonPath("$.agingStock").value(AGING_STOCK));
    }
}