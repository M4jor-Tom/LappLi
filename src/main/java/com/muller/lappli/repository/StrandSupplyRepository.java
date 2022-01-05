package com.muller.lappli.repository;

import com.muller.lappli.domain.StrandSupply;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StrandSupply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrandSupplyRepository extends JpaRepository<StrandSupply, Long> {}
