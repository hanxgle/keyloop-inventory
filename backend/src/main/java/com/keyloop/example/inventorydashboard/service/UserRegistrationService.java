package com.keyloop.example.inventorydashboard.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.keyloop.example.inventorydashboard.entity.Manager;
import com.keyloop.example.inventorydashboard.repository.ManagerRepository;

@Service
public class UserRegistrationService implements UserDetailsService{

    private ManagerRepository managerRepository;

    public UserRegistrationService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = managerRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("User not found")
        );
        
        return org.springframework.security.core.userdetails.User
            .withUsername(manager.getUsername())
            .password(manager.getPassword())
            .roles("USER") // Minimum required role mapping
            .build();
    }

    
}
