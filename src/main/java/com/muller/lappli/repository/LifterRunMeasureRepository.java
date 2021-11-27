package com.muller.lappli.repository;

import com.muller.lappli.domain.LifterRunMeasure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LifterRunMeasure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LifterRunMeasureRepository extends JpaRepository<LifterRunMeasure, Long> {}
