package com.keyloop.example.inventorydashboard.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.keyloop.example.inventorydashboard.service.InventoryDetailService;
import com.keyloop.example.inventorydashboard.service.ManagerDetailService;
import com.keyloop.example.inventorydashboard.service.VehicleDetailService;

public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    protected ManagerDetailService managerDetailService;

    @MockitoBean
    protected InventoryDetailService inventoryDetailService;

    @MockitoBean
    protected VehicleDetailService vehicleDetailService;

    protected final String INVENTORY_ID = "19140c19-e7e5-489c-ac48-e2ddc435eebb";
    protected final String DEALERSHIP_NAME = "Test Dealership";
    protected final String LOCATION = "123 Test Street, Test Ward, Test City, Viet Nam";
    protected final String MANAGER_ID = "09140c19-e7e5-489c-ac48-e2ddc435eeba";
    protected final String MANAGER_NAME = "Test Manager";
    protected final String USERNAME = "testusername";
    protected final String USER_ROLE = "USER";
    protected final String VEHICLE_ID = "29140c19-e7e5-489c-ac48-e2ddc435eebc";
    protected final String MAKE = "Test Make";
    protected final String MODEL = "Test Model";
    protected final String MODEL_YEAR = "2026";
    protected final LocalDate DATE_ADDED = LocalDate.now();
    protected final String STATUS = "AVAILABLE";
    protected final boolean AGING_STOCK = false;
}