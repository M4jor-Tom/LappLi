package com.muller.lappli.repository;

import com.muller.lappli.domain.Copper;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Copper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CopperRepository extends JpaRepository<Copper, Long> {}
