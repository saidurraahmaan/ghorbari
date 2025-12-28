package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    List<Resident> findByApartmentId(Long apartmentId);

    List<Resident> findByUserId(Long userId);

    Optional<Resident> findByApartmentIdAndIsPrimaryResident(Long apartmentId, Boolean isPrimaryResident);

    Optional<Resident> findByUserIdAndApartmentId(Long userId, Long apartmentId);
}
