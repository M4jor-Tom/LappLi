package com.muller.lappli.repository;

import com.muller.lappli.domain.StripLaying;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StripLaying entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StripLayingRepository extends JpaRepository<StripLaying, Long> {}
