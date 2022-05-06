package com.muller.lappli.repository;

import com.muller.lappli.domain.Plaiter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Plaiter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaiterRepository extends JpaRepository<Plaiter, Long> {}
