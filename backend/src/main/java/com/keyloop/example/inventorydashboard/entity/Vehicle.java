package com.keyloop.example.inventorydashboard.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.hibernate.annotations.CreationTimestamp;

import com.keyloop.example.inventorydashboard.enums.VehicleStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicle")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String modelYear;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDate dateAdded;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    @Column(name = "aging_stock", nullable = false)
    private boolean agingStock = false;

    @PostLoad
    public void checkAgingStock() {
        if (this.dateAdded != null) {
            // Calculate the age in days between the dateAdded and today
            long daysInInventory = ChronoUnit.DAYS.between(this.dateAdded, LocalDate.now());
            
            // If it has been sitting for more than 90 days, update status to AGING_STOCK
            if (daysInInventory > 90) {
                this.agingStock = true;
            }
        }
    }
}
