package com.s4r.ghorbari.core.service;

import com.s4r.ghorbari.core.domain.BuildingDto;
import com.s4r.ghorbari.core.entity.Building;

import java.util.List;
import java.util.Optional;

public interface IBuildingService {

    void createBuilding(BuildingDto dto);

    List<BuildingDto> getAllBuildings();

    Optional<BuildingDto> getBuildingById(Long id);

    List<BuildingDto> getBuildingsByStatus(Building.BuildingStatus status);

    void updateBuilding(Long id, BuildingDto dto);

    void deleteBuilding(Long id);
}
