package com.muller.lappli.repository;

import com.muller.lappli.domain.ISupply;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ISupply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ISupplyRepository extends JpaRepository<ISupply, Long>, JpaSpecificationExecutor<ISupply> {}
