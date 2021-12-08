package com.muller.lappli.repository;

import com.muller.lappli.domain.MaterialMarkingStatistic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MaterialMarkingStatistic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialMarkingStatisticRepository
    extends JpaRepository<MaterialMarkingStatistic, Long>, JpaSpecificationExecutor<MaterialMarkingStatistic> {}
