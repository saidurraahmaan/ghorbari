package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.ResidentDto;

import java.util.List;
import java.util.Optional;

public interface IResidentService {

    void createResident(ResidentDto dto);

    List<ResidentDto> getAllResidents();

    Optional<ResidentDto> getResidentById(Long id);

    List<ResidentDto> getResidentsByApartmentId(Long apartmentId);

    List<ResidentDto> getResidentsByUserId(Long userId);

    Optional<ResidentDto> getPrimaryResidentByApartmentId(Long apartmentId);

    void updateResident(Long id, ResidentDto dto);

    void deleteResident(Long id);
}
