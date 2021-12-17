package com.muller.lappli.repository;

import com.muller.lappli.domain.BangleSupply;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BangleSupply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BangleSupplyRepository extends JpaRepository<BangleSupply, Long> {}
