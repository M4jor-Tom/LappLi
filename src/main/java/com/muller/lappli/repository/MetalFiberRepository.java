package com.muller.lappli.repository;

import com.muller.lappli.domain.MetalFiber;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MetalFiber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetalFiberRepository extends JpaRepository<MetalFiber, Long> {}
