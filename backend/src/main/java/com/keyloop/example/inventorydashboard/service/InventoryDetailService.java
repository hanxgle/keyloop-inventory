package com.keyloop.example.inventorydashboard.service;

import org.springframework.stereotype.Service;

import com.keyloop.example.inventorydashboard.dto.GetInventoryResponse;
import com.keyloop.example.inventorydashboard.dto.InventoryDto;
import com.keyloop.example.inventorydashboard.entity.Inventory;
import com.keyloop.example.inventorydashboard.entity.Manager;
import com.keyloop.example.inventorydashboard.repository.InventoryRepository;
import com.keyloop.example.inventorydashboard.repository.ManagerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InventoryDetailService {

    private InventoryRepository inventoryRepository;
    private ManagerRepository managerRepository;

    public InventoryDetailService(InventoryRepository inventoryRepository, ManagerRepository managerRepository) {
        this.inventoryRepository = inventoryRepository;
        this.managerRepository = managerRepository;
    }

    public GetInventoryResponse getInventoryFromManagerIdAndInventoryId(String managerId, String inventoryId) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(
            () -> new EntityNotFoundException(
                String.format("Manager ID %s does not exist.", managerId)));

        Inventory inventory = inventoryRepository.findByManagerIdAndId(managerId, inventoryId).orElseThrow(
            () -> new EntityNotFoundException(
                String.format("Inventory for ID %s and manager ID %s does not exist.", inventoryId, managerId)));

        InventoryDto inventoryDto = convertInventoryToInventoryDto(inventoryId, manager, inventory);
        
        return new GetInventoryResponse(inventoryDto);
    }

    private InventoryDto convertInventoryToInventoryDto(String inventoryId, Manager manager, Inventory inventory) {
        return InventoryDto.builder()
            .id(inventoryId)
            .dealershipName(inventory.getDealershipName())
            .location(inventory.getLocation())
            .managerId(manager.getId())
            .managerName(manager.getName())
            .build();
    }
}
