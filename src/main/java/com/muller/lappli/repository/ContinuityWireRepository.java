package com.muller.lappli.repository;

import com.muller.lappli.domain.ContinuityWire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ContinuityWire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContinuityWireRepository extends JpaRepository<ContinuityWire, Long> {}
