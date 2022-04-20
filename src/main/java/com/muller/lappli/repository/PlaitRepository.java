package com.muller.lappli.repository;

import com.muller.lappli.domain.Plait;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Plait entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaitRepository extends JpaRepository<Plait, Long> {}
