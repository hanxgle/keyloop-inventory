package com.keyloop.example.inventorydashboard.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.keyloop.example.inventorydashboard.dto.GetManagerResponse;
import com.keyloop.example.inventorydashboard.dto.ManagerDto;
import com.keyloop.example.inventorydashboard.dto.RegistrationRequest;
import com.keyloop.example.inventorydashboard.entity.Inventory;
import com.keyloop.example.inventorydashboard.entity.Manager;
import com.keyloop.example.inventorydashboard.repository.ManagerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ManagerDetailService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    public ManagerDetailService(ManagerRepository managerRepository, PasswordEncoder passwordEncoder) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public GetManagerResponse getManagerFromManagerId(String managerId) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(
            () -> new EntityNotFoundException(String.format("Manager ID %s does not exist.", managerId)));

        ManagerDto managerDto = ManagerDto.builder()
            .id(managerId)
            .username(manager.getUsername())
            .managerName(manager.getName())
            .inventoryId(manager.getInventory().getId())
            .build();
            
        return new GetManagerResponse(managerDto);
    }

    public String registerNewManager(RegistrationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (managerRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(String.format("Username %s is already taken.", username));
        }

        Manager newManager = new Manager();
        newManager.setUsername(username);
        newManager.setPassword(passwordEncoder.encode(password));
        newManager.setName(request.getName()); 

        Inventory newInventory = new Inventory();
        newInventory.setDealershipName(request.getDealershipName());
        newInventory.setLocation(request.getLocation());

        newManager.setInventory(newInventory);
        managerRepository.save(newManager);

        return newManager.getId();
    }
}
