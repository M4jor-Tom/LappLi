package com.muller.lappli.repository;

import com.muller.lappli.domain.Strand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Strand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrandRepository extends JpaRepository<Strand, Long> {}
