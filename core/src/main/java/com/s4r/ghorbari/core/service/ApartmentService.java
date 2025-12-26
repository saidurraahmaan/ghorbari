package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.entity.Apartment;
import com.s4r.ghorbari.core.repository.ApartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApartmentService implements IApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public void createApartment(Apartment apartment) {
        apartmentRepository.save(apartment);
    }

    public Optional<Apartment> getApartmentById(Long id) {
        return apartmentRepository.findById(id);
    }

    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public List<Apartment> getApartmentsByStatus(Apartment.ApartmentStatus status) {
        return apartmentRepository.findByStatus(status);
    }

    public void updateApartment(Apartment apartment) {
        apartmentRepository.save(apartment);
    }

    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }
}
