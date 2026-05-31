package com.keyloop.example.inventorydashboard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.keyloop.example.inventorydashboard.dto.AddVehicleRequest;
import com.keyloop.example.inventorydashboard.dto.ChangeVehicleStatusRequest;
import com.keyloop.example.inventorydashboard.dto.GetListOfVehiclesResponse;
import com.keyloop.example.inventorydashboard.dto.GetVehicleResponse;
import com.keyloop.example.inventorydashboard.dto.VehicleDto;
import com.keyloop.example.inventorydashboard.dto.VehicleFilterRequest;
import com.keyloop.example.inventorydashboard.entity.Inventory;
import com.keyloop.example.inventorydashboard.entity.Vehicle;
import com.keyloop.example.inventorydashboard.enums.VehicleStatus;
import com.keyloop.example.inventorydashboard.helper.VehicleSpecifications;
import com.keyloop.example.inventorydashboard.repository.InventoryRepository;
import com.keyloop.example.inventorydashboard.repository.VehicleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VehicleDetailService {

    private VehicleRepository vehicleRepository;
    private InventoryRepository inventoryRepository;

    public VehicleDetailService(VehicleRepository vehicleRepository, InventoryRepository inventoryRepository) {
        this.vehicleRepository = vehicleRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public VehicleDto addVehicleToInventory(String inventoryId, AddVehicleRequest request) {
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(
            () -> new EntityNotFoundException(String.format("Inventory with ID %s does not exist.", inventoryId))
        );
        Vehicle newVehicle = new Vehicle();
        newVehicle.setInventory(inventory);
        newVehicle.setMake(request.getMake());
        newVehicle.setModel(request.getModel());
        newVehicle.setModelYear(request.getModelYear());

        vehicleRepository.save(newVehicle);

        return convertVehicleToVehicleDto(newVehicle);
    }

    public GetListOfVehiclesResponse getListOfVehiclesFromInventoryId(
        String inventoryId, VehicleFilterRequest request
    ) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("model").ascending());
        
        Specification<Vehicle> specification = VehicleSpecifications.filterVehicles(
            inventoryId, request.getMake(), request.getModel(), request.getMaxAgeInDays()
        );
        
        Page<Vehicle> vehicles = vehicleRepository.findAll(specification, pageable);
        
        Page<VehicleDto> vehicleDtos = vehicles.map(this::convertVehicleToVehicleDto);

        return new GetListOfVehiclesResponse(vehicleDtos);
    }

    public GetVehicleResponse getVehicleFromInventoryIdAndVehicleId(String inventoryId, String vehicleId) {
        Vehicle vehicle = getAndValidateVehicle(inventoryId, vehicleId);
        VehicleDto vehicleDto = convertVehicleToVehicleDto(vehicle);
            
        return new GetVehicleResponse(vehicleDto);
    }

    public VehicleDto changeVehicleStatus(String inventoryId, String vehicleId, ChangeVehicleStatusRequest request) {
        String status = request.getStatus();
        Vehicle vehicle = getAndValidateVehicle(inventoryId, vehicleId);

        if (!vehicle.isAgingStock()) {
            throw new IllegalArgumentException(String.format("Vehicle with ID %s is not an aging stock.", vehicleId));
        }
        
        vehicle.setStatus(VehicleStatus.valueOf(status.toUpperCase()));
        vehicleRepository.save(vehicle);

        return convertVehicleToVehicleDto(vehicle);
    }

    private Vehicle getAndValidateVehicle(String inventoryId, String vehicleId) {
        return vehicleRepository.findByInventoryIdAndId(inventoryId, vehicleId).orElseThrow(
            () -> new EntityNotFoundException(
                String.format("Vehicle for ID %s and inventory ID %s does not exist.", vehicleId, inventoryId)));
    }

    private VehicleDto convertVehicleToVehicleDto(Vehicle vehicle) {
        return VehicleDto.builder()
            .id(vehicle.getId())
            .inventoryId(vehicle.getInventory().getId())
            .make(vehicle.getMake())
            .model(vehicle.getModel())
            .modelYear(vehicle.getModelYear())
            .dateAdded(vehicle.getDateAdded())
            .status(vehicle.getStatus().name())
            .agingStock(vehicle.isAgingStock())
            .build();
    }
}
