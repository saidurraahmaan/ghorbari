package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.entity.Apartment;

import java.util.List;
import java.util.Optional;

public interface IApartmentService {

    Apartment createApartment(Apartment apartment);

    Optional<Apartment> getApartmentById(Long id);

    List<Apartment> getAllApartments();

    List<Apartment> getApartmentsByStatus(Apartment.ApartmentStatus status);

    Apartment updateApartment(Apartment apartment);

    void deleteApartment(Long id);
}
