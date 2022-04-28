package com.muller.lappli.repository;

import com.muller.lappli.domain.CarrierPlaitFiber;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CarrierPlaitFiber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarrierPlaitFiberRepository extends JpaRepository<CarrierPlaitFiber, Long> {}
