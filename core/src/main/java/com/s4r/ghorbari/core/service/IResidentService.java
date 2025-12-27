package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.ResidentDto;
import com.s4r.ghorbari.core.entity.Resident;

import java.util.List;
import java.util.Optional;

public interface IResidentService {

    void createResident(ResidentDto dto);

    List<ResidentDto> getAllResidents();

    Optional<ResidentDto> getResidentById(Long id);

    Optional<ResidentDto> getResidentByEmail(String email);

    List<ResidentDto> getResidentsByStatus(Resident.ResidentStatus status);

    List<ResidentDto> getResidentsByApartmentId(Long apartmentId);

    void updateResident(Long id, ResidentDto dto);

    void deleteResident(Long id);
}
