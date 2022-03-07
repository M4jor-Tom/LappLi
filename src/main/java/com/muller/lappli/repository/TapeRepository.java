package com.muller.lappli.repository;

import com.muller.lappli.domain.Tape;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tape entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TapeRepository extends JpaRepository<Tape, Long> {}
