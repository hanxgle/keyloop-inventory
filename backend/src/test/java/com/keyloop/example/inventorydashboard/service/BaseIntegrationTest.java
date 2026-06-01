package com.keyloop.example.inventorydashboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.keyloop.example.inventorydashboard.entity.Inventory;
import com.keyloop.example.inventorydashboard.entity.Manager;
import com.keyloop.example.inventorydashboard.entity.Vehicle;
import com.keyloop.example.inventorydashboard.enums.VehicleStatus;
import com.keyloop.example.inventorydashboard.repository.InventoryRepository;
import com.keyloop.example.inventorydashboard.repository.ManagerRepository;
import com.keyloop.example.inventorydashboard.repository.VehicleRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    static {
        System.setProperty("spring.datasource.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        System.setProperty("spring.datasource.driver-class-name", "org.h2.Driver");
        System.setProperty("spring.datasource.username", "sa");
        System.setProperty("spring.datasource.password", "");
        System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.H2Dialect");
        System.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
    }

    protected final String TEST_MANAGER_ID = "test-manager-id";
    protected final String TEST_INVENTORY_ID = "test-inventory-id";
    protected final String TEST_VEHICLE_ID = "test-vehicle-id";
    protected final String DEALERSHIP_NAME = "Test Dealership";
    protected final String LOCATION = "123 Test Street, Test Ward, Test City, Viet Nam";
    protected final String MANAGER_NAME = "Test Manager";
    protected final String USERNAME = "testusername";
    protected final String PASSWORD = "testpassword";
    protected final String USER_ROLE = "USER";
    protected final String MAKE_1 = "Test Make 1";
    protected final String MODEL_1 = "Test Model 1";
    protected final String MODEL_YEAR_1 = "2026";
    protected final String MAKE_2 = "Test Make 2";
    protected final String MODEL_2 = "Test Model 2";
    protected final String MODEL_YEAR_2 = "2025";
    protected final LocalDate DATE_ADDED_1 = LocalDate.now();
    protected final LocalDate DATE_ADDED_2 = LocalDate.now().minusDays(100);
    protected final VehicleStatus STATUS = VehicleStatus.AVAILABLE;
    protected final boolean AGING_STOCK_1 = false;
    protected final boolean AGING_STOCK_2 = true;

    @Autowired
    protected InventoryRepository inventoryRepository;

    @Autowired
    protected ManagerRepository managerRepository;

    @Autowired
    protected VehicleRepository vehicleRepository;

    protected Manager savedManager;
    protected Inventory savedInventory;
    protected Vehicle savedVehicle1;
    protected Vehicle savedVehicle2;

    @BeforeEach
    void baseSetUp() {
        vehicleRepository.deleteAll();
        inventoryRepository.deleteAll();
        managerRepository.deleteAll();

        Manager manager = new Manager();
        manager.setUsername(USERNAME);
        manager.setPassword(PASSWORD);
        manager.setName(MANAGER_NAME);

        Inventory inventory = new Inventory();
        inventory.setDealershipName(DEALERSHIP_NAME);
        inventory.setLocation(LOCATION);

        manager.setInventory(inventory);

        savedManager = managerRepository.save(manager);
        savedInventory = savedManager.getInventory();

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setMake(MAKE_1);
        vehicle1.setModel(MODEL_1);
        vehicle1.setModelYear(MODEL_YEAR_1);
        vehicle1.setDateAdded(DATE_ADDED_1);
        vehicle1.setStatus(STATUS);
        vehicle1.setAgingStock(AGING_STOCK_1);
        vehicle1.setInventory(savedInventory); 

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setMake(MAKE_2);
        vehicle2.setModel(MODEL_2);
        vehicle2.setModelYear(MODEL_YEAR_2);
        vehicle2.setDateAdded(DATE_ADDED_2);
        vehicle2.setStatus(STATUS);
        vehicle2.setAgingStock(AGING_STOCK_2);
        vehicle2.setInventory(savedInventory);

        savedVehicle1 = vehicleRepository.save(vehicle1);
        savedVehicle2 = vehicleRepository.save(vehicle2);

        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(savedVehicle1);
        vehicleList.add(savedVehicle2);
        savedInventory.setVehicles(vehicleList);
    }
}