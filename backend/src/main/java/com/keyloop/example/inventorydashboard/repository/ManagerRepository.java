package com.keyloop.example.inventorydashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keyloop.example.inventorydashboard.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String>{

    Optional<Manager> findById(String managerId);

    Optional<Manager> findByUsername(String username);

    boolean existsByUsername(String username);
}
