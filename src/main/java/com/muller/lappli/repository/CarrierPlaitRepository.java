package com.muller.lappli.repository;

import com.muller.lappli.domain.CarrierPlait;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CarrierPlait entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarrierPlaitRepository extends JpaRepository<CarrierPlait, Long> {}
