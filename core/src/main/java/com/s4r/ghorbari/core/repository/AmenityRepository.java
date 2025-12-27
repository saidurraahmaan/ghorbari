package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    List<Amenity> findByType(Amenity.AmenityType type);

    List<Amenity> findByStatus(Amenity.AmenityStatus status);

    List<Amenity> findByIsBookable(Boolean isBookable);

    List<Amenity> findByStatusAndIsBookable(Amenity.AmenityStatus status, Boolean isBookable);
}
