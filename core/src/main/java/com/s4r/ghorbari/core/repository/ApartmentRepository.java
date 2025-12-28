package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    List<Apartment> findByStatus(Apartment.ApartmentStatus status);

    List<Apartment> findByBuildingId(Long buildingId);

    List<Apartment> findByFloor(Integer floor);

    @Query("SELECT a FROM Apartment a LEFT JOIN FETCH a.building")
    List<Apartment> findAllWithBuilding();

    @Query("SELECT a FROM Apartment a LEFT JOIN FETCH a.building WHERE a.id = :id")
    Optional<Apartment> findByIdWithBuilding(Long id);

    @Query("SELECT a FROM Apartment a LEFT JOIN FETCH a.building WHERE a.status = :status")
    List<Apartment> findByStatusWithBuilding(Apartment.ApartmentStatus status);

    @Query("SELECT a FROM Apartment a LEFT JOIN FETCH a.building WHERE a.buildingId = :buildingId")
    List<Apartment> findByBuildingIdWithBuilding(Long buildingId);
}
