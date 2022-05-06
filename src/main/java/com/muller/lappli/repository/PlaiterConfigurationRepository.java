package com.muller.lappli.repository;

import com.muller.lappli.domain.PlaiterConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PlaiterConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaiterConfigurationRepository extends JpaRepository<PlaiterConfiguration, Long> {}
