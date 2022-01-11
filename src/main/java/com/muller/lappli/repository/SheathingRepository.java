package com.muller.lappli.repository;

import com.muller.lappli.domain.Sheathing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sheathing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SheathingRepository extends JpaRepository<Sheathing, Long> {}
