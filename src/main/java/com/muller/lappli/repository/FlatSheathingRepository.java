package com.muller.lappli.repository;

import com.muller.lappli.domain.FlatSheathing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FlatSheathing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlatSheathingRepository extends JpaRepository<FlatSheathing, Long> {}
