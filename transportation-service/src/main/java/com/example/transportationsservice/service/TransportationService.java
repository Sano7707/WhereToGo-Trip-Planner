package com.example.transportationsservice.service;


import com.example.transportationsservice.entity.Transportation;
import com.example.transportationsservice.repository.TransportationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportationService {

    @Autowired
    private TransportationRepository transportationRepository;

    public List<Transportation> getAllTransportations() {
        return transportationRepository.findAll();
    }

    public Optional<Transportation> getTransportationById(Long id) {
        return transportationRepository.findById(id);
    }

    public List<Transportation> searchTransportations(String city) {
        return transportationRepository.findByCity(city);
    }

    public Transportation addTransportation(Transportation transportation) {
        return transportationRepository.save(transportation);
    }

    public Optional<Transportation> updateTransportation(Long id, Transportation updatedTransportation) {
        return transportationRepository.findById(id)
            .map(t -> {
                t.setName(updatedTransportation.getName());
                t.setCity(updatedTransportation.getCity());
                t.setType(updatedTransportation.getType());
                t.setCost(updatedTransportation.getCost());
                t.setDescription(updatedTransportation.getDescription());
                return transportationRepository.save(t);
            });
    }

    public boolean deleteTransportation(Long id) {
        if (transportationRepository.existsById(id)) {
            transportationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}