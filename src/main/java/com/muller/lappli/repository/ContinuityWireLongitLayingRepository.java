package com.muller.lappli.repository;

import com.muller.lappli.domain.ContinuityWireLongitLaying;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ContinuityWireLongitLaying entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContinuityWireLongitLayingRepository extends JpaRepository<ContinuityWireLongitLaying, Long> {}
