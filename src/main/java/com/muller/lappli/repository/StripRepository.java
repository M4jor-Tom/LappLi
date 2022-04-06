package com.muller.lappli.repository;

import com.muller.lappli.domain.Strip;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Strip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StripRepository extends JpaRepository<Strip, Long> {}
