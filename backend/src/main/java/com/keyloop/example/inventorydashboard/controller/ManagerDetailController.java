package com.keyloop.example.inventorydashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keyloop.example.inventorydashboard.dto.GetManagerResponse;
import com.keyloop.example.inventorydashboard.service.ManagerDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/inventorydashboard/managers")
@Tag(name = "Manager Detail Controller", description = "Endpoints for retrieving manager's information")
public class ManagerDetailController {

    private final ManagerDetailService managerDetailService;

    public ManagerDetailController(ManagerDetailService managerDetailService) {
        this.managerDetailService = managerDetailService;
    }

    @GetMapping("/{managerId}")
    @Operation(summary = "Get manager from manager ID", description = "Returns the signed-in manager's information")
    public ResponseEntity<GetManagerResponse> getManagerFromManagerId(
        @PathVariable String managerId
    ) {
        GetManagerResponse response = managerDetailService.getManagerFromManagerId(managerId);
        return ResponseEntity.ok(response);
    }
}
