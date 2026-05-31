package com.keyloop.example.inventorydashboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keyloop.example.inventorydashboard.dto.RegistrationRequest;
import com.keyloop.example.inventorydashboard.service.ManagerDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/inventorydashboard")
@Tag(name = "Registration Controller", description = "Endpoints for registering a new manager")
public class RegistrationController {

    private ManagerDetailService managerDetailService;

    public RegistrationController(ManagerDetailService managerDetailService) {
        this.managerDetailService = managerDetailService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new manager with a new empty inventory", description = "Returns the registered manager's ID")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        try {
            String registeredManagerId = managerDetailService.registerNewManager(request);
            return ResponseEntity.ok(String.format("Manager registered successfully with ID %s.", registeredManagerId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}