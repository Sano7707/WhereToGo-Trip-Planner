package com.example.transportationsservice.repository;


import com.example.transportationsservice.entity.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {
    List<Transportation> findByCity(String city);
}