package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.ApartmentDto;
import com.s4r.ghorbari.core.entity.Apartment;

import java.util.List;
import java.util.Optional;

public interface IApartmentService {

    void createApartment(ApartmentDto dto);

    Optional<ApartmentDto> getApartmentById(Long id);

    List<ApartmentDto> getAllApartments();

    List<ApartmentDto> getApartmentsByStatus(Apartment.ApartmentStatus status);

    List<ApartmentDto> getApartmentsByBuildingId(Long buildingId);

    void updateApartment(Long id, ApartmentDto dto);

    void deleteApartment(Long id);
}
