package com.s4r.ghorbari.core.repository;

import com.s4r.ghorbari.core.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    Optional<Resident> findByEmail(String email);

    List<Resident> findByStatus(Resident.ResidentStatus status);

    List<Resident> findByApartmentId(Long apartmentId);
}
