package com.muller.lappli.repository;

import com.muller.lappli.domain.FlatSheathingSupplyPosition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FlatSheathingSupplyPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlatSheathingSupplyPositionRepository extends JpaRepository<FlatSheathingSupplyPosition, Long> {}
