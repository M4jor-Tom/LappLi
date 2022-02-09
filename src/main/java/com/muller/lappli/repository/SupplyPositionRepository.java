package com.muller.lappli.repository;

import com.muller.lappli.domain.SupplyPosition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SupplyPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyPositionRepository extends JpaRepository<SupplyPosition, Long> {}
