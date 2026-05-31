package com.keyloop.example.inventorydashboard.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.keyloop.example.inventorydashboard.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String>, JpaSpecificationExecutor<Vehicle>{

    Page<Vehicle> findByInventoryId(String inventoryId, Pageable pageable);

    Optional<Vehicle> findByInventoryIdAndId(String inventoryId, String vehicleId);
}
