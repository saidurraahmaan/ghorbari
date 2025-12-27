package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    List<Apartment> findByStatus(Apartment.ApartmentStatus status);

    List<Apartment> findByBuildingId(Long buildingId);

    List<Apartment> findByFloor(Integer floor);
}
