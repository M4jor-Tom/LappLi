package com.muller.lappli.repository;

import com.muller.lappli.domain.Lifter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Lifter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LifterRepository extends JpaRepository<Lifter, Long> {}
