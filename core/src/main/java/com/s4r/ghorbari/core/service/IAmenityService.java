package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.AmenityDto;
import com.s4r.ghorbari.core.entity.Amenity;

import java.util.List;
import java.util.Optional;

public interface IAmenityService {

    void createAmenity(AmenityDto dto);

    List<AmenityDto> getAllAmenities();

    Optional<AmenityDto> getAmenityById(Long id);

    List<AmenityDto> getAmenitiesByType(Amenity.AmenityType type);

    List<AmenityDto> getAmenitiesByStatus(Amenity.AmenityStatus status);

    List<AmenityDto> getBookableAmenities();

    List<AmenityDto> getAvailableBookableAmenities();

    void updateAmenity(Long id, AmenityDto dto);

    void deleteAmenity(Long id);
}
