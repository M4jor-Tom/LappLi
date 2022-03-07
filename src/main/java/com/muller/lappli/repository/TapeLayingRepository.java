package com.muller.lappli.repository;

import com.muller.lappli.domain.TapeLaying;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TapeLaying entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TapeLayingRepository extends JpaRepository<TapeLaying, Long> {}
