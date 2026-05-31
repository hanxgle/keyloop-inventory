package com.keyloop.example.inventorydashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keyloop.example.inventorydashboard.dto.GetManagerResponse;
import com.keyloop.example.inventorydashboard.service.ManagerDetailService;

@RestController
@RequestMapping("/inventorydashboard/managers")
public class ManagerDetailController {

    private final ManagerDetailService managerDetailService;

    public ManagerDetailController(ManagerDetailService managerDetailService) {
        this.managerDetailService = managerDetailService;
    }

    @GetMapping("/{managerId}")
    public ResponseEntity<GetManagerResponse> getManagerFromManagerId(
        @PathVariable String managerId
    ) {
        GetManagerResponse response = managerDetailService.getManagerFromManagerId(managerId);
        return ResponseEntity.ok(response);
    }
}
