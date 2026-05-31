package com.keyloop.example.inventorydashboard.helper;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import com.keyloop.example.inventorydashboard.entity.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VehicleSpecifications {

    public static Specification<Vehicle> filterVehicles(String inventoryId, String make, String model, Integer maxAgeInDays) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("inventory").get("id"), inventoryId));

            if (make != null && !make.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("make")), "%" + make.toLowerCase() + "%"));
            }

            if (model != null && !model.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("model")), "%" + model.toLowerCase() + "%"));
            }

            // 4. Age Filter (maxAgeInDays = 30 means cars added within the last 30 days)
            if (maxAgeInDays != null) {
                LocalDate cutoffDate = LocalDate.now().minusDays(maxAgeInDays);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateAdded"), cutoffDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
