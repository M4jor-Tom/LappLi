package com.muller.lappli.repository;

import com.muller.lappli.domain.CopperFiber;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CopperFiber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CopperFiberRepository extends JpaRepository<CopperFiber, Long> {}
