package com.muller.lappli.repository;

import com.muller.lappli.domain.Bangle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bangle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BangleRepository extends JpaRepository<Bangle, Long> {}
