package com.muller.lappli.repository;

import com.muller.lappli.domain.OneStudySupply;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OneStudySupply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OneStudySupplyRepository extends JpaRepository<OneStudySupply, Long> {}
