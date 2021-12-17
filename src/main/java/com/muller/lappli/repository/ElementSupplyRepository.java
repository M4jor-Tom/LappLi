package com.muller.lappli.repository;

import com.muller.lappli.domain.ElementSupply;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ElementSupply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementSupplyRepository extends JpaRepository<ElementSupply, Long> {}
