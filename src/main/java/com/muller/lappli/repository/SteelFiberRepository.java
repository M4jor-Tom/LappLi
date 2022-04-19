package com.muller.lappli.repository;

import com.muller.lappli.domain.SteelFiber;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SteelFiber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SteelFiberRepository extends JpaRepository<SteelFiber, Long> {}
