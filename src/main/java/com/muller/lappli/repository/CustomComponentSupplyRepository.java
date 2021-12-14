package com.muller.lappli.repository;

import com.muller.lappli.domain.CustomComponentSupply;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustomComponentSupply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomComponentSupplyRepository
    extends JpaRepository<CustomComponentSupply, Long>, JpaSpecificationExecutor<CustomComponentSupply> {}
